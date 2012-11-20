/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import java.net.Socket;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoSR implements Runnable {

    boolean corriendo;
    long periodo = 300;
    VtnClaseAlumno ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
    Alumno alumno;
    Socket socketSR;
    ConexionAlumno conexion;

    public ManejadorAlumnoSR(Socket elSocketRS, ConexionAlumno laConexion) {
        alumno = ventana.getAlumno();
        socketSR = elSocketRS;
        conexion = laConexion;
    }

    private void manejar() {
        while (corriendo) {
            try {
                Thread.sleep(periodo);
                //Hacer Algo
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
    
    public void conectar(){
        
    }
}
