/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import netcomp.Clase;
import netcomp.GUI.VtnClaseMaestro;
import netcomp.GUI.VtnCrearClase;

/**
 *
 * @author zerg
 */
public class AccionCrearClaseMaestro extends AbstractAction {

    public static VtnClaseMaestro vtnClaseMaestro;
    VtnCrearClase vtnCrearClase;
    private String nombre;
    private String contrasenia;
    private String descripcion;
    private String profesor;

    public AccionCrearClaseMaestro(VtnCrearClase parent) {
        vtnCrearClase = parent;
    }

    private void prepararDatos() {
        //try {
        this.nombre = vtnCrearClase.getCrearClaseNombre().getText();
        /*char[] chars = vtnCrearClase.getCrearClaseContrasenia().getPassword();
         byte[] bytes = new byte[chars.length];
         for (int i = 0; i < chars.length; i++) {
         bytes[i] = (byte) chars[i];
         //bytes[i * 2 + 1] = (byte) chars[i];
         }
         this.contrasenia = new String(bytes,"UTF-8");*/
        this.contrasenia = new String(vtnCrearClase.getCrearClaseContrasenia().getPassword());
        this.descripcion = vtnCrearClase.getCrearClaseDescripcion().getText();
        this.profesor = vtnCrearClase.getCrearClaseProfesor().getText();
        /*} catch (UnsupportedEncodingException ex) {
         Logger.getLogger(AccionCrearClaseMaestro.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Cierro la ventana "Crear Clase"
        vtnCrearClase.dispose();
        //Preparo los datos para instanciar el objeto
        prepararDatos();
        //Instancio el objeto "Clase"
        Clase clase = new Clase(nombre, contrasenia, profesor, descripcion, vtnClaseMaestro);
        System.out.println("Puerto de escucha: " + clase.getPuerto());
        vtnClaseMaestro = new VtnClaseMaestro(clase);
        //Paso el objeto Clase a la ventana "Clase Maestro"
        clase.setVentana(vtnClaseMaestro);
        //vtnClaseMaestro.setClase(clase);
        //Configuro el título de la ventana
        vtnClaseMaestro.setTitle("Clase - " + clase.getNombre());
        //Muestro la ventana "Clase Maestro"
        vtnClaseMaestro.setVisible(true);
        //Inicio el anunciador de la clase
        vtnClaseMaestro.iniciar();
        //Configuro la operación por defecto al cerrar la ventana "Clase Maestro"
        vtnClaseMaestro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}
