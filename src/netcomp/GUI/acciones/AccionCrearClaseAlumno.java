/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import netcomp.GUI.VtnBuscarClase;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.InfoClase;

/**
 *
 * @author zerg
 */
public class AccionCrearClaseAlumno extends AbstractAction {

    static VtnClaseAlumno vtnClaseAlumno;
    VtnBuscarClase vtnBuscarClase;
    InfoClase clase;

    public AccionCrearClaseAlumno(VtnBuscarClase parent) {
        vtnClaseAlumno = new VtnClaseAlumno();
        vtnBuscarClase = parent;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int index = vtnBuscarClase.getTabla().getSelectedRow();
        if (index >= 0) {
            InfoClase laClase = vtnBuscarClase.getClases().get(index);
            vtnBuscarClase.dejarDeEscuchar();
            vtnBuscarClase.dispose();
            vtnClaseAlumno.setVisible(true);
            vtnClaseAlumno.setTitle("Clase NetComp - " + laClase.getNombre());
            vtnClaseAlumno.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            (laClase).imprimeInfo();
        } else {
        }
    }
}
