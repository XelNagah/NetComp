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
    String ip;
    static int puerto;
    static String nombreClase;
    Boolean tieneContrasenia;
    String descripcion;
    static Boolean corriendo;

    //Constructor
    public Anunciador(String ip, int puerto, String nombreClase, Boolean tieneContrasenia, String descripcion) {
        this.ip = ip;
        Anunciador.puerto = puerto;
        Anunciador.nombreClase = nombreClase;
        this.tieneContrasenia = tieneContrasenia;
        this.descripcion = descripcion;
    }

    //Método anunciar
    public void anunciar() {
        //Establezco que el hilo está corriendo
        DatagramSocket socket;
        try {
            //Creo el socket
            socket = new DatagramSocket(puerto, InetAddress.getByName("0.0.0.0"));
            //Establezco la capacidad de enviar broadcasts
            socket.setBroadcast(true);
            //configuro tamaño del buffer
            byte[] buf = new byte[512];
            //Creo el paquete
            //DatagramPacket packet = new DatagramPacket(buf, buf.length);
            //Creo el paquete
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length);
            //Configuro la dirección de broadcast
            sendPacket.setAddress(InetAddress.getByName("255.255.255.255"));
            //Configuro el puerto
            sendPacket.setPort(puerto);
            //Configuro los datos
            String tieneCont;
            if (tieneContrasenia){
                tieneCont = "Si";
            } else {
                tieneCont = "No";
            }
            //Armo el string de anuncio
            String paquete = ip + "," + puerto + "," + nombreClase + "," + tieneCont + "," + descripcion;
            //configuro los datos en el paquete
            sendPacket.setData(paquete.getBytes());

            //Creo el bucle
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
    
    public void run() {
        //Restablezco condición de funcionamiento
        corriendo = true;
        //Ejecuto el método anunciar
        anunciar();
    }
}
