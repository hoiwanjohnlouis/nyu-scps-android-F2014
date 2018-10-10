/*
 * **************************************************************************
 *  * Copyright (c) 2016 HW Tech Services, LLC
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  **************************************************************************
 */

package com.hoiwanlouis.mystockportfolio.sockets;

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
    // get a logger instance named "com.company.TimeServer"
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    public static String TERMINATION_CLAUSE = "SERVER>>> TERMINATE";

    static {
        // Now set its level. Normally you do not need to set the
        // level of a logger programmatically. This is usually done
        // in configuration files.
        //logger.setLevel(Level.INFO);
    }

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
        logger.info(m_serverName+": Processing service");
        m_serverState = serverStates.RUNNING;
        try {
            waitForConnection();
            getStreams();
            processConnection();
        }
        catch (Exception ex) {
            logger.error(m_serverName+": Trying to process client connections; Received exception.\n",ex);
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
            logger.error(m_serverName+": Trying to close socket; Received exception.\n"+ex1+"\n",ex1);
        }
        catch (IOException ex2) {
            logger.error(m_serverName+": Trying to close socket; Received exception.\n"+ex2+"\n",ex2);
        }
    }

    public void waitForConnection() throws IOException {
        logger.info(m_serverName+": Waiting for connection");
        m_clientConnection = m_serverSocket.accept(); // allow server to accept client connection
    }

    public void getStreams() throws IOException {
        logger.info(m_serverName+": Getting streams for connection");
        m_clientOutput = new ObjectOutputStream(m_clientConnection.getOutputStream());
        m_clientOutput.flush(); // flush output buffer to send header info
        m_clientInput = new ObjectInputStream(m_clientConnection.getInputStream());
    }

    public void processConnection() throws IOException {
        logger.info(m_serverName+": Processing service connection");
        //String message = "Connection successful";
        sendData(getDateTime());
        sendData(TERMINATION_CLAUSE);
    }

    public void closeConnection() {
        logger.info(m_serverName+": Terminating connection");
        try {
            m_clientOutput.close();
            m_clientInput.close();
            m_clientConnection.close();
        }
        catch (StreamCorruptedException ex2) {
            logger.error(m_serverName+": Trying to close client connections; Received exception.\n"+ex2+"\n",ex2);
        }
        catch (IOException ex1) {
            logger.error(m_serverName+": Trying to close client connections; Received exception.\n"+ex1+"\n",ex1);
        }
    }

    public void sendData(String message) {
        logger.info(m_serverName+": Sending data to client");
        try {
            m_clientOutput.writeObject(message);
        }
        catch (IOException ex1) {
            logger.error(m_serverName+": Trying to send data to client connection; Received exception.\n"+ex1+"\n",ex1);
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
