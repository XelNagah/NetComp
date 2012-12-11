/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Clase;
import netcomp.ConexionClase;

/**
 *
 * @author zerg
 */
public class ManejadorConexiones implements Runnable {

    boolean corriendo;
    private long periodo = 300;
    private int puerto;
    private Clase clase;
    private ArrayList<ConexionClase> conexiones;
    private Socket socketAlumno;

    public ManejadorConexiones(int elPuerto, Clase laClase) {
        this.puerto = elPuerto;
        //alumnos = listaAlumnos;
        clase = laClase;
        conexiones = new ArrayList<ConexionClase>();
    }

    private void manejarConexiones() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(puerto);
            while (corriendo) {
                try {
                    //Defino el sleep
                    Thread.sleep(periodo);
                    //Espero una conexión y acepto las entrantes.
                    socketAlumno = serverSocket.accept();
                    //Al aceptarla, creo el thread que va a manejar ese alumno.
                    conexiones.add(new ConexionClase(socketAlumno, clase));
                    //Olvido el socket de este alumno, ya que se lo delegué al manejador 
                    //y vuelvo a escuchar conexiones entrantes.
                    socketAlumno = null;
                } catch (IOException ex) {
                    Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    corriendo = false;
                    try {
                        socketAlumno.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ConexionClase> getConexiones() {
        return conexiones;
    }
    
    @Override
    public void run() {
        corriendo = true;
        manejarConexiones();
    }
}