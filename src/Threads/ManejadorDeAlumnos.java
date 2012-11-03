/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author zerg
 */
public class ManejadorDeAlumnos implements Runnable {

    private Socket server;
    private String line, input;

    public ManejadorDeAlumnos(Socket server) {
        this.server = server;
    }
    
    @Override
    public void run() {



        input = "";

        try {
            // Get input from the client
            //DataInputStream in = new DataInputStream(server.getInputStream());
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());

            while ((line = in.readLine()) != null && !line.equals(".")) {
                input = input + line;
                out.println("I got:" + line);
            }

            // Now write to the client

            System.out.println("Overall message is:" + input);
            out.println("Overall message is:" + input);

            server.close();
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
        }
    }
}
