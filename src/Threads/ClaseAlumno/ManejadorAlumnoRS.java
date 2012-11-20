/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class ManejadorAlumnoRS implements Runnable {

    boolean corriendo = true;
    long periodo = 300;
    VtnClaseAlumno ventana;
    Alumno alumno;
    Socket socket;

    public ManejadorAlumnoRS(VtnClaseAlumno laVentana, Alumno elAlumno) {
        ventana = laVentana;
        alumno = elAlumno;
        socket = elAlumno.getSocket();
    }

    private void manejar() {
        while (corriendo) {
            ObjectOutputStream oos = null;
            try {
                Thread.sleep(periodo);
                //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //Creo un output stream writer
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
                out.println(GenTools.XMLGenerator("tipo", "conexion"));
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(alumno);
                oos.flush();
                out.println("bye.");
            } catch (IOException ex) {
                Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                corriendo = false;
                break;
            } finally {
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(ManejadorAlumnoRS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejar();
    }

    private void conectar() {
    }
}
