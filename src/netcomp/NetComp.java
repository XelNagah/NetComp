/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import netcomp.GUI.VtnPrincipal;

/**
 *
 * @author zerg
 */
public class NetComp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            // TODO code application logic here
            // Ventanas del programa
            VtnPrincipal vtnPrincipal = new VtnPrincipal();
            vtnPrincipal.setVisible(true);
            vtnPrincipal.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        }
}
