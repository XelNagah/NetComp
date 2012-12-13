/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import Mensajes.MensajesClase;
import Mensajes.TipoEventosGUI;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
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
    BlockingQueue<TipoEventosGUI> queue;

    public ManejadorClaseSR(Socket elSocketRS, Clase laClase, ConexionClase laConexion) {
        clase = laClase;
        socketRS = elSocketRS;
        conexion = laConexion;
        queue = new ArrayBlockingQueue<TipoEventosGUI>(50);
    }

    public void setPuertoSR(int puertoSR) {
        this.puertoSR = puertoSR;
    }

    private void manejar() {
        try {
            socketSR = new Socket(socketRS.getInetAddress(), puertoSR);

            conectarSR();
            while (corriendo) {
                try {
                    //Recibir instruccion del GUI
                    TipoEventosGUI elTipoEVento = queue.take();
                    Integer tipoMsg = elTipoEVento.getEventId();

                    switch (tipoMsg) {
                        case TipoEventosGUI.actualizarListaArchivos:
                            System.out.println("Actualizo list de archivos");
                            //método
                            break;
                        case TipoEventosGUI.enviarArchivo:
                            System.out.println("Envío archivo");
                            //método
                            enviarArchivo();
                            break;
                        case TipoEventosGUI.desconectarse:
                            System.out.println("Me desconecto");
                            //método
                            desconectar();
                            break;
                        case TipoEventosGUI.listUpdate:
                            System.out.println("Envío un listUpdate");
                            //método
                            listUpdate(clase.getAlumnos());
                            break;
                    }

                } catch (InterruptedException ex) {
                    corriendo = false;
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BlockingQueue<TipoEventosGUI> getQueue() {
        return queue;
    }

    private void conectarSR() {
        try {
            oos = new ObjectOutputStream(socketSR.getOutputStream());
            oos.flush();
            String mensaje;
            mensaje = MensajesClase.conectar();
            oos.writeObject(mensaje);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            String mensaje;
            mensaje = MensajesClase.desconectar();
            if (!socketSR.isClosed()) {
                oos.writeObject(mensaje);
                oos.writeObject("bye.");
                oos.flush();
            }
            oos.close();
            socketSR.close();
            //clase.delConexion(conexion);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listUpdate(ArrayList<Alumno> losAlumnos) {
        try {
            String mensaje;
            mensaje = MensajesClase.listUpdate();
            if (!socketSR.isClosed()) {
                oos.writeObject(mensaje);
                oos.writeUnshared(losAlumnos);
                oos.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }

    private void enviarArchivo() {
        try {
            String mensaje;
            mensaje = MensajesClase.listUpdate();
            if (!socketSR.isClosed()) {
                oos.writeObject(mensaje);
                oos.writeUnshared(clase);
                oos.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarArchivo(File unArchivo) {
        try {
            String mensaje;
            mensaje = MensajesClase.listUpdate();
            if (!socketSR.isClosed()) {
                oos.writeObject(mensaje);
                oos.writeUnshared(unArchivo);
                oos.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
