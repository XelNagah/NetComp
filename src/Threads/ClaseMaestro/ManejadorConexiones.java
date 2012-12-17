/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import Mensajes.TipoEventosGUI;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.Clase;
import netcomp.ConexionClase;

/**
 *
 * @author zerg
 */
public class ManejadorConexiones implements Runnable {

    boolean corriendo;
    private long periodo = 300;
    private int puerto;
    private Clase clase;
    private Set<ConexionClase> conexiones;
    private Socket socketAlumno;

    public ManejadorConexiones(int elPuerto, Clase laClase) {
        this.puerto = elPuerto;
        //alumnos = listaAlumnos;
        clase = laClase;
        conexiones = new ConcurrentSkipListSet<ConexionClase>(new Comparator<ConexionClase>() {
            @Override
            public int compare(ConexionClase t, ConexionClase t1) {
                if (t.equals(t1)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    private void manejarConexiones() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(puerto);
            while (corriendo) {
                try {
                    //Defino el sleep
                    Thread.sleep(periodo);
                    //Espero una conexión y acepto las entrantes.
                    socketAlumno = serverSocket.accept();
                    //Al aceptarla, creo el thread que va a manejar ese alumno.
                    //addConexion(new ConexionClase(socketAlumno, clase));
                    new ConexionClase(socketAlumno, clase);
                    //Olvido el socket de este alumno, ya que se lo delegué al manejador 
                    //y vuelvo a escuchar conexiones entrantes.
                    socketAlumno = null;
                } catch (IOException ex) {
                    //Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
                    socketAlumno.close();
                } catch (InterruptedException ex) {
                    corriendo = false;
                    try {
                        socketAlumno.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorConexiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<ConexionClase> getConexiones() {
        return conexiones;
    }

    public void addConexion(ConexionClase laConexion) {
        conexiones.add(laConexion);
    }

    public void delConexion(ConexionClase laConexion) {
        conexiones.remove(laConexion);
    }

    public void enviarEventoGeneral(TipoEventosGUI elEvento) {
        for (Iterator<ConexionClase> it = this.conexiones.iterator(); it.hasNext();) {
            ConexionClase laConexion = it.next();
            laConexion.enviarEvento(elEvento);
        }
    }
    
    public void enviarPantalla(TipoEventosGUI elEvento) {
        for (Iterator<ConexionClase> it = this.conexiones.iterator(); it.hasNext();) {
            ConexionClase laConexion = it.next();
            if (laConexion.getRecibePantalla()){
                laConexion.enviarPantalla(elEvento);
            }
        }
    }

    public void enviarEvento(TipoEventosGUI evt, Alumno elAlumno) {
        //Dirigido
        for (Iterator<ConexionClase> it = conexiones.iterator(); it.hasNext();) {
            ConexionClase laConexion = it.next();
            if (laConexion.getAlumno().equals(elAlumno)) {
                laConexion.enviarEvento(evt);
            }
        }
    }

    public void actualizarListaAlumnos() {
        TipoEventosGUI eventoActualizarListaGeneral = new TipoEventosGUI(TipoEventosGUI.listaAlumnosUpdate);
        enviarEventoGeneral(eventoActualizarListaGeneral);
    }

    public void actualizarListaArchivos() {
        TipoEventosGUI eventoActualizarArchivosGeneral = new TipoEventosGUI(TipoEventosGUI.listaArchivosUpdate);
        enviarEventoGeneral(eventoActualizarArchivosGeneral);
    }

    public void actualizarListaArchivos(Alumno elAlumno) {
        TipoEventosGUI eventoActualizarArchivosGeneral = new TipoEventosGUI(TipoEventosGUI.listaArchivosUpdate);
        enviarEvento(eventoActualizarArchivosGeneral, elAlumno);
    }

    @Override
    public void run() {
        corriendo = true;
        manejarConexiones();
    }

    public boolean enviarPantalla() {
        boolean enviarPantalla = false;
        for (Iterator<ConexionClase> it = conexiones.iterator(); it.hasNext();) {
            ConexionClase laConexion = it.next();
            if (laConexion.getRecibePantalla() == true) {
                enviarPantalla =true;
                break;
            }
        }
        return enviarPantalla;
    }
}