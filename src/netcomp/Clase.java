/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Mensajes.TipoEventosGUI;
import Threads.ClaseMaestro.Anunciador;
import Threads.ClaseMaestro.GeneradorPantalla;
import Threads.ClaseMaestro.ManejadorConexiones;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.GUI.VtnClaseMaestro;

/**
 *
 * @author zerg
 */
public class Clase implements Serializable {

    //Atributos
    private String nombre;
    private String profesor;
    private String descripcion;
    private String contrasenia;
    private String ip;
    private int puerto;
    private VtnClaseMaestro ventana;
    private Anunciador anunciador;
    private Thread anunciadorThread;
    private ManejadorConexiones manejadorDeConexiones;
    private Thread manejadorDeConexionesThread;
    private List<Alumno> alumnos;
    private ArrayList<File> archivos;
    private GeneradorPantalla generadorPantalla;
    private Thread generadorPantallaThread;
    private boolean compartiendoPantalla;

    //Constructor
    public Clase(String nombre, String contrasenia, String profesor, String descripcion, VtnClaseMaestro laVentana) {
        //Seteo nombre
        this.nombre = nombre;
        //Seteo contraseña
        this.contrasenia = contrasenia;
        //Seteo profesor
        this.profesor = profesor;
        //Seteo descripcion
        this.descripcion = descripcion;
        //seteo mi ip
        ip = averiguarIp();
        //seteo mi puerto
        this.puerto = encontrarPuerto();
        Boolean pass = true;
        if (contrasenia == null || "".equals(contrasenia)) {
            pass = false;
        }
        //Creo lista de alumnos
        this.alumnos = new LinkedList<Alumno>();
        //Seteo el anunciador de clase
        this.anunciador = new Anunciador(ip, puerto, nombre, pass, profesor, descripcion);
        //Seteo el manejador de conexiones
        this.manejadorDeConexiones = new ManejadorConexiones(puerto, this);
        //Configuro la ventana de la clase
        ventana = laVentana;
        //Creo lista de archivos
        archivos = new ArrayList<File>();
        compartiendoPantalla = false;
    }

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCompartiendoPantalla(boolean b) {
        compartiendoPantalla = b;
    }

    public boolean getCompartiendoPantalla() {
        return compartiendoPantalla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void addAlumno(Alumno elAlumno) {
        this.alumnos.add(elAlumno);
        sortAlumnos();
    }

    public void delAlumno(Alumno elAlumno) {
        this.alumnos.remove(elAlumno);
        sortAlumnos();
    }

    public void actualizarListaAlumnos() {
        ventana.actualizarVista(0, alumnos.size());
        manejadorDeConexiones.actualizarListaAlumnos();
    }
    
    public ArrayList<Alumno> getAlumnos() {
        return new ArrayList<Alumno>(alumnos);
    }

    public Alumno getAlumnos(int pos) {
        ArrayList<Alumno> a = new ArrayList<Alumno>(alumnos);
        return a.get(pos);
    }
    
    private void sortAlumnos() {
        Collections.sort(alumnos, new Comparator<Alumno>() {
            @Override
            public int compare(Alumno t, Alumno t1) {
                return (t.getNombre() + t.getApellido()).compareToIgnoreCase(
                        (t1.getNombre() + t1.getApellido()));
            }
        });
    }

    public void actualizarListaArchivos() {
        manejadorDeConexiones.actualizarListaArchivos();
    }

    public void actualizarListaArchivos(Alumno elAlumno) {
        manejadorDeConexiones.actualizarListaArchivos(elAlumno);
    }

    public ArrayList<File> getArchivos() {
        return archivos;
    }

    public File getArchivos(File elArchivo) {
        File unArchivo;
        for (Iterator it = archivos.iterator(); it.hasNext();) {
            unArchivo = (File) it.next();
            if (unArchivo.getName().equals(elArchivo.getName())) {
                elArchivo = unArchivo;
            }
        }
        return elArchivo;
    }

    public File getArchivos(int i) {
        return archivos.get(i);
    }

    public void addArchivo(File elArchivo) {
        if (!archivos.contains(elArchivo)) {
            archivos.add(elArchivo);
        }
    }

    public void delArchivo(File elArchivo) {
        archivos.remove(elArchivo);
    }

    public void delArchivo(int i) {
        archivos.remove(i);
    }

    public VtnClaseMaestro getVentana() {
        return ventana;
    }

    public void setVentana(VtnClaseMaestro ventana) {
        this.ventana = ventana;
    }

    private int encontrarPuerto() {
        try {
            return GenTools.findFreePort();
        } catch (IOException ex) {
            Logger.getLogger(Clase.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    private String averiguarIp() {
        String laIp = null;
        try {
            //Averiguo mi dirección ip y la seteo
            laIp = GenTools.getIp(true, false).toString().substring(1);
        } catch (SocketException ex) {
            Logger.getLogger(VtnClaseMaestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return laIp;
    }

    public void addConexion(ConexionClase laConexion) {
        manejadorDeConexiones.addConexion(laConexion);
    }

    public void delConexion(ConexionClase laConexion) {
        manejadorDeConexiones.delConexion(laConexion);
    }

    public ManejadorConexiones getManejadorDeConexiones() {
        return manejadorDeConexiones;
    }

    public void compartirPantalla() {
        generadorPantalla = new GeneradorPantalla(manejadorDeConexiones);
        generadorPantallaThread = new Thread(generadorPantalla);
        generadorPantallaThread.start();
        compartiendoPantalla = true;
    }

    public void pararCompartirPantalla() {
        if (generadorPantallaThread != null && !generadorPantallaThread.isInterrupted()) {
            TipoEventosGUI elEvento = new TipoEventosGUI(TipoEventosGUI.stopCompartirPantalla);
            compartiendoPantalla = false;
            manejadorDeConexiones.enviarEventoGeneral(elEvento);
            generadorPantallaThread.interrupt();
        }
    }
    
    public void iniciar() {

        if (anunciadorThread != null && !anunciadorThread.isInterrupted()) {
            anunciadorThread.interrupt();
        }
        anunciadorThread = new Thread(anunciador);
        anunciadorThread.start();
        manejadorDeConexionesThread = new Thread(manejadorDeConexiones);
        manejadorDeConexionesThread.start();
    }

    public void detener() {
        anunciadorThread.interrupt();
        //manejadorDeConexiones.cerrarConexiones();
        TipoEventosGUI elEvento = new TipoEventosGUI(TipoEventosGUI.desconectarse);
        manejadorDeConexiones.enviarEventoGeneral(elEvento);
        manejadorDeConexionesThread.interrupt();
    }
}
