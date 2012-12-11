/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.Clase;
import netcomp.ConexionClase;
import netcomp.GUI.VtnClaseMaestro;
import netcomp.GUI.acciones.AccionCrearClaseMaestro;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class ManejadorClaseRS implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    Clase clase;
    VtnClaseMaestro ventana = AccionCrearClaseMaestro.vtnClaseMaestro;
    ConexionClase conexion;
    private Socket socketRS;
    private int puertoSR;

    public ManejadorClaseRS(Socket elSocketRS, Clase laClase, ConexionClase laConexion) {
        clase = laClase;
        socketRS = elSocketRS;
        conexion = laConexion;
    }

    public int getPuertoSR() {
        return puertoSR;
    }

    private void manejar() {
        while (corriendo) {
            try {
                Thread.sleep(periodo);
                //Hacer algo
                //conectarSR();
                BufferedReader in = new BufferedReader(new InputStreamReader(socketRS.getInputStream(), "UTF-8"));
                String linea;
                while (!"bye.".equals(linea = in.readLine())) {
                    manejarMensaje(linea);
                }
                socketRS.close();
                corriendo = false;
            } catch (IOException ex) {
                Logger.getLogger(ManejadorClaseRS.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setVentana(VtnClaseMaestro ventana) {
        this.ventana = ventana;
    }

    private void manejarMensaje(String elMensaje) {
        String tipo = GenTools.XMLParser("tipo", elMensaje);
        if ("conexion".equals(tipo)) {
            try {
                manejarConexion(elMensaje);
            } catch (IOException ex) {
                Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("Pedir archivo".equals(tipo)) {
            manejarPedidoArchivo();
        } else if ("desconexion".equals(tipo)) {
            manejarDesconectar();
        } else {
            System.out.println(elMensaje);
        }
    }

    private void manejarConexion(String elMensaje) throws IOException {
        //Leo los datos del paquete XML.
        String nombre = GenTools.XMLParser("nombre", elMensaje);
        String apellido = GenTools.XMLParser("apellido", elMensaje);
        puertoSR = Integer.parseInt(GenTools.XMLParser("puertoRS", elMensaje));
        //Creo un alumno
        Alumno elAlumno = new Alumno(nombre, apellido);
        elAlumno.setConexionClase(conexion);
        conexion.setAlumno(elAlumno);
        //Agrego el alumno a la clase.
        clase.addAlumno(elAlumno);
        ventana.actualizarVista(elAlumno, 0, clase.getAlumnos().size());
        //Establezo la conexión Send-Receive con el alumno
        conexion.conectarSR(puertoSR);
    }

    private void manejarPedidoArchivo() {
    }

    private void manejarDesconectar() {
        Alumno elAlumno = conexion.getAlumno();
        //Borro el alumno de la clase.
        clase.delAlumno(elAlumno);
        ventana.actualizarVista(elAlumno, 0, clase.getAlumnos().size());
        clase.getManejadorDeConexiones().getConexiones().remove(conexion);
        try {
            socketRS.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}