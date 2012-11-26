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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.Clase;
import netcomp.ConexionClase;
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
                BufferedReader in = new BufferedReader(new InputStreamReader(socketRS.getInputStream(), "UTF-8"));
                String linea;
                linea = in.readLine();
                System.out.println(linea);
                linea = in.readLine();
                puertoSR = Integer.parseInt(linea);
                System.out.println(puertoSR);
                conexion.conectarSR(puertoSR);
                while (!"bye.".equals(linea = in.readLine())) {
                    //manejarMensaje(linea);
                    System.out.println(linea);
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
        } else if ("desconectar".equals(tipo)) {
            MensajesClase.desconectar(socketRS);
        } else {
            System.out.println(elMensaje);
        }
    }

    private void manejarConexion(String elMensaje) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socketRS.getInputStream(), "UTF-8"));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socketRS.getOutputStream(), "UTF-8"), true);
        
        GenTools.XMLParser("", elMensaje);
        
    }

    private void manejarPedidoArchivo() {
    }
}
