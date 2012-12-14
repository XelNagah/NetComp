/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Mensajes.TipoEventosGUI;
import Threads.ClaseAlumno.ManejadorAlumnoRS;
import Threads.ClaseAlumno.ManejadorAlumnoSR;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import netcomp.GUI.VtnArchivosAlumno;
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
    private VtnArchivosAlumno ventanaArchivos;
    private VtnClaseAlumno ventana;
    private ArrayList<Alumno> alumnos;
    private ArrayList<File> archivos;

    public ConexionAlumno(Alumno elAlumno, InfoClase laClase) {
        alumno = elAlumno;
        clase = laClase;
        ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
        alumnos = new ArrayList<Alumno>();
        archivos = new ArrayList<File>();
        conectar();
    }

    public ConexionAlumno(Alumno elAlumno, InfoClase laClase, String elPassword) {
        alumno = elAlumno;
        clase = laClase;
        ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
        alumnos = new ArrayList<Alumno>();
        archivos = new ArrayList<File>();
        conectar(elPassword);
    }

    public ArrayList<File> getArchivos() {
        return archivos;
    }

    public File getArchivos(int i) {
        if (i >= 0) {
            return archivos.get(i);
        } else {
            return null;
        }
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

    public Boolean getConectado() {
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

    public void setVentanaArchivos(VtnArchivosAlumno ventanaArchivos) {
        this.ventanaArchivos = ventanaArchivos;
    }

    public void actualizarListaAlumnos(ArrayList<Alumno> losAlumnos) {
        alumnos.clear();
        for (Iterator<Alumno> it = losAlumnos.iterator(); it.hasNext();) {
            Alumno elAlumno = it.next();
            alumnos.add(elAlumno);
        }
        ventana.actualizarVistaAlumnos();
    }

    public void actualizarListaArchivos(ArrayList<File> losArchivos) {
        archivos.clear();
        for (Iterator<File> it = losArchivos.iterator(); it.hasNext();) {
            File elArchivo = it.next();
            archivos.add(elArchivo);
        }
        ventanaArchivos.actualizarVistaArchivos();
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
        JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Inane error", JOptionPane.ERROR_MESSAGE);
        ventana.desconectar();
    }

    public void enviarEvento(TipoEventosGUI evt) {
        manejadorSR.getQueue().offer(evt);
    }

    public void pedirArchivo(File elArchivo, File guardarPath) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(elArchivo);
        params.add(guardarPath);
        TipoEventosGUI evento = new TipoEventosGUI(TipoEventosGUI.pedirArchivo, params);
        enviarEvento(evento);
    }
}
