/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;
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
        }
    }

    public int getPuertoRS() {
        return puertoRS;
    }

    private void manejar() {

        System.out.println(socketSR);
        PrintWriter out;
        try {
            out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            System.out.println("ENVIO: Soy el alumno " + alumno.getNombre() + " " + alumno.getApellido());
            out.println("Soy el alumno " + alumno.getNombre() + " " + alumno.getApellido());
            System.out.println("Creo el manejador");
            ManejadorAlumnoRS unManejador = new ManejadorAlumnoRS(alumno, puertoRS, conexion);
            conexion.setManejadorRS(unManejador);
            System.out.println("ENVIO EL PUERTO RS: " + puertoRS);
            out.println(puertoRS);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }


        //while (corriendo) {
            //try {
                //Thread.sleep(periodo);
                //System.out.println(socketSR);
                //PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
                //System.out.println(alumno.getNombre());
                //out.println("Soy el alumno " + alumno.getNombre() + " " + alumno.getApellido());
                //conexion.setManejadorRS(new ManejadorAlumnoRS(alumno, puertoRS, conexion));
                //out.println(puertoRS);

            //} catch (IOException ex) {
             //   Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
            //} catch (InterruptedException ex) {
             //   corriendo = false;
             //   break;
            //}
        //}
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }
}
