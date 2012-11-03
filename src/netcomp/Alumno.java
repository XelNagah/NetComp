/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zerg
 */
public class Alumno {
    String nombre;
    String apellido;
    String ip;
    Socket socket;

    public Alumno(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        try {
            this.ip = GenTools.getIp(true, false).toString();
        } catch (SocketException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        System.out.println("Me setearon el socket: " + socket);
    }
    
}
