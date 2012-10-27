/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.net.SocketException;
import java.util.Collection;
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
    private Collection<Alumno> alumnos;
    private Collection<Archivo> archivos;

    //Constructor
    public Clase(String nombre, String descripcion, String contrasenia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contrasenia = contrasenia;
        ip = averiguarIp();
        this.puerto = 5008;
        Boolean pass = true;
        if (contrasenia == null) {
            pass = false;
        }
        this.anunciador = new Anunciador(ip, puerto, nombre, pass, descripcion);
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

    private String averiguarIp(){
        String laIp = null;
        try {
            //Averiguo mi dirección ip y la seteo
            laIp = ObtenerIp.getIp(true, false).toString().substring(1);
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
    }

    public void dejarDeAnunciar() {
        anunciadorThread.interrupt();
    }
}