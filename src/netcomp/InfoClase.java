/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

/**
 *
 * @author zerg
 */
public class InfoClase {

    //Atributos
    public String nombre;
    String descripcion;
    Boolean tieneContrasenia;
    String hash;
    String ip;
    int puerto;

    //Constructor
    
    public InfoClase(String stringInfoClase) {
        this.ip = GenTools.XMLParser("ip", stringInfoClase);
        this.puerto = Integer.parseInt(GenTools.XMLParser("puerto", stringInfoClase));
        this.nombre = GenTools.XMLParser("nombre", stringInfoClase);
        this.tieneContrasenia = Boolean.parseBoolean(GenTools.XMLParser("boolpass", stringInfoClase));
        this.descripcion = GenTools.XMLParser("descripcion", stringInfoClase);
        this.hash = GenTools.XMLParser("UUID", stringInfoClase);
        //System.out.println("Nueva InfoClase.");
    }

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getTieneContrasenia() {
        return tieneContrasenia;
    }

    public void setTieneContrasenia(Boolean tieneContrasenia) {
        this.tieneContrasenia = tieneContrasenia;
    }

    public void imprimeInfo() {
        System.out.println("Ip: " + ip);
        System.out.println("Puerto: " + puerto);
        System.out.println("Nombre: " + nombre);
        if (tieneContrasenia) {
            System.out.println("Contraseña: Sí.");
        } else {
            System.out.println("Contraseña: No.");
        }
        System.out.println("Descripcion: " + descripcion);
        System.out.println("UUID: " + hash.toString());
    }
}