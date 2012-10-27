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

    static VtnClaseMaestro vtnClaseMaestro;
    VtnCrearClase vtnCrearClase;
    private String nombre;
    private String contrasenia;
    private String descripcion;

    public AccionCrearClaseMaestro(VtnCrearClase parent) {
        vtnClaseMaestro = new VtnClaseMaestro();
        vtnCrearClase = parent;
    }

    private void prepararDatos(){
        this.nombre = vtnCrearClase.getCrearNombreClase().getText();
        this.contrasenia = vtnCrearClase.getCrearContraseniaClase().getPassword().toString();
        this.descripcion = vtnCrearClase.getCrearDescripcionClase().getText();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //Cierro la ventana "Crear Clase"
        vtnCrearClase.dispose();
        //Instancio el objeto "Clase"
        prepararDatos();
        Clase clase = new Clase(nombre, descripcion, contrasenia);
        //Paso el objeto Clase a la ventana "Clase Maestro"
        vtnClaseMaestro.setClase(clase);
        //Muestro la ventana "Clase Maestro"
        vtnClaseMaestro.setVisible(true);
        //Inicio el anunciador de la clase
        vtnClaseMaestro.anunciar();
        //Configuro la operación por defecto al cerrar la ventana "Clase Maestro"
        vtnClaseMaestro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}
