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

    public static void pedirArchivo() {
    }
}
