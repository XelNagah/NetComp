/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mensajes;

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

    public static String listaAlumnosUpdate() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "listaAlumnosUpdate");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }
    
    public static String listaArchivosUpdate() {
        String mensaje;
        mensaje = GenTools.XMLGenerator("tipo", "listaArchivosUpdate");
        mensaje = GenTools.XMLWrapper("msg", mensaje);
        return mensaje;
    }
}
