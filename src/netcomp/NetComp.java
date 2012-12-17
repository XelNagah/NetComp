/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import javax.swing.JOptionPane;
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
        System.setProperty("java.net.preferIPv4Stack" , "true");
        // TODO code application logic here
        // Ventanas del programa
        vtnPrincipal = new VtnPrincipal();
        vtnPrincipal.setResizable(false);
        vtnPrincipal.setVisible(true);
        Configuracion.configFileExists();
        if ("1".equals(Configuracion.primeraVez())) {
            System.out.println("Primera vez");
            AccionConfiguracion accionConfiguracion = new AccionConfiguracion();
            accionConfiguracion.actionPerformed(null);
            (AccionConfiguracion.vtnConfiguracion).toFront();
        }
        vtnPrincipal.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}