/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mensajes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class MensajesClase {

    public static String conectar() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "conexion");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    public static String desconectar() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "desconexion");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    public static String listUpdate() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "listUpdate");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    @Deprecated
    public static Socket pedirSocketSR(Socket elSocket) {
        //Pide el puerto del socketSR al alumno y lo devuelve
        try {
            String msg;
            int puerto;
            String ip;
            Socket socketSR;
            //Creo el mensaje a enviar
            msg = GenTools.XMLGenerator("<tipo>", "pedirSocketSR");
            msg = GenTools.XMLWrapper("msg", msg);
            //Creo los flujos de E/S
            PrintWriter out = new PrintWriter(new OutputStreamWriter(elSocket.getOutputStream(), "UTF-8"), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(elSocket.getInputStream(), "UTF-8"));
            //Envío el mensaje al alumno
            out.println(msg);
            //Espero a que me respondan el puerto al cual debo conectarme para el socketSR
            puerto = Integer.parseInt(in.readLine());
            //Saco la ip del alumno del socket
            ip = elSocket.getRemoteSocketAddress().toString();
            //Establezco la conexión en el puerto que recibí
            socketSR = new Socket(ip, puerto);
            //Cierro los flujos de E/S
            out.close();
            in.close();
            //Devuelvo el SocketSR
            return socketSR;
        } catch (IOException ex) {
            Logger.getLogger(MensajesClase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        //Si todo falló devuelvo null
        return null;
    }

    @Deprecated
    public static void desconectar(Socket socketSR) {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socketSR.getOutputStream(), "UTF-8"), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketSR.getInputStream(), "UTF-8"));

            //Envío mensaje de desconexión al alumno
            String msg = GenTools.XMLGenerator("tipo", "desconectar");
            msg = GenTools.XMLWrapper("msg", msg);
            out.println(msg);

            //Cierro los flujos E/S
            out.close();
            in.close();
            //Cierro el socket
            socketSR.close();
        } catch (IOException ex) {
            Logger.getLogger(MensajesClase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
