/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import Mensajes.TipoEventosGUI;
import Threads.ClaseMaestro.ManejadorClaseRS;
import Threads.ClaseMaestro.ManejadorClaseSR;
import Threads.ClaseMaestro.ManejadorSendScreen;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    boolean recibePantalla;
    ManejadorSendScreen manejadorSendScreen;

    public ConexionClase(Socket elSocketRS, Clase laClase) {
        //Establezco mi socket Receive-Send
        socketRS = elSocketRS;
        //Seteo la clase
        clase = laClase;
        //Creo el manejador Receive-Send
        manejadorClaseRS = new ManejadorClaseRS(socketRS, clase, this);
        //Creo el hilo del manejador Receive-Send
        manejadorClaseRSThread = new Thread(manejadorClaseRS);
        recibePantalla = false;
        comenzarThreads();
    }

    public boolean getRecibePantalla() {
        return recibePantalla;
    }

    public void setRecibePantalla(boolean recibePantalla) {
        this.recibePantalla = recibePantalla;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    private void comenzarThreads() {
        manejadorClaseRSThread.start();
    }

    public void conectarSR(int elPuerto) {
        manejadorClaseSR = new ManejadorClaseSR(socketRS, clase, this);
        manejadorClaseSR.setPuertoSR(elPuerto);
        manejadorClaseSRThread = new Thread(manejadorClaseSR);
        manejadorClaseSRThread.start();
    }

    public void comenzarEnviarPantalla(ServerSocket elServerSocket) {
        manejadorSendScreen = new ManejadorSendScreen(elServerSocket, this);
        new Thread(manejadorSendScreen).start();
    }

    public void enviarPantalla(TipoEventosGUI elEvento) {
        manejadorSendScreen.getQueue().offer(elEvento);
    }

    public void listaAlumnosUpdate(ArrayList<Alumno> losAlumnos) {
        manejadorClaseSR.listaAlumnosUpdate(losAlumnos);
    }

    public void enviarEvento(TipoEventosGUI evt) {
        if (manejadorClaseRS != null) {
            manejadorClaseSR.getQueue().offer(evt);
        }
    }

    public void desconectar() {
        manejadorClaseSR.desconectar();
    }
}