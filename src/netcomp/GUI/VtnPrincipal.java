/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI;

import netcomp.Configuracion;
import netcomp.GUI.acciones.AccionAbout;
import netcomp.GUI.acciones.AccionConfiguracion;
import netcomp.GUI.acciones.AccionVtnBuscarClase;

/**
 *
 * @author zerg
 */
public class VtnPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form mainWindow
     */
    public VtnPrincipal() {
        initComponents();
        setTitle("NetComp");
    }
    //Creación de ventanas del programa
    //Ventana Configuración de Clases
//    VtnConfiguracion configuracion = new VtnConfiguracion();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainBotonCrearClase = new javax.swing.JButton();
        mainLabelCrearClase = new javax.swing.JLabel();
        mainLabelBuscarClase = new javax.swing.JLabel();
        mainBotonBuscarClase = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        archivoMenu = new javax.swing.JMenu();
        abrirMenuItem = new javax.swing.JMenuItem();
        guardarMenuItem = new javax.swing.JMenuItem();
        guardarComoMenuItem = new javax.swing.JMenuItem();
        salirMenuItem = new javax.swing.JMenuItem();
        editarMenu = new javax.swing.JMenu();
        cortarMenuItem = new javax.swing.JMenuItem();
        copiarMenuItem = new javax.swing.JMenuItem();
        pegarMenuItem = new javax.swing.JMenuItem();
        herramientasMenu = new javax.swing.JMenu();
        configuracionMenuItem = new javax.swing.JMenuItem();
        ayudaMenu = new javax.swing.JMenu();
        acercaDeMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainBotonCrearClase.setAction(new netcomp.GUI.acciones.AccionVtnCrearClase());
        mainBotonCrearClase.setText("Crear");

        mainLabelCrearClase.setText("Crear una clase");

        mainLabelBuscarClase.setText("Buscar Clases");

        mainBotonBuscarClase.setAction(new AccionVtnBuscarClase());
        mainBotonBuscarClase.setText("Buscar");
        mainBotonBuscarClase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainBotonBuscarClaseActionPerformed(evt);
            }
        });

        archivoMenu.setMnemonic('f');
        archivoMenu.setText("Archivo");

        abrirMenuItem.setMnemonic('o');
        abrirMenuItem.setText("Abrir");
        archivoMenu.add(abrirMenuItem);

        guardarMenuItem.setMnemonic('s');
        guardarMenuItem.setText("Guardar");
        archivoMenu.add(guardarMenuItem);

        guardarComoMenuItem.setMnemonic('a');
        guardarComoMenuItem.setText("Guardar como");
        archivoMenu.add(guardarComoMenuItem);

        salirMenuItem.setMnemonic('x');
        salirMenuItem.setText("Salir");
        salirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenuItemActionPerformed(evt);
            }
        });
        archivoMenu.add(salirMenuItem);

        menuBar.add(archivoMenu);

        editarMenu.setMnemonic('e');
        editarMenu.setText("Edición");

        cortarMenuItem.setMnemonic('t');
        cortarMenuItem.setText("Cortar");
        cortarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cortarMenuItemActionPerformed(evt);
            }
        });
        editarMenu.add(cortarMenuItem);

        copiarMenuItem.setMnemonic('y');
        copiarMenuItem.setText("Copiar");
        editarMenu.add(copiarMenuItem);

        pegarMenuItem.setMnemonic('p');
        pegarMenuItem.setText("Pegar");
        editarMenu.add(pegarMenuItem);

        menuBar.add(editarMenu);

        herramientasMenu.setText("Herramientas");

        configuracionMenuItem.setAction(new AccionConfiguracion());
        configuracionMenuItem.setText("Configuración");
        herramientasMenu.add(configuracionMenuItem);

        menuBar.add(herramientasMenu);

        ayudaMenu.setMnemonic('h');
        ayudaMenu.setText("Ayuda");

        acercaDeMenuItem.setAction(new AccionAbout());
        acercaDeMenuItem.setText("Acerca De");
        acercaDeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaDeMenuItemActionPerformed(evt);
            }
        });
        ayudaMenu.add(acercaDeMenuItem);

        menuBar.add(ayudaMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainLabelCrearClase)
                    .addComponent(mainLabelBuscarClase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mainBotonBuscarClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainBotonCrearClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(483, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainLabelCrearClase)
                    .addComponent(mainBotonCrearClase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainLabelBuscarClase)
                    .addComponent(mainBotonBuscarClase))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirMenuItemActionPerformed

    private void cortarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cortarMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cortarMenuItemActionPerformed

    private void acercaDeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaDeMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_acercaDeMenuItemActionPerformed

    private void mainBotonBuscarClaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainBotonBuscarClaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mainBotonBuscarClaseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VtnPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrirMenuItem;
    private javax.swing.JMenuItem acercaDeMenuItem;
    private javax.swing.JMenu archivoMenu;
    private javax.swing.JMenu ayudaMenu;
    private javax.swing.JMenuItem configuracionMenuItem;
    private javax.swing.JMenuItem copiarMenuItem;
    private javax.swing.JMenuItem cortarMenuItem;
    private javax.swing.JMenu editarMenu;
    private javax.swing.JMenuItem guardarComoMenuItem;
    private javax.swing.JMenuItem guardarMenuItem;
    private javax.swing.JMenu herramientasMenu;
    private javax.swing.JButton mainBotonBuscarClase;
    private javax.swing.JButton mainBotonCrearClase;
    private javax.swing.JLabel mainLabelBuscarClase;
    private javax.swing.JLabel mainLabelCrearClase;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem pegarMenuItem;
    private javax.swing.JMenuItem salirMenuItem;
    // End of variables declaration//GEN-END:variables
}
