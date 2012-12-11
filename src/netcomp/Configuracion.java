/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Wini;

/**
 *
 * @author zerg
 */
public class Configuracion {

    private static String nombre;
    private static String apellido;
    private static String curso;
    private final static String FILENAME = "config.ini";
    private final static String SECCION = "configuracion";

    public Configuracion() {
        File f = new File("./" + FILENAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            nombre = leer(FILENAME, SECCION, "nombre");
            apellido = leer(FILENAME, SECCION, "apellido");
            curso = leer(FILENAME, SECCION, "curso");
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void inicializar() {
        try {
            nombre = leer(FILENAME, SECCION, "nombre");
            apellido = leer(FILENAME, SECCION, "apellido");
            curso = leer(FILENAME, SECCION, "curso");
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setNombre(String nombre) {
        Configuracion.nombre = nombre;
    }

    public static void setApellido(String apellido) {
        Configuracion.apellido = apellido;
    }

    public static void setCurso(String curso) {
        Configuracion.curso = curso;
    }

    public static String getNombre() {
        return nombre;
    }

    public static String getApellido() {
        return apellido;
    }

    public static String getCurso() {
        return curso;
    }

    public static String primeraVez() {
        try {
            return leer(FILENAME, SECCION, "primeraVez");
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void guardar() throws IOException {
        Wini ini = new Wini(new File(FILENAME));

        ini.put(SECCION, "primeraVez", "0");
        ini.put(SECCION, "nombre", nombre);
        ini.put(SECCION, "apellido", apellido);
        ini.put(SECCION, "curso", curso);
        ini.store();
    }

    static String leer(String filename, String seccion, String campo) throws IOException {
        File f = new File("./" + FILENAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
                return Integer.toString(1);
            } catch (IOException ex) {
                Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Wini ini = new Wini(new File(filename));
        return ini.get(seccion, campo, String.class);
    }

    static void guardar(String filename, String seccion, String campo, String valor) throws IOException {
        Wini ini = new Wini(new File(filename));

        ini.put(seccion, campo, valor);
        ini.store();
    }
}
