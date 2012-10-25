/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.Clase;
import netcomp.GUI.VtnClaseMaestro;

/**
 *
 * @author zerg
 */
public class AccionCerrarClaseMaestro extends AbstractAction{

    static VtnClaseMaestro vtnClaseMaestro;
    static Clase clase;
    
    public AccionCerrarClaseMaestro(VtnClaseMaestro parent){
        vtnClaseMaestro = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        vtnClaseMaestro.dispose();
        vtnClaseMaestro.dejarDeAnunciar();
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
