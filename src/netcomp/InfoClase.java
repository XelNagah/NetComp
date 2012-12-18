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
    String ip;
    int puerto;
    String nombre;
    Boolean tieneContrasenia;
    String profesor;
    String descripcion;
    String hash;
    private int timeOut;
    final int period = 6;

    //Constructor
    public InfoClase(String stringInfoClase) {
        ip = GenTools.XMLParser("ip", stringInfoClase);
        puerto = Integer.parseInt(GenTools.XMLParser("puerto", stringInfoClase));
        nombre = GenTools.XMLParser("nombre", stringInfoClase);
        tieneContrasenia = Boolean.parseBoolean(GenTools.XMLParser("boolpass", stringInfoClase));
        profesor = GenTools.XMLParser("profesor", stringInfoClase);
        descripcion = GenTools.XMLParser("descripcion", stringInfoClase);
        hash = GenTools.XMLParser("UUID", stringInfoClase);
        timeOut = period;
    }

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getTieneContrasenia() {
        return tieneContrasenia;
    }

    public void setTieneContrasenia(Boolean tieneContrasenia) {
        this.tieneContrasenia = tieneContrasenia;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHash() {
        return hash;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void updateTimeOut(int i) {
        timeOut -= i;
    }

    public void resetTimeOut() {
        timeOut = period;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }
}