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

    private String nombre;
    private String apellido;
    private String curso;
    private final static String FILENAME = "config.ini";
    private final static String SECCION = "configuracion";

    public Configuracion() {
        try {
            nombre = leer(FILENAME,SECCION,"nombre");
            apellido = leer(FILENAME,SECCION,"apellido");
            curso = leer(FILENAME,SECCION,"curso");
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
        
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getCurso() {
        return curso;
    }
    
    public void guardar() throws IOException {
        Wini ini = new Wini(new File(FILENAME));
        
        ini.put(SECCION,"nombre",nombre);
        ini.put(SECCION,"apellido",apellido);
        ini.put(SECCION,"curso",curso);
        ini.store();
    }
    
    static String leer(String filename, String seccion, String campo) throws IOException
    {
        Wini ini = new Wini(new File(filename));
        return ini.get(seccion, campo, String.class);
    }
    
    static void guardar(String filename, String seccion, String campo, String valor) throws IOException 
    {
        Wini ini = new Wini(new File(filename));

        ini.put(seccion,campo,valor);
        ini.store();
    }
    
}
