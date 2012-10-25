/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.GUI.VtnAcercaDe;

/**
 *
 * @author zerg
 */
public class AccionAbout extends AbstractAction {

    static VtnAcercaDe acercaDe;
    
    public AccionAbout()
    {
        acercaDe = new VtnAcercaDe();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
    //Ventana Configuración de Clases    
        if (acercaDe.isShowing() == false) {
            acercaDe.setVisible(true);
            acercaDe.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }
        else {
            acercaDe.toFront();
        }
    }
    
}
