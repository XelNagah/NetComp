/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class ManejadorDeConexiones implements Runnable {

    private long periodo = 300;
    private int puerto;
    ArrayList<Alumno> alumnos;
    private Socket server;
    boolean corriendo;

    public ManejadorDeConexiones(int elPuerto, ArrayList<Alumno> listaAlumnos) {
        this.puerto = elPuerto;
        alumnos = listaAlumnos;
    }

    private void manejarConexiones() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(puerto);
            while (corriendo) {
                try {
                    Thread.sleep(periodo);

                    server = serverSocket.accept();

                    new Thread(new ManejadorDeAlumnos(server,alumnos)).start();
                    server = null;
                } catch (IOException ex) {
                    Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    corriendo = false;
                    try {
                        server.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void manejarMensaje(String elMensaje) {
        String tipo = GenTools.XMLParser("Tipo", elMensaje);
        if ("conexion".equals(tipo)) {
            try {
                manejarConexion();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ("Pedir archivo".equals(tipo)) {
            manejarPedidoArchivo();
        } else {
            System.out.println(elMensaje);
        }
    }

    private void manejarConexion() throws IOException {
        InputStream is = server.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        Alumno unAlumno;
        try {
            unAlumno = (Alumno) ois.readObject();

            System.out.println("Leí un Alumno");

            alumnos.add(unAlumno);

            System.out.println("Agregué el alumno " + unAlumno.getNombre() + " "
                    + unAlumno.getApellido() + " a la lista de alumnos");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejarPedidoArchivo() {
        System.out.println("Me pidieron un archivo.");
    }

    @Override
    public void run() {
        corriendo = true;
        manejarConexiones();
    }
}