/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import netcomp.Alumno;

/**
 *
 * @author zerg
 */
public class ManejadorDeConexiones implements Runnable {
    
    private int puerto;
    ArrayList<Alumno> alumnos;

    public ManejadorDeConexiones(int elPuerto, ArrayList<Alumno> listaAlumnos ) {
        this.puerto = elPuerto;
        alumnos = listaAlumnos;
    }
    
    private void manejarConexiones() {
        ServerSocket serverSocket;
        boolean listening = true;

        while (listening) {
            try {
                serverSocket = new ServerSocket(puerto);
                Socket server;

                server = serverSocket.accept();

                ManejadorDeAlumnos manejador = new ManejadorDeAlumnos(server);
                Thread t = new Thread(manejador);
                t.start();
            } catch (Exception e) {
                
            }
        }
    }

    @Override
    public void run() {
        manejarConexiones();
    }
    
}