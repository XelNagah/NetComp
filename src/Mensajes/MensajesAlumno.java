/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mensajes;

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
        //try {
            //BASE64Encoder encoder = new BASE64Encoder();
            //String passEncoded = encoder.encodeBuffer(elPassword.getBytes("UTF-8"));

            String mensaje;
            mensaje = GenTools.XMLGenerator("tipo", "password");
            mensaje = GenTools.XMLAppend("password", elPassword, mensaje);
            mensaje = GenTools.XMLWrapper("msg", mensaje);
            return mensaje;
        /*} catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MensajesAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }*/

    }
}
