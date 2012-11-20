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
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.Clase;
import netcomp.GUI.VtnClaseMaestro;
import netcomp.GUI.acciones.AccionCrearClaseMaestro;
import netcomp.GenTools;
import netcomp.MensajesClase;

/**
 *
 * @author zerg
 */
public class ManejadorClaseRS implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    Clase clase;
    VtnClaseMaestro ventana = AccionCrearClaseMaestro.vtnClaseMaestro;
    private Socket socketRS;

    public ManejadorClaseRS(Socket elSocketRS, Clase laClase) {
        clase = laClase;
        socketRS = elSocketRS;
    }

    private void manejar() {
        while (corriendo) {
            try {
                Thread.sleep(periodo);
                //Hacer algo
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
                manejarConexion();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("Pedir archivo".equals(tipo)) {
            manejarPedidoArchivo();
        } else if ("desconectar".equals(tipo)) {
            MensajesClase.desconectar(socketRS);
        } else {
            System.out.println(elMensaje);
        }
    }

    private void manejarConexion() throws IOException {
        InputStream is = socketRS.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        Alumno unAlumno;
        try {
            unAlumno = (Alumno) ois.readObject();
            System.out.println("Leí un Alumno");
            unAlumno.setSocket(socketRS);

            System.out.println(unAlumno.getNombre());
            System.out.println(unAlumno.getApellido());
            System.out.println(unAlumno.getSocket());
            clase.addAlumno(unAlumno);


            System.out.println("Agregué el alumno " + unAlumno.getNombre() + " "
                    + unAlumno.getApellido() + " a la lista de alumnos");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejarPedidoArchivo() {
    }
}
