/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.BuscadorDeClases;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import netcomp.GenTools;
import netcomp.InfoClase;

/**
 *
 * @author zerg
 */
public class Escuchador implements Runnable {

    ArrayList<InfoClase> clases = new ArrayList<InfoClase>();
    Boolean corriendo;
    public String[] columnas = {"Nombre de la Clase",
        "Profesor",
        "Descripción",
        "Contraseña"};
    DefaultTableModel customDataModel;
    ManejadorDeClases elManejador = new ManejadorDeClases();
    Thread elManejadorThread = new Thread(elManejador);

    public void setCustomDataModel(DefaultTableModel elObjeto) {
        elManejador.setCustomDataModel(elObjeto);
        customDataModel = elObjeto;
    }

    public Escuchador() {
        elManejador.setLasClases(clases);
    }

    synchronized public void escuchar() {
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
            //Comiendo el Manejador de Clases
            elManejadorThread.start();
            //Creo el bucle de operación
            while (corriendo) {
                try {
                    //Configuro la espera del bucle
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    //Si me han interrumpido, dejo de correr
                    corriendo = false;
                    //cierro el socket
                    socket.close();
                    //e interrumpo el bucle
                    break;
                }
                //Recibo un paquete
                socket.receive(packet);
                //Convierto el paquete a String.
                String paquete = new String(packet.getData(),"UTF-8");
                //Hago algo con el paquete
                manejarPaquete(paquete);
            }
            //Al salir del bucle - reinicio la colección de clases
            clases = null;
            //e interrumpo el manejador de clases.
            elManejadorThread.interrupt();
        } catch (SocketException e) {
        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        //Restablezco condición de funcionamiento
        corriendo = true;
        //Ejecuto el método escuchar
        escuchar();
    }

    public ArrayList<InfoClase> getClases() {
        return clases;
    }

    private void manejarPaquete(String paquete) {
        //Veo si es un paquete válido
        if (validarPaquete(paquete)) {
            //Si lo es, creo una nueva Clase con los datos del paquete.
            manejarClase(paquete);
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

    private void manejarClase(String paquete) {
        //Creo una bandera
        Boolean flag = true;
        //Obtengo el ID Único del paquete
        String nuevoId = GenTools.XMLParser("UUID", paquete);
        //Comparo el ID con las clases ya agregadas
        for (Iterator<InfoClase> it = clases.iterator(); it.hasNext();) {
            InfoClase laClase = it.next();
            //Si alguna clase agregada tiene el mismo id
            if (laClase.getHash().equals(nuevoId)) {
                //Configuro la bandera en falso
                flag = false;
                //y reinicio el contador de timeout de la clase
                //ya que aún se está anunciando
                laClase.resetTimeOut();
            } else {
            }
        }
        //Si ninguna tenía el mismo ID (La bandera está en true)
        if (flag) {
            //Creo la clase
            InfoClase laClase = new InfoClase(paquete);
            //Agrego la clase a la lista
            agregarClase(laClase);
        } else {
            //Si no, imprimo un mensaje
            //System.out.println("Clase ya agregada.");
        }

    }

    private void agregarClase(InfoClase laClase) {
        //Agrego la clase a la lista de clases
        clases.add(laClase);
        //Refresco la tabla
        customDataModel.fireTableDataChanged();
        //Muestro información de clases para Debug
        //imprimirClases();
    }

    private void imprimirClases() {
        //Itero a través de las clases
        for (Iterator<InfoClase> it = clases.iterator(); it.hasNext();) {
            //e imprimo la información de la misma.
            it.next().imprimeInfo();
        }
    }
}
