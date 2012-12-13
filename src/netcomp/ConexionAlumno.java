/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Threads.ClaseAlumno.ManejadorAlumnoRS;
import Threads.ClaseAlumno.ManejadorAlumnoSR;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;

/**
 *
 * @author zerg
 */
public class ConexionAlumno {

    Boolean conectado = false;
    Alumno alumno;
    InfoClase clase;
    Socket socketRS;
    int puertoRS;
    ManejadorAlumnoRS manejadorRS;
    Thread manejadorRSThread;
    Socket socketSR;
    ManejadorAlumnoSR manejadorSR;
    Thread manejadorSRThread;
    VtnClaseAlumno ventana;
    private ArrayList<Alumno> alumnos;

    public ConexionAlumno(Alumno elAlumno, InfoClase laClase) {
        alumno = elAlumno;
        clase = laClase;
        ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
        alumnos = new ArrayList<Alumno>();
        conectar();
    }

    public ConexionAlumno(Alumno elAlumno, InfoClase laClase, String elPassword) {
        alumno = elAlumno;
        clase = laClase;
        ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
        alumnos = new ArrayList<Alumno>();
        conectar(elPassword);
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public Alumno getAlumnos(int i) {
        return alumnos.get(i);
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public synchronized Boolean getConectado() {
        return conectado;
    }

    public void setConectado(Boolean conectado) {
        this.conectado = conectado;
    }

    public void setManejadorRS(ManejadorAlumnoRS manejadorRS) {
        this.manejadorRS = manejadorRS;
        manejadorRSThread = new Thread(manejadorRS);
        manejadorRSThread.start();
    }

    public void actualizarListaAlumnos(ArrayList<Alumno> losAlumnos) {
        alumnos.clear();
        for (Iterator<Alumno> it = losAlumnos.iterator(); it.hasNext();) {
            Alumno elAlumno = it.next();
            alumnos.add(elAlumno);
        }
        ventana.actualizarVista();

    }

    private void conectar() {
        manejadorSR = new ManejadorAlumnoSR(clase, this);
        manejadorSRThread = new Thread(manejadorSR);
        manejadorSRThread.start();
    }

    private void conectar(String elPassword) {
        manejadorSR = new ManejadorAlumnoSR(clase, this, elPassword);
        manejadorSRThread = new Thread(manejadorSR);
        manejadorSRThread.start();
    }

    public void confirmarConexion() {
        manejadorSR.confirmarConexion();
    }

    public void desconectar() {
        manejadorSR.desconectar();
    }

    public void fallaContrasenia() {
        JOptionPane.showMessageDialog(null,"Contraseña incorrecta.","Inane error",JOptionPane.ERROR_MESSAGE);
        ventana.desconectar();
    }
}
