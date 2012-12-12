/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import Mensajes.MensajesClase;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.Clase;
import netcomp.ConexionClase;

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
    ConexionClase conexion;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    BlockingQueue<Integer> queue;

    public ManejadorClaseSR(Socket elSocketRS, Clase laClase, ConexionClase laConexion) {
        clase = laClase;
        socketRS = elSocketRS;
        conexion = laConexion;
    }

    public void setPuertoSR(int puertoSR) {
        this.puertoSR = puertoSR;
    }

    private void manejar() {
        try {
            //BlockingQueue
            socketSR = new Socket(socketRS.getInetAddress(), puertoSR);
            conectarSR();
            ServerSocket unSocket = new ServerSocket(5467);
            unSocket.accept();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ManejadorClaseSR ha muerto.");
    }

    private void conectarSR() {
        try {
            oos = new ObjectOutputStream(socketSR.getOutputStream());
            oos.flush();
            String mensaje;
            mensaje = MensajesClase.conectar();
            oos.writeObject(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            String mensaje;
            mensaje = MensajesClase.desconectar();
            oos.writeObject(mensaje);
            oos.writeObject("bye.");
            socketSR.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listUpdate(ArrayList<Alumno> losAlumnos) {
        try {
            String mensaje;
            mensaje = MensajesClase.listUpdate();
            oos.writeObject(mensaje);
            oos.writeUnshared(losAlumnos);
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
