/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Threads.ClaseMaestro.ManejadorClaseRS;
import Threads.ClaseMaestro.ManejadorClaseSR;
import java.net.Socket;

/**
 *
 * @author zerg
 */
public class ConexionClase {

    Clase clase;
    Alumno alumno;
    Socket socketRS;
    Socket socketSR;
    ManejadorClaseRS manejadorClaseRS;
    Thread manejadorClaseRSThread;
    ManejadorClaseSR manejadorClaseSR;
    Thread manejadorClaseSRThread;

    public ConexionClase(Socket elSocketRS, Clase laClase) {
        //Establezco mi socket Receive-Send
        socketRS = elSocketRS;
        //Seteo la clase
        clase = laClase;
        //Creo el manejador Receive-Send
        manejadorClaseRS = new ManejadorClaseRS(socketRS, clase);
        //Creo el hilo del manejador Receive-Send
        manejadorClaseRSThread = new Thread(manejadorClaseRS);
        //Establezco mi socket Send-Receive pidiéndoselo al alumno
        socketSR = MensajesClase.pedirSocketSR(socketRS);
        //Creo el manejador Send-Receive
        manejadorClaseSR = new ManejadorClaseSR(socketRS, clase);
        //Creo el hilo del manejador Send-Receive
        manejadorClaseSRThread = new Thread(manejadorClaseSR);
        //Inicio los hilos
        comenzarThreads();
    }

    private void comenzarThreads(){
        manejadorClaseRSThread.start();
        manejadorClaseSRThread.start();
    }
}