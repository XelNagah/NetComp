/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.util.Collection;

/**
 *
 * @author zerg
 */
public class Clase {

    String nombre;
    String descripcion;
    String contrasenia;
    String ip;
    int puerto;
    Anunciador anunciador;
    Thread anunciadorThread;
    Collection<Alumno> alumnos;
    Collection<Archivo> archivos;

    public Clase(String nombre, String descripcion, String contrasenia, String ip, int puerto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contrasenia = contrasenia;
        this.ip = ip;
        this.puerto = puerto;
        Boolean pass = true;
        if (contrasenia == null) {
            pass = false;
        }
        this.anunciador = new Anunciador(ip, puerto, nombre, pass, descripcion);
    }

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