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
import java.util.*;

/**
 *
 * @author zerg
 */
public class Escuchador implements Runnable {

    List<InfoClase> clases;
    Boolean corriendo;
    //String a partir del cual construyo el paquete.

    public Escuchador() {
    }

    public void escuchar() {
        clases = new ArrayList<InfoClase>();
        DatagramSocket socket;
        try {
            //Creo el socket
            socket = new DatagramSocket(5008, InetAddress.getByName("0.0.0.0"));
            //Establezco la capacidad de enviar broadcasts
            socket.setBroadcast(true);
            //configuro tamaño del buffer
            byte[] buf = new byte[512];
            //Establezco el tipo de paquete
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            //Creo el bucle de operación
            while (corriendo) {
                try {
                    //Configuro la espera del bucle
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    //Si me han interrumpido, corto el bucle
                    corriendo = false;
                    socket.close();
                    break;
                }
                //Recibo un paquete
                socket.receive(packet);
                //Convierto el paquete a String.
                String paquete = new String(packet.getData());
                //Hago Algo
                manejarPaquete(paquete);
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
        escuchar();
    }

    private void manejarPaquete(String paquete) {
        //Veo si es un paquete válido
        if (validarPaquete(paquete)) {
            //Si lo es, creo una nueva Clase con los datos del paquete.
            agregarClase(paquete);
            //System.out.println("Recibi paquete. Creo clase.");
        }
    }

    private boolean validarPaquete(String elPaquete) {
        //Verifico que el paquete tenga la estructura adecuada.
        //Estructura: [ip,puerto,nombre,tieneContrasenia,descripción]
        //Verifico que sea un paquete de mensaje
        if ((GenTools.XMLParser("msg", elPaquete)) != null) {
        }
        //Verifico que sea de tupo anuncio
        if ("anuncio".equals(GenTools.XMLParser("tipo", elPaquete))) {
            return true;
        } else {
            //Si no pasa la prueba, devuelvo falso.
            return false;
        }
    }

    private void imprimirClases() {
        for (Iterator<InfoClase> it = clases.iterator(); it.hasNext();) {
            it.next().imprimeInfo();
        }
    }

    private void agregarClase(String paquete) {
        //Creo una bandera
        Boolean flag = true;
        //Obtengo el ID Único del paquete
        String nuevoId = GenTools.XMLParser("UUID", paquete);
        //Comparo el ID con las clases ya agregadas
        for (Iterator<InfoClase> it = clases.iterator(); it.hasNext();) {
            //Si alguna clase agregada tiene el mismo id
            if (it.next().hash.equals(nuevoId)) {
                //Configuro la bandera en falso
                flag = false;
                //Salgo del loop.
                break;
            }
        }
        //Si ninguna tenía el mismo ID
        if (flag) {
            //Creo la clase
            InfoClase laClase = new InfoClase(paquete);
            //Agrego la clase a la lista de clases
            clases.add(laClase);
        } else {
            //Si no, imprimo un mensaje
            //System.out.println("Clase ya agregada.");
        }
    }
}
