/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.GUI.VtnCrearClase;
import netcomp.GUI.VtnPrincipal;
import netcomp.NetComp;

/**
 *
 * @author zerg
 */
public class AccionVtnCrearClase extends AbstractAction {

    VtnCrearClase vtnCrearClase;
    
    public AccionVtnCrearClase() {
        vtnCrearClase = new VtnCrearClase();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        NetComp.vtnPrincipal.setVisible(false);
        if (vtnCrearClase.isShowing() == false) {
            vtnCrearClase.setVisible(true);
            vtnCrearClase.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }
        else {
            vtnCrearClase.toFront();
        }
    }
    
}
