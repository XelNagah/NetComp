/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import Mensajes.MensajesAlumno;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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

    public ManejadorAlumnoSR(InfoClase laClase, ConexionAlumno laConexion) {
        try {
            alumno = laConexion.getAlumno();
            String ip = laClase.getIp();
            int puerto = laClase.getPuerto();
            socketSR = new Socket(ip, puerto);
            conexion = laConexion;
            puertoRS = GenTools.findFreePort();
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
    }

    private void conectar() {
        try {
            //Creo un PrintWriter
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            //Genero el mensaje de conexión
            String mensaje = MensajesAlumno.conectar(alumno, puertoRS);
            //Creo el manejador RS de alumno, en el puerto que envío a la clase para que se conecte.
            ManejadorAlumnoRS unManejador = new ManejadorAlumnoRS(alumno, puertoRS, conexion);
            //Asigno el manejador a la conexión alumno.
            conexion.setManejadorRS(unManejador);
            //Envío el mensaje de conexión a la clase.
            out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            String mensaje;
            mensaje = MensajesAlumno.desconectar();
            out.println(mensaje);
            out.println("bye.");
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
