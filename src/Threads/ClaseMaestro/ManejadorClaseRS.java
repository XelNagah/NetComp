/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public ManejadorClaseRS(Socket elSocketRS, Clase laClase, ConexionClase laConexion) {
        clase = laClase;
        socketRS = elSocketRS;
        conexion = laConexion;
    }

    public int getPuertoSR() {
        return puertoSR;
    }

    private void manejar() {
        try {
            ois = new ObjectInputStream(socketRS.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClaseRS.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (corriendo) {
            try {
                Thread.sleep(periodo);
                String linea;
                while (!"bye.".equals(linea = ois.readObject().toString())) {
                    manejarMensaje(linea);
                }
                socketRS.close();
                corriendo = false;
            } catch (IOException ex) {
                Logger.getLogger(ManejadorClaseRS.class.getName()).log(Level.SEVERE, null, ex);
                corriendo = false;
                break;
            } catch (ClassNotFoundException ex) {
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
        //Establezo la conexión Send-Receive con el alumno
        conexion.conectarSR(puertoSR);
        try {
            //Espero confirmación de establecimiento de la conexión
            String confirmacion = ois.readObject().toString();
            if (!"Done".equals(confirmacion)) {
                System.out.println("Recibo mensaje extraño.");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorClaseRS.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Creo un alumno
        Alumno elAlumno = new Alumno(nombre, apellido);
        elAlumno.setConexionClase(conexion);
        conexion.setAlumno(elAlumno);
        //Agrego el alumno a la clase.
        clase.addAlumno(elAlumno);
        clase.getManejadorDeConexiones().addConexion(conexion);
        clase.actualizarListaAlumnos();
    }

    private void manejarPedidoArchivo() {
    }

    private void manejarDesconectar() {
        Alumno elAlumno = conexion.getAlumno();
        //Borro el alumno de la clase.
        clase.delAlumno(elAlumno);
        clase.delConexion(conexion);
        clase.actualizarListaAlumnos();
    }
}
