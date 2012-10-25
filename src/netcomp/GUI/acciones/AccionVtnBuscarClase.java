/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.GUI.VtnBuscarClase;

/**
 *
 * @author zerg
 */
public class AccionVtnBuscarClase extends AbstractAction {

    static VtnBuscarClase vtnBuscarClase;
    
    public AccionVtnBuscarClase(){
        vtnBuscarClase = new VtnBuscarClase();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (vtnBuscarClase.isShowing() == false) {
            vtnBuscarClase.setVisible(true);
            vtnBuscarClase.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }
        else {
            vtnBuscarClase.toFront();
        }
    }
    
}
