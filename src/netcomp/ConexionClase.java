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
        manejadorClaseRS = new ManejadorClaseRS(socketRS, clase, this);
        //Creo el hilo del manejador Receive-Send
        manejadorClaseRSThread = new Thread(manejadorClaseRS);
        comenzarThreads();
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    private void comenzarThreads(){
        manejadorClaseRSThread.start();
    }
    
    public void conectarSR(int elPuerto){
        manejadorClaseSR = new ManejadorClaseSR(socketRS, clase);
        manejadorClaseSR.setPuertoSR(elPuerto);
        manejadorClaseSRThread = new Thread(manejadorClaseSR);
        manejadorClaseSRThread.start();
    }
    
    public void desconectar(){
        manejadorClaseSR.desconectar();
    }
}
