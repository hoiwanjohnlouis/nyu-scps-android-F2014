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

import android.util.Log;

import java.io.*;
import java.util.Properties;

public class TimeServerMain {
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    static {
        // Now set its level. Normally you do not need to set the
        // level of a logger programmatically. This is usually done
        // in configuration files.
        //logger.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        FileReader propsFileReader = null;
        File pFile = null;
        BufferedReader pfReader = null;
        Properties serverProps = new Properties();

        boolean shouldExit = false;
        // load properties for server configuration
        try
        {
            pFile = new File("server.properties");
            propsFileReader = new FileReader(pFile);
            pfReader = new BufferedReader(propsFileReader);
            serverProps.load(pfReader);
            serverProps.list(System.out);
        }
        catch (FileNotFoundException ex1) {
            Log.e(DEBUG_TAG, "No such server properties file exists:\n\t" + pFile.getAbsolutePath());
            shouldExit = true;
        }
        catch (IOException ex2) {
            Log.e(DEBUG_TAG, "Error in reading server properties file:\n\t" + pfReader);
            shouldExit = true;
        }
        finally {
            if (shouldExit) {
                return;
            }
        }


        //int port = 1971; // pick a port above 1024 (<1024 need root access)
        //int backlog = 100; // socket queue length

        int port = 0;
        int backlog = 0;

        // load in server configuration properties from file
        try {
            port = Integer.parseInt(serverProps.getProperty("port"));
            backlog = Integer.parseInt(serverProps.getProperty("backlog"));
        }
        catch (NumberFormatException ex1) {
            Log.e(DEBUG_TAG, "Problem parsing server configuration parameters from properties");
            //Log.e(DEBUG_TAG, serverProps);
            return;
        }

        TimeServer tServer = null;

        try {
            tServer = new TimeServer("myTimeServer",port, backlog);
            while (true) {
                tServer.processServerQueue();
            }
        }
        catch (IOException ex1) {
            Log.e(DEBUG_TAG, ex1.toString());
            Log.e(DEBUG_TAG, ex1.getStackTrace().toString());
        }
        catch (Exception exx) {
            Log.e(DEBUG_TAG, exx.toString());
            Log.e(DEBUG_TAG, exx.getStackTrace().toString());
        }
        finally {
            if (tServer != null) {
                tServer.close();
            }
        }
    }
}
