/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import Mensajes.MensajesAlumno;
import Mensajes.TipoEventosGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;
import netcomp.GenTools;
import netcomp.InfoClase;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoSR implements Runnable {

    boolean corriendo;
    long periodo = 300;
    VtnClaseAlumno ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
    Alumno alumno;
    Socket socketSR;
    int puertoRS;
    ConexionAlumno conexion;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    BlockingQueue<TipoEventosGUI> queue;

    public ManejadorAlumnoSR(InfoClase laClase, ConexionAlumno laConexion) {
        try {
            alumno = laConexion.getAlumno();
            String ip = laClase.getIp();
            int puerto = laClase.getPuerto();
            socketSR = new Socket(ip, puerto);
            conexion = laConexion;
            puertoRS = GenTools.findFreePort();
            queue = new ArrayBlockingQueue<TipoEventosGUI>(50);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No pude conectar al socketSR - Alumno");
        }
    }

    public int getPuertoRS() {
        return puertoRS;
    }

    private void manejar() {
        conectar();
        while (corriendo) {
            try {
                //Recibir instruccion del GUI
                TipoEventosGUI elTipoEVento = queue.take();
                Integer tipoMsg = elTipoEVento.getEventId();

                switch (tipoMsg) {

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void conectar() {
        try {
            //Creo un PrintWriter
            //PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            oos = new ObjectOutputStream(socketSR.getOutputStream());
            oos.flush();
            //Genero el mensaje de conexión
            String mensaje = MensajesAlumno.conectar(alumno, puertoRS);
            //Creo el manejador RS de alumno, en el puerto que envío a la clase para que se conecte.
            ManejadorAlumnoRS unManejador = new ManejadorAlumnoRS(alumno, puertoRS, conexion);
            //Asigno el manejador a la conexión alumno.
            conexion.setManejadorRS(unManejador);
            //Envío el mensaje de conexión a la clase.
            oos.writeObject(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirmarConexion() {
        try {
            oos.writeObject("Done");
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            //PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            String mensaje;
            mensaje = MensajesAlumno.desconectar();
            if (!socketSR.isClosed()) {
                oos.writeObject(mensaje);
                oos.writeObject("bye.");
            }
            socketSR.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }
}
