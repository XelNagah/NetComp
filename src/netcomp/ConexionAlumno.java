/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Threads.ClaseAlumno.ManejadorAlumnoRS;
import Threads.ClaseAlumno.ManejadorAlumnoSR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zerg
 */
public class ConexionAlumno {

    Alumno alumno;
    InfoClase clase;
    Socket socketRS;
    ManejadorAlumnoRS manejadorRS;
    Thread manejadorRSThread;
    Socket socketSR;
    ManejadorAlumnoSR manejadorSR;
    Thread manejadorSRThread;

    public ConexionAlumno(Alumno elAlumno, InfoClase laClase) {
        alumno = elAlumno;
        clase = laClase;
        conectar();
    }

    private void conectar() {
        try {
            String ip = clase.getIp();
            int puerto = clase.getPuerto();
            socketSR = new Socket(ip, puerto);
            manejadorSR = new ManejadorAlumnoSR(socketSR, this);
            manejadorSRThread = new Thread(manejadorSR);
            manejadorSRThread.start();
            manejadorSR.conectar();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConexionAlumno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConexionAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
