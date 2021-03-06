/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import Mensajes.MensajesAlumno;
import Mensajes.TipoEventosGUI;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;
import netcomp.GenTools;
import netcomp.InfoClase;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoSR implements Runnable {

    boolean corriendo;
    long periodo = 300;
    VtnClaseAlumno ventana = AccionCrearClaseAlumno.vtnClaseAlumno;
    Alumno alumno;
    Socket socketSR;
    int puertoRS;
    String password;
    ConexionAlumno conexion;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    BlockingQueue<TipoEventosGUI> queue;

    public ManejadorAlumnoSR(InfoClase laClase, ConexionAlumno laConexion) {
        try {
            alumno = laConexion.getAlumno();
            String ip = laClase.getIp();
            int puerto = laClase.getPuerto();
            socketSR = new Socket(ip, puerto);
            oos = new ObjectOutputStream(socketSR.getOutputStream());
            ois = new ObjectInputStream(socketSR.getInputStream());
            oos.flush();
            conexion = laConexion;
            puertoRS = GenTools.findFreePort();
            queue = new ArrayBlockingQueue<TipoEventosGUI>(50);
            password = null;
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No pude conectar al socketSR - Alumno");
        }
    }

    public ManejadorAlumnoSR(InfoClase laClase, ConexionAlumno laConexion, String elPassword) {
        try {
            alumno = laConexion.getAlumno();
            String ip = laClase.getIp();
            int puerto = laClase.getPuerto();
            socketSR = new Socket(ip, puerto);
            oos = new ObjectOutputStream(socketSR.getOutputStream());
            ois = new ObjectInputStream(socketSR.getInputStream());
            oos.flush();
            conexion = laConexion;
            puertoRS = GenTools.findFreePort();
            queue = new ArrayBlockingQueue<TipoEventosGUI>(50);
            password = elPassword;
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No pude conectar al socketSR - Alumno");
        }
    }

    public int getPuertoRS() {
        return puertoRS;
    }

    public BlockingQueue<TipoEventosGUI> getQueue() {
        return queue;
    }

    private void manejar() {
        conectar();
        while (corriendo) {
            try {
                //Recibir instruccion del GUI
                TipoEventosGUI elEvento = queue.take();
                Integer tipoMsg = elEvento.getEventId();

                switch (tipoMsg) {
                    case TipoEventosGUI.pedirArchivo:
                        pedirArchivo((File) elEvento.getParams().get(0), (File) elEvento.getParams().get(1));
                        break;
                    case TipoEventosGUI.verPantalla:
                        verPantalla();
                        break;
                    case TipoEventosGUI.stopCompartirPantalla:
                        noVerPantalla();
                        break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void conectar() {
        try {
            if (password != null) {
                String mensaje = MensajesAlumno.password(password);
                oos.writeObject(mensaje);
                if ((Boolean) ois.readObject()) {
                    establecerConexion();
                } else {
                    oos.writeObject("bye.");
                    socketSR.close();
                    conexion.fallaContrasenia();
                }
            } else {
                establecerConexion();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirmarConexion() {
        try {
            oos.writeObject("Done");
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            //PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            String mensaje;
            mensaje = MensajesAlumno.desconectar();
            if (!socketSR.isClosed()) {
                oos.writeObject(mensaje);
                oos.writeObject("bye.");
                oos.flush();
                //socketSR.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pedirArchivo(File elArchivo, File guardarPath) {
        try {
            String mensaje;
            int puertoConexion = GenTools.findFreePort();
            ServerSocket serverSocket = new ServerSocket(puertoConexion);
            new Thread(new ManejadorRecvFiles(serverSocket, guardarPath)).start();
            mensaje = MensajesAlumno.pedirArchivo(puertoConexion);
            if (!socketSR.isClosed()) {
                try {
                    oos.writeObject(mensaje);
                    //Pido el archivo
                    oos.writeObject(elArchivo);
                    oos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verPantalla() {
        try {
            String mensaje = MensajesAlumno.verPantalla();
            oos.writeObject(mensaje);
            if ((Boolean) ois.readObject()) {
                int elPuerto = new Integer(ois.readObject().toString());
                ManejadorRecvScreen manejador = new ManejadorRecvScreen(socketSR.getInetAddress(), elPuerto, ventana);
                Thread manejadorThread = new Thread(manejador);
                conexion.setManejadorRecvScreen(manejador);
                conexion.setManejadorRecvScreenThread(manejadorThread);
                manejadorThread.start();
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "La pantalla no está compartida.");
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void noVerPantalla() {
        try {
            String mensaje = MensajesAlumno.noVerPantalla();
            oos.writeObject(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAlumnoSR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }

    private void establecerConexion() throws IOException {
        //Genero el mensaje de conexión
        String mensaje = MensajesAlumno.conectar(alumno, puertoRS);
        //Creo el ServerSocket RS de alumno, en el puerto que envío a la clase para que se conecte.
        ServerSocket serverSocket = new ServerSocket(puertoRS);
        //Creo el Manejador
        ManejadorAlumnoRS unManejador = new ManejadorAlumnoRS(alumno, serverSocket, conexion);
        //Asigno el manejador a la conexión alumno y lo inicio
        conexion.setManejadorRS(unManejador);
        ventana.setConexionAlumno(conexion);
        //Envío el mensaje de conexión a la clase.
        oos.writeObject(mensaje);
        oos.flush();
        conexion.setConectado(true);
    }
}
