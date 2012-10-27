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
    String nombre;
    String descripcion;
    Boolean tieneContrasenia;
    String ip;
    int puerto;

    //Constructor
    public InfoClase(String stringInfoClase) {
        
        //Parseo el string para obtener información sobre la clase
        //usando el método privado parseasInfoClase().
        //Estructura: [ip,puerto,nombre,tieneContrasenia,descripción]
        String[] vectorInfoClase = this.parsearInfoClase(stringInfoClase);
        
        this.ip = vectorInfoClase[0];
        this.puerto = Integer.parseInt(vectorInfoClase[1]);
        this.nombre = vectorInfoClase[2];
        this.tieneContrasenia = Boolean.parseBoolean(vectorInfoClase[3]);
        this.descripcion = vectorInfoClase[4];
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
    
    public void imprimeInfo(){
                    System.out.println("Ip: " + ip);
            System.out.println("Puerto: " + puerto);
            System.out.println("Nombre: " + nombre);
                       if (tieneContrasenia){
                System.out.println("Contraseña: Sí.");
            } else {
                System.out.println("Contraseña: No.");
            }
            System.out.println("Descripcion: " + descripcion);
    }
    
    private String[] parsearInfoClase(String infoClaseString) {
        //Creo vector para almacenar información sobre la clase.
        //Estructura: [ip,puerto,nombre,tieneContrasenia,descripción]
        String[] vectorInfoClase;

        //Configuro el delimitador entre token y token.
        String delims = "[,]+";
        //Lleno el vector con información de la clase.
        vectorInfoClase = infoClaseString.split(delims);

        return vectorInfoClase;
    }
}
