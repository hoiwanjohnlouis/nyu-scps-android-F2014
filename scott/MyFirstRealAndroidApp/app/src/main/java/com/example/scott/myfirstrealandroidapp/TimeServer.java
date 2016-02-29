package com.example.scott.myfirstrealandroidapp;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Created by computerlab on 9/20/14.
 */
public class TimeServer {
    public static String TERMINATION_CLAUSE = "SERVER>>> TERMINATE";
    public enum serverStates {CLOSED,RUNNING};
    int m_port;
    int m_backlog;
    serverStates m_serverState;
    String m_serverName;
    ServerSocket m_serverSocket = null;
    Socket m_clientConnection;
    ObjectOutputStream m_clientOutput;
    ObjectInputStream m_clientInput;

    public TimeServer(String name, int port, int blog) throws Exception {
        m_serverName = name;
        //m_currentServerState;
        m_port = port;
        m_backlog = blog;
        m_serverSocket = new ServerSocket(m_port, m_backlog);
    }

    public void processServerQueue() throws Exception {
        System.out.println(m_serverName+": Processing service");
        m_serverState = serverStates.RUNNING;
        try {
            waitForConnection();
            getStreams();
            processConnection();
        }
        catch (Exception ex) {
            System.err.println(m_serverName+": Trying to process client connections; Received exception.\n");
            ex.printStackTrace(System.err);
        }
        finally {
            closeConnection();
        }
    }

    public void close() {
        try {
            m_serverSocket.close();
        }
        catch (NullPointerException ex1) {
            System.err.println(m_serverName+": Trying to close socket; Received exception.\n"+ex1+"\n");
            ex1.printStackTrace(System.err);
        }
        catch (IOException ex2) {
            System.err.println(m_serverName+": Trying to close socket; Received exception.\n"+ex2+"\n");
            ex2.printStackTrace(System.err);
        }
    }

    public void waitForConnection() throws IOException {
        System.out.println(m_serverName+": Waiting for connection");
        m_clientConnection = m_serverSocket.accept(); // allow server time to accept client connection
    }

    public void getStreams() throws IOException {
        System.out.println(m_serverName+": Getting streams for connection");
        m_clientOutput = new ObjectOutputStream(m_clientConnection.getOutputStream());
        m_clientOutput.flush(); // flush output buffer to send header info
        m_clientInput = new ObjectInputStream(m_clientConnection.getInputStream());
    }

    public void processConnection() throws IOException {
        System.out.println(m_serverName+": Processing service connection");
        //String message = "Connection successful";
        sendData(getDateTime());
        sendData(TERMINATION_CLAUSE);
    }

    public void closeConnection() {
        System.out.println(m_serverName+": Terminating connection");
        try {
            m_clientOutput.close();
            m_clientInput.close();
            m_clientConnection.close();
        }
        catch (StreamCorruptedException ex2) {
            System.err.println(m_serverName+": Trying to close client connections; Received exception.\n"+ex2+"\n");
            ex2.printStackTrace(System.err);
        }
        catch (IOException ex1) {
            System.err.println(m_serverName+": Trying to close client connections; Received exception.\n"+ex1+"\n");
            ex1.printStackTrace(System.err);
        }
    }

    public void sendData(String message) {
        System.out.println(m_serverName+": Sending data to client");
        try {
            m_clientOutput.writeObject(message);
        }
        catch (IOException ex1) {
            System.err.println(m_serverName+": Trying to send data to client connection; Received exception.\n"+ex1+"\n");
            ex1.printStackTrace(System.err);
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
