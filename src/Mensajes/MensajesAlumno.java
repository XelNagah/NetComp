/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mensajes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.Alumno;
import netcomp.GenTools;

/**
 *
 * @author zerg
 */
public class MensajesAlumno {

    public static String msgValidacion() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "NetComp");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    public static String conectar(Alumno elAlumno, int elPuerto) {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "conexion");
        mensaje = GenTools.XMLAppend("nombre", elAlumno.getNombre(), mensaje);
        mensaje = GenTools.XMLAppend("apellido", elAlumno.getApellido(), mensaje);
        mensaje = GenTools.XMLAppend("puertoRS", Integer.toString(elPuerto), mensaje);
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    public static String desconectar() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "desconexion");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    public static String pedirArchivo(int elPuerto) {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "pedirArchivo");
        mensaje = GenTools.XMLAppend("puerto", Integer.toString(elPuerto), mensaje);
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }

    public static String password(String elPassword) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(elPassword.getBytes());
            String encryptedString = new String(messageDigest.digest());
            String mensaje;
            mensaje = GenTools.XMLGenerator("tipo", "password");
            mensaje = GenTools.XMLAppend("password", encryptedString, mensaje);
            mensaje = GenTools.XMLWrapper("msg", mensaje);
            return mensaje;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MensajesAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
