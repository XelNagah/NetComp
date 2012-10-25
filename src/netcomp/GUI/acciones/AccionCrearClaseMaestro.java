/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import netcomp.Clase;
import netcomp.GUI.VtnClaseMaestro;
import netcomp.GUI.VtnCrearClase;
import netcomp.ObtenerIp;

/**
 *
 * @author zerg
 */
public class AccionCrearClaseMaestro extends AbstractAction {

    static VtnClaseMaestro vtnClaseMaestro;
    VtnCrearClase vtnCrearClase;
    private String miIp;
    private String nombre;
    private String contrasenia;
    private String descripcion;

    public AccionCrearClaseMaestro(VtnCrearClase parent,String elNombre, String laContrasenia, String laDescripcion) {
        vtnClaseMaestro = new VtnClaseMaestro();
        vtnCrearClase = parent;
        this.nombre = elNombre;
        this.contrasenia = laContrasenia;
        this.descripcion = laDescripcion;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Cierro la ventana "Crear Clase"
        vtnCrearClase.dispose();
        try {
            //Averiguo mi dirección ip
            miIp = ObtenerIp.getIp(true, false).toString().substring(1);
        } catch (SocketException ex) {
            Logger.getLogger(VtnClaseMaestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Instancio el objeto "Clase"
        Clase clase = new Clase(nombre, descripcion, contrasenia, miIp, 5008);
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
