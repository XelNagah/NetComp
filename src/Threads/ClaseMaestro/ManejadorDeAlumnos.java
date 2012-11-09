/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.GUI.VtnClaseMaestro;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class ManejadorDeAlumnos implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    VtnClaseMaestro ventana;
    ArrayList<Alumno> alumnos;
    private Socket server;

    public ManejadorDeAlumnos(Socket server, ArrayList<Alumno> alumnos) {
        this.server = server;
        this.alumnos = alumnos;
    }

    private void manejar() {
        while (corriendo) {
            try {
                Thread.sleep(periodo);
                //Hacer algo
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream(),"UTF-8"));
                String linea;
                while (!"bye.".equals(linea = in.readLine())) {
                    manejarMensaje(linea);
                }
                server.close();
                corriendo = false;
            } catch (IOException ex) {
                Logger.getLogger(ManejadorDeAlumnos.class.getName()).log(Level.SEVERE, null, ex);
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

    private void manejarMensaje(String elMensaje) {
        String tipo = GenTools.XMLParser("tipo", elMensaje);
        if ("conexion".equals(tipo)) {
            try {
                manejarConexion();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("Pedir archivo".equals(tipo)) {
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
            unAlumno.setSocket(server);
            
            System.out.println(unAlumno.getNombre());
            System.out.println(unAlumno.getApellido());
            System.out.println(unAlumno.getSocket());
            alumnos.add(unAlumno);

            System.out.println("Agregué el alumno " + unAlumno.getNombre() + " "
                    + unAlumno.getApellido() + " a la lista de alumnos");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorDeConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejarPedidoArchivo() {
    }
}
