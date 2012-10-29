/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.GUI.VtnBuscarClase;
import netcomp.NetComp;

/**
 *
 * @author zerg
 */
public class AccionVtnBuscarClase extends AbstractAction {

    static VtnBuscarClase vtnBuscarClase;
    
    public AccionVtnBuscarClase(){
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        NetComp.vtnPrincipal.setVisible(false);
        vtnBuscarClase = new VtnBuscarClase();
        if (!vtnBuscarClase.isShowing()) {
            vtnBuscarClase.setVisible(true);
            vtnBuscarClase.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            vtnBuscarClase.escuchar();
        }
        else {
            vtnBuscarClase.toFront();
        }
    }
}
