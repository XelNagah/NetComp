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
import java.util.Collection;

/**
 *
 * @author zerg
 */
public class Escuchador implements Runnable {

    Collection<Clase> clases;

    public Escuchador() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(5008, InetAddress.getByName("0.0.0.0"));

            socket.setBroadcast(true);
            System.out.println("Listen on " + socket.getLocalAddress()
                    + " from " + socket.getInetAddress() + " port "
                    + socket.getBroadcast());
            byte[] buf = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length);
            sendPacket.setAddress(InetAddress.getByName("255.255.255.255"));
            sendPacket.setPort(5008);
            sendPacket.setData("Paquete Arbitrario".getBytes());

            System.out.println("Esperando Datos");
            while (true) {
                //socket.send(sendPacket);
                socket.receive(packet);
                System.out.println("Datos Recibidos: "
                        + new String(packet.getData()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }

        } catch (SocketException e) {
        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
