/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        configFileExists();
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

    public static void guardarFileNotThere() throws IOException {
        //File archivoIn = new File(ClassLoader.getSystemResource("config.ini").getFile());
        InputStream is = ClassLoader.getSystemResourceAsStream("config.ini");
        InputStreamReader isr = new InputStreamReader(is);
        File archivoOut = new File(FILENAME);
        FileWriter out = new FileWriter(archivoOut);
        //FileReader in = new FileReader(ClassLoader.getSystemResource("config.ini").getFile());

        int c;

        while ((c = isr.read()) != -1) {
            out.write(c);
        }
        out.close();
        isr.close();
        is.close();
        //in.close();
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
            /*try {
             f.createNewFile();
             return Integer.toString(1);
             } catch (IOException ex) {
             Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            return Integer.toString(1);
        }
        Wini ini = new Wini(new File(filename));
        return ini.get(seccion, campo, String.class);
    }

    static void guardar(String filename, String seccion, String campo, String valor) throws IOException {
        Wini ini = new Wini(new File(filename));

        ini.put(seccion, campo, valor);
        ini.store();
    }

    public static void configFileExists() {
        File f = new File(System.getProperty("user.dir") + "/" + FILENAME);
        if (!f.exists()) {
            try {
                guardarFileNotThere();
            } catch (IOException ex) {
                Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
