/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoRS implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    VtnClaseAlumno ventana;
    Alumno alumno;
    ServerSocket socketEscucha;
    Socket socketRS;
    int puerto;
    ConexionAlumno conexion;

    public ManejadorAlumnoRS(Alumno elAlumno, int elPuerto, ConexionAlumno laConexion) {
        alumno = elAlumno;
        puerto = elPuerto;
        conexion = laConexion;
        System.out.println("ManejadorAlumnoRS Creado");
    }

    private void manejar() {
        try {
            System.out.println("ManejadorRS alumno Corriendo");
            socketEscucha = new ServerSocket(puerto);
            System.out.println("puertoRS = " + puerto);
            socketRS = socketEscucha.accept();
            System.out.println(socketRS);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (corriendo) {
            
            try {
                Thread.sleep(periodo);
                BufferedReader in = new BufferedReader(new InputStreamReader(socketRS.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketRS.getOutputStream(),"UTF-8"), true);
                String mensaje;
                mensaje = in.readLine();
                System.out.println(mensaje);
                puerto = Integer.parseInt(in.readLine());
                System.out.println(mensaje);

                
            } catch (IOException ex) {
                Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
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

    private void conectar() {
    }
}
