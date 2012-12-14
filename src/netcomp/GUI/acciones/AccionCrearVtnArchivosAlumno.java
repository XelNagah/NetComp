/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.ConexionAlumno;
import netcomp.GUI.VtnArchivosAlumno;

/**
 *
 * @author zerg
 */
public class AccionCrearVtnArchivosAlumno extends AbstractAction {

    static VtnArchivosAlumno vtnArchivosAlumno;

    public AccionCrearVtnArchivosAlumno(ConexionAlumno laConexion) {
        vtnArchivosAlumno = new VtnArchivosAlumno(laConexion);
        vtnArchivosAlumno.setResizable(false);
        vtnArchivosAlumno.setTitle("Ver Archivos Compartidos");
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //vtnArchivosAlumno.setVisible(true);
    }
    
}
