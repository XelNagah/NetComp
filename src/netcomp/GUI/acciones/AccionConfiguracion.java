/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.GUI.VtnConfiguracion;

/**
 *
 * @author zerg
 */
public class AccionConfiguracion extends AbstractAction {

    public static VtnConfiguracion vtnConfiguracion;
    
    public AccionConfiguracion(){
        vtnConfiguracion = new VtnConfiguracion();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (vtnConfiguracion.isShowing() == false) {
            vtnConfiguracion.init();
            vtnConfiguracion.setVisible(true);
            vtnConfiguracion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }
        else {
            vtnConfiguracion.toFront();
        }
    }
}
