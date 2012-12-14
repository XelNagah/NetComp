/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoRS implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    VtnClaseAlumno ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
    Alumno alumno;
    Socket socketRS;
    int puerto;
    ServerSocket socketEscucha;
    ConexionAlumno conexion;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public ManejadorAlumnoRS(Alumno elAlumno, ServerSocket socketEscucha, ConexionAlumno laConexion) {
        alumno = elAlumno;
        this.socketEscucha = socketEscucha;
        conexion = laConexion;
    }

    private void manejar() {
        try {
            socketRS = socketEscucha.accept();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ois = new ObjectInputStream(socketRS.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (corriendo) {

            try {
                Thread.sleep(periodo);
                String linea;
                while (!"bye.".equals(linea = ois.readObject().toString())) {
                    manejarMensaje(linea);
                }
                ois.close();
                socketRS.close();
                corriendo = false;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                corriendo = false;
                break;
            }
        }
        try {
            socketRS.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
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
            manejarConexion(elMensaje);
        } else if ("desconexion".equals(tipo)) {
            manejarDesconexion(elMensaje);
        } else if ("listaAlumnosUpdate".equals(tipo)) {
            manejarListaAlumnosUpdate(elMensaje);
        } else if ("listaArchivosUpdate".equals(tipo)) {
            manejarListaArchivosUpdate(elMensaje);
        } else {
            System.out.println(elMensaje);
        }
    }

    private void manejarConexion(String elMensaje) {
        conexion.confirmarConexion();
    }

    private void manejarListaAlumnosUpdate(String elMensaje) {
        try {
            ArrayList<Alumno> losAlumnos;
            try {
                losAlumnos = (ArrayList<Alumno>) ois.readObject();
                conexion.actualizarListaAlumnos(losAlumnos);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejarListaArchivosUpdate(String elMensaje) {
        try {
            ArrayList<File> losArchivos;
            try {
                losArchivos = (ArrayList<File>) ois.readObject();
                conexion.actualizarListaArchivos(losArchivos);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejarDesconexion(String elMensaje) {
        try {
            conexion.desconectar();
            socketEscucha.close();
            //System.out.println("La clase se ha cerrado.");
            ventana.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}