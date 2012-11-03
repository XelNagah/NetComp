/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public ManejadorDeConexiones(int elPuerto, ArrayList<Alumno> listaAlumnos) {
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
                System.out.println("Soy el server con el socket: " + server);

                //Paso el manejo de la conexión a un nuevo Thread.
                //ManejadorDeAlumnos manejador = new ManejadorDeAlumnos(server);
                //Thread t = new Thread(manejador);
                //t.start();

                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter out = new PrintWriter(server.getOutputStream());
                String linea;
                while (!"bye.".equals(linea = in.readLine())){
                    System.out.println("Me ha llegado la siguiente linea:" + linea);
                }
                server.close();

            } catch (Exception e) {
            }
        }
    }

    @Override
    public void run() {
        manejarConexiones();
    }
}