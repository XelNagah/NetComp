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
import java.net.UnknownHostException;
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
    int puertoSR;
    private Socket socketRS;
    private Socket socketSR;

    public ManejadorClaseSR(Socket elSocketRS, Clase laClase) {
        clase = laClase;
        socketRS = elSocketRS;
    }

    public void setPuertoSR(int puertoSR) {
        this.puertoSR = puertoSR;
    }
    
    private void manejar() {
        try {
            System.out.println(socketRS.getInetAddress().toString());
            System.out.println(puertoSR);
            socketSR = new Socket(socketRS.getInetAddress(), puertoSR);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (corriendo) {
            BufferedReader in = null;
            try {
                Thread.sleep(periodo);
                in = new BufferedReader(new InputStreamReader(socketSR.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
                String linea;
                out.println("Soy la clase "+ clase.getNombre());
                out.println(puertoSR);
                
                
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
