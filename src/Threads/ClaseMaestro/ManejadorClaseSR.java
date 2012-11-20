/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Clase;

/**
 *
 * @author zerg
 */
public class ManejadorClaseSR implements Runnable {

    boolean corriendo;
    long periodo = 300;
    Clase clase;
    private Socket socketSR;

    public ManejadorClaseSR(Socket elSocketSR, Clase laClase) {
        clase = laClase;
        socketSR = elSocketSR;
    }

    private void manejar() {
        while (corriendo) {
            BufferedReader in = null;
            try {
                Thread.sleep(periodo);
                in = new BufferedReader(new InputStreamReader(socketSR.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
                String linea;
            } catch (IOException ex) {
                Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                corriendo = false;
                break;
            }
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }
}
