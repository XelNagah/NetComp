/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
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
        this.nombre = vtnCrearClase.getCrearClaseNombre().getText();
        this.contrasenia = new String(vtnCrearClase.getCrearClaseContrasenia().getPassword());
        this.descripcion = vtnCrearClase.getCrearClaseDescripcion().getText();
        this.profesor = vtnCrearClase.getCrearClaseProfesor().getText();
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
        vtnClaseMaestro.setResizable(false);
        //Muestro la ventana "Clase Maestro"
        vtnClaseMaestro.setVisible(true);
        //Inicio el anunciador de la clase
        vtnClaseMaestro.iniciar();
        //Configuro la operación por defecto al cerrar la ventana "Clase Maestro"
        vtnClaseMaestro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}
