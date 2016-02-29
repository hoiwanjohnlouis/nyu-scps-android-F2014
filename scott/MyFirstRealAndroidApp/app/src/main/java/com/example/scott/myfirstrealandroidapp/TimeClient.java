package com.example.scott.myfirstrealandroidapp;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/** // 172.30.2.151
 *  // port 1971
 * Created by computerlab on 9/20/14.
 */
public class TimeClient {
    private static final String TAG = TimeClient.class.getSimpleName();

    static {
        // Now set its level. Normally you do not need to set the
        // level of a logger programmatically. This is usually done
        // in configuration files.
        //logger.setLevel(Level.INFO);
    }

    ObjectOutputStream m_output;
    ObjectInputStream m_input;
    Integer m_port;
    String m_clientName;
    String m_timeServerHost;
    Socket m_clientSocket;

    public TimeClient(String name, String host, int port) {
        m_clientName = name;
        m_timeServerHost = host;
        m_port = port;
    }

    public void runClient() {
        Log.i(TAG, m_clientName + ": Processing service");
        // connect to server; get streams; process connection
        try {
            connectToServer(); // create Socket to make connection
            getStreams(); // get input and output streams
            processConnection(); // process connection
        }
        catch (ConnectException ex2) {
            Log.e(TAG, m_clientName+": Could not connect to server.");
            ex2.printStackTrace(System.err);
        }
        catch (NullPointerException ex3) {
            Log.e(TAG,m_clientName+": Null pointer son.\n"+ex3+"\n");
            ex3.printStackTrace(System.err);
        }
        catch (IOException ex1) {
            Log.e(TAG,m_clientName+": Trying to connect to server; Received exception.");
            ex1.printStackTrace(System.err);
        }
        finally {
            Log.d(TAG,"close connection");
            closeConnection();
        }
    }

    public void connectToServer() throws IOException {
        Log.i(TAG,m_clientName+": Connecting to server");
        m_clientSocket = new Socket(InetAddress.getByName(m_timeServerHost), m_port);
    }

    public void getStreams() throws IOException {
        Log.i(TAG,m_clientName+": Getting streams");
        m_output = new ObjectOutputStream(m_clientSocket.getOutputStream());
        m_output.flush(); // flush output buffer to send header info

        // set up input stream for objects
        m_input = new ObjectInputStream(m_clientSocket.getInputStream());
    }

    public void processConnection() throws IOException {
        Log.i(TAG,m_clientName+": Processing connection");

        String message = TimeServer.TERMINATION_CLAUSE;
        do // process messages sent from server
        {
            try {
                message = (String) m_input.readObject(); // read new message
                if (!message.equals(TimeServer.TERMINATION_CLAUSE)) {
                    Log.e(TAG,m_clientName+": TIMESTAMP = "+message);
                }
            }
            catch (ClassNotFoundException ex1) {
                Log.e(TAG,m_clientName+": Trying to read a message from server; Received exception.\n"+ex1+"\n");
                ex1.printStackTrace(System.err);
            }
            catch (IOException ex2) {
                Log.e(TAG,m_clientName+": Trying to read a message from server; Received exception.\n"+ex2+"\n");
                ex2.printStackTrace(System.err);
            }
        } while (! message.equals(TimeServer.TERMINATION_CLAUSE));
    }

    public void closeConnection() {
        Log.i(TAG,m_clientName+": Terminating connection");
        try {
            m_output.close();
            m_input.close();
            m_clientSocket.close();
        }
        catch (StreamCorruptedException ex2) {
            Log.e(TAG,m_clientName+": Trying to close server connection; Received exception.\n"+ex2+"\n");
            ex2.printStackTrace(System.err);
        }
        catch (NullPointerException ex3) {
            Log.e(TAG,m_clientName+": Null pointer son.\n"+ex3+"\n");
            ex3.printStackTrace(System.err);
        }
        catch (IOException ex1) {
            Log.e(TAG,m_clientName+": Trying to close server connection; Received exception.\n"+ex1+"\n");
            ex1.printStackTrace(System.err);
        }
    }
}
