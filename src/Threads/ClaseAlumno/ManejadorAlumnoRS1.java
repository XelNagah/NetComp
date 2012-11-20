/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoRS1 implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    VtnClaseAlumno ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
    Alumno alumno;
    Socket socketRS;
    ConexionAlumno conexion;

    public ManejadorAlumnoRS1(Socket elSocketRS, ConexionAlumno laConexion) {
        alumno = ventana.getAlumno();
        socketRS = elSocketRS;
        conexion = laConexion;
    }

    private void manejar() {
        while (corriendo) {
            try {
                Thread.sleep(periodo);
                BufferedReader in = new BufferedReader(new InputStreamReader(socketRS.getInputStream(), "UTF-8"));
                String linea;
                while (!"bye.".equals(linea = in.readLine())) {
                    manejarMensaje(linea);
                }
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorAlumnoRS1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                corriendo = false;
                break;
            }
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }
    
    private void manejarMensaje(String msg){
        
    }
}
