/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import Mensajes.MensajesClase;
import java.io.IOException;
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
            socketSR = new Socket(socketRS.getInetAddress(), puertoSR);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
        conectarSR();
    }

    private void conectarSR() {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            String mensaje;
            mensaje = MensajesClase.conectar();
            out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            String mensaje;
            mensaje = MensajesClase.desconectar();
            out.println(mensaje);
            out.println("bye.");
            socketSR.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }
}
