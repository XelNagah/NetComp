/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.Clase;
import netcomp.GUI.VtnArchivosMaestro;
import netcomp.GUI.VtnClaseMaestro;

/**
 *
 * @author zerg
 */
public class AccionCrearVtnArchivosMaestro extends AbstractAction {
    
    static VtnArchivosMaestro vtnArchivosMaestro;

    public AccionCrearVtnArchivosMaestro(Clase laClase, VtnClaseMaestro parent) {
        vtnArchivosMaestro = new VtnArchivosMaestro(laClase);
        vtnArchivosMaestro.setResizable(false);
        vtnArchivosMaestro.setTitle("Compartir Archivos");
        parent.setVtnArchivos(vtnArchivosMaestro);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        vtnArchivosMaestro.setVisible(true);
    }
    
}
