/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zerg
 */
public class MensajesAlumno {

    public static Socket enviarSocketSR(Socket elSocket) {
        //Envía el puerto de escucha del manejador Send-Receive del alumno
        //a la clase, establece la conexión y devuelve el socketSR
        try {
            int elPuerto = GenTools.findFreePort();
            //Creo un nuevo serverSocket para escuchar la conexión entrante desde la clase
            ServerSocket serverSocket = new ServerSocket(elPuerto);
            //Creo streams de salida y entrada
            PrintWriter out = new PrintWriter(new OutputStreamWriter(elSocket.getOutputStream(), "UTF-8"), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(elSocket.getInputStream(), "UTF-8"));
            //Envío el puerto en el que voy a estar escuchando
            out.println(Integer.toString(elPuerto));
            //Creo el server socket para aceptar la conexión y crear el socket Send-Receive
            Socket socketSR = serverSocket.accept();
            //Cierro los streams
            out.close();
            in.close();
            //Devuelvo el nuevo socket
            return socketSR;
        } catch (IOException ex) {
            Logger.getLogger(MensajesAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Si todo falló devuelvo null
        return null;
    }

    public static void desconectar(Socket socketSR, Socket socketRS) {
        try {
            String msg;
            msg = GenTools.XMLGenerator("tipo", "desconectar");
            msg = GenTools.XMLWrapper("msg", msg);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketSR.getInputStream(), "UTF-8"));
            out.println(msg);
            if ("desconectar".equals(GenTools.XMLParser("tipo", in.readLine())) ) {
                out.println("bye.");
                in.close();
                out.close();
                socketRS.close();
            } else {
                System.out.println("No recibí el cierre de conexión");
            }
        } catch (IOException ex) {
            Logger.getLogger(MensajesAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
