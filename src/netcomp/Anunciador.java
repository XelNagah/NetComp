/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author zerg
 */
public class Anunciador implements Runnable {

    //Campos
    private String ip;
    private static int puerto;
    private String profesor;
    private static String nombreClase;
    private Boolean boolPass;
    private String descripcion;
    static Boolean corriendo;
    //String a partir del cual construyo el paquete.
    private String dataString;

    //Constructor
    public Anunciador(String ip, int puerto, String nombreClase, Boolean tieneContrasenia, String descripcion) {
        this.ip = ip;
        Anunciador.puerto = puerto;
        Anunciador.nombreClase = nombreClase;
        this.boolPass = tieneContrasenia;
        this.descripcion = descripcion;
        this.dataString = generarString();
    }

    //Método anunciar
    public void anunciar() {
        //Establezco que el hilo está corriendo
        DatagramSocket socket;
        try {
            //Creo el socket
            socket = new DatagramSocket(GenTools.findFreePort(), InetAddress.getByName("0.0.0.0"));
            //Establezco la capacidad de enviar broadcasts
            socket.setBroadcast(true);
            //configuro tamaño del buffer
            byte[] buf = new byte[512];
            //Creo y establezco el tipo de paquete
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length);
            //Configuro la dirección de broadcast
            sendPacket.setAddress(InetAddress.getByName("255.255.255.255"));
            //Configuro el puerto
            sendPacket.setPort(puerto);
            //Armo el paquete
            String paquete = dataString;
            //configuro los datos en el paquete
            sendPacket.setData(paquete.getBytes());

            //Creo el bucle de operación
            while (corriendo) {
                //Envío el paquete
                socket.send(sendPacket);
                try {
                    //Configuro la espera del bucle
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    //Si me han interrumpido, corto el bucle
                    corriendo = false;
                    socket.close();
                }
            }
        } catch (SocketException e) {
        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        //Restablezco condición de funcionamiento
        corriendo = true;
        //Ejecuto el método anunciar
        anunciar();
    }

    private String generarString() {
        //Configuro los datos
        String tieneCont;
        if (boolPass) {
            tieneCont = "True";
        } else {
            tieneCont = "False";
        }
        String paquete = GenTools.XMLGenerator("tipo", "anuncio");
        paquete = GenTools.XMLAppend("ip", ip, paquete);
        paquete = GenTools.XMLAppend("puerto", Integer.toString(puerto), paquete);
        paquete = GenTools.XMLAppend("nombre", nombreClase, paquete);
        paquete = GenTools.XMLAppend("boolpass", tieneCont, paquete);
        paquete = GenTools.XMLAppend("descripcion", descripcion, paquete);
        paquete = GenTools.XMLWrapper("msg", paquete);

        return paquete;
    }
}
