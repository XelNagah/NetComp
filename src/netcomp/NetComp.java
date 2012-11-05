/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.GUI.VtnPrincipal;
import netcomp.GUI.acciones.AccionConfiguracion;

/**
 *
 * @author zerg
 */
public class NetComp {

    static public VtnPrincipal vtnPrincipal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Ventanas del programa
        vtnPrincipal = new VtnPrincipal();
        vtnPrincipal.setVisible(true);
        if ("1".equals(Configuracion.primeraVez())) {
            System.out.println("me ejecuto");
            AccionConfiguracion accionConfiguracion = new AccionConfiguracion();
            accionConfiguracion.actionPerformed(null);
            (AccionConfiguracion.vtnConfiguracion).toFront();
        }
        vtnPrincipal.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}