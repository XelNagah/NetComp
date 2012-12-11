/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Threads.ClaseAlumno.ManejadorAlumnoRS;
import Threads.ClaseAlumno.ManejadorAlumnoSR;
import java.net.Socket;

/**
 *
 * @author zerg
 */
public class ConexionAlumno {

    Alumno alumno;
    InfoClase clase;
    Socket socketRS;
    int puertoRS;
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

    public Alumno getAlumno() {
        return alumno;
    }

    public void setManejadorRS(ManejadorAlumnoRS manejadorRS) {
        System.out.println("Configure ManejadorRS");
        this.manejadorRS = manejadorRS;
        System.out.println("Creo thread de manejadorRS");
        manejadorRSThread = new Thread(manejadorRS);
        System.out.println("Empiezo thread de manejadorRS");
        manejadorRSThread.start();
    }

    private void conectar() {
        manejadorSR = new ManejadorAlumnoSR(clase, this);
        manejadorSRThread = new Thread(manejadorSR);
        manejadorSRThread.start();
    }
    
    public void desconectar(){
        manejadorSR.desconectar();
    }
}
