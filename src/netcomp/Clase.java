/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Threads.ClaseMaestro.Anunciador;
import Threads.ClaseMaestro.ManejadorDeConexiones;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.GUI.VtnClaseMaestro;

/**
 *
 * @author zerg
 */
public class Clase {

    //Atributos
    private String nombre;
    private String profesor;
    private String descripcion;
    private String contrasenia;
    private String ip;
    private int puerto;
    private Anunciador anunciador;
    private Thread anunciadorThread;
    private ManejadorDeConexiones manejadorDeConexiones;
    private ArrayList<Alumno> alumnos;
    private ArrayList<Archivo> archivos;

    //Constructor
    public Clase(String nombre, String contrasenia, String profesor, String descripcion) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.profesor = profesor;
        this.descripcion = descripcion;
        ip = averiguarIp();
        this.puerto = encontrarPuerto();
        Boolean pass = true;
        if (contrasenia == null || "".equals(contrasenia)) {
            pass = false;
        }
        this.alumnos = new ArrayList<Alumno>();
        this.anunciador = new Anunciador(ip, puerto, nombre, pass, profesor, descripcion);
        this.manejadorDeConexiones = new ManejadorDeConexiones(puerto, alumnos);
    }

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void anunciar() {

        if (anunciadorThread != null && !anunciadorThread.isInterrupted()) {
            anunciadorThread.interrupt();
        }
        anunciadorThread = new Thread(anunciador);
        anunciadorThread.start();
        new Thread(manejadorDeConexiones).start();
    }

    public void dejarDeAnunciar() {
        anunciadorThread.interrupt();
    }
}