package com.hoiwanlouis.mystockportfolio.sockets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * Created by computerlab on 9/20/14.
 */
public class TimeClient {
    // get a logger instance named "com.company.TimeClient"
    public static Logger logger = Logger.getLogger("com.company.TimeClient");

    static {
        // Now set its level. Normally you do not need to set the
        // level of a logger programmatically. This is usually done
        // in configuration files.
        //logger.setLevel(Level.INFO);
    }

    ObjectOutputStream m_output;
    ObjectInputStream m_input;
    int m_port;
    String m_clientName;
    String m_timeServerHost;
    Socket m_clientSocket;

    public TimeClient(String name, String host, int port) {
        m_clientName = name;
        m_timeServerHost = host;
        m_port = port;
    }

    public void runClient() {
        logger.info(m_clientName+": Processing service");
        // connect to server; get streams; process connection
        try {
            connectToServer(); // create Socket to make connection
            getStreams(); // get input and output streams
            processConnection(); // process connection
        }
        catch (ConnectException ex0) {
            logger.error(m_clientName+": Trying to connect to server; Received exception.\n"+ex0+"\n");
        }
        catch (IOException ex1) {
            logger.error(m_clientName+": Trying to connect to server; Received exception.\n"+ex1+"\n");
        }
        finally {
            logger.debug(m_clientName+"Closing connection.");
            closeConnection();
        }
    }

    public void connectToServer() throws IOException {
        logger.info(m_clientName+": Connecting to server");
        m_clientSocket = new Socket(InetAddress.getByName(m_timeServerHost), m_port);
    }

    public void getStreams() throws IOException {
        logger.info(m_clientName+": Getting streams");
        m_output = new ObjectOutputStream(m_clientSocket.getOutputStream());
        m_output.flush(); // flush output buffer to send header info

        // set up input stream for objects
        m_input = new ObjectInputStream(m_clientSocket.getInputStream());
    }

    public void processConnection() throws IOException {
        logger.info(m_clientName+": Processing connection");

        String message = TimeServer.TERMINATION_CLAUSE;
        do // process messages sent from server
        {
            try {
                message = (String) m_input.readObject(); // read new message
                if (!message.equals(TimeServer.TERMINATION_CLAUSE)) {
                    logger.info(m_clientName+": TIMESTAMP = "+message);
                }
            }
            catch (ClassNotFoundException ex1) {
                logger.error(m_clientName+": Trying to read a message from server; Received exception.\n"+ex1+"\n", ex1);
            }
            catch (IOException ex2) {
                logger.error(m_clientName+": Trying to read a message from server; Received exception.\n"+ex2+"\n", ex2);
            }
        } while (! message.equals(TimeServer.TERMINATION_CLAUSE));
    }

    public void closeConnection() {
        logger.info(m_clientName+": Terminating connection");
        try {
            m_output.close();
            m_input.close();
            m_clientSocket.close();
        }
        catch (StreamCorruptedException ex2) {
            logger.error(m_clientName+": Trying to close server connection; Received exception.\n"+ex2+"\n", ex2);
        }
        catch (IOException ex1) {
            logger.error(m_clientName+": Trying to close server connection; Received exception.\n"+ex1+"\n", ex1);
        }
    }
}
