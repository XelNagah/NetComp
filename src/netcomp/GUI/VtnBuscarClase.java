/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import netcomp.Escuchador;
import netcomp.GUI.acciones.AccionCrearClaseAlumno;
import netcomp.InfoClase;
import netcomp.NetComp;

/**
 *
 * @author zerg
 */
public class VtnBuscarClase extends JFrame {

    private Escuchador escuchador;
    private Thread escuchadorThread;
    private CustomDataModel modeloDatos;

    /**
     * Creates new form buscarClaseVtn
     */
    public VtnBuscarClase() {
        //Creo un escuchador (Si creo primero los componentes, la tabla no sabe cuantas rondas debe tener)
        escuchador = new Escuchador();
        modeloDatos = new CustomDataModel();
        //Establezco los componentes de la ventana
        initComponents();
        //Establezco título.
        setTitle("Buscador de Clases");
    }

    private void refreshTable() {
        buscarClasesTabla.repaint();
    }

    public void escuchar() {
        if (escuchadorThread != null && !escuchadorThread.isInterrupted()) {
            escuchadorThread.interrupt();
        }
        escuchadorThread = new Thread(escuchador);
        escuchadorThread.start();
    }

    public void dejarDeEscuchar() {
        escuchadorThread.interrupt();
    }
    //Creación de ventanas del programa
    //Ventana Crear Clase
    VtnClaseAlumno vtnClaseAlumno = new VtnClaseAlumno();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        buscarClasesTabla = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        buscarClaseBotonIngresar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        buscarClaseBotonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                vntBuscarClaseClosing(evt);
            }
        });

        buscarClasesTabla.setModel(modeloDatos);
        buscarClasesTabla.getModel().addTableModelListener(modeloDatos);
        buscarClasesTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(buscarClasesTabla);

        jLabel1.setFont(new java.awt.Font("Gentium", 1, 18)); // NOI18N
        jLabel1.setText("Buscar Clases");

        buscarClaseBotonIngresar.setAction(new AccionCrearClaseAlumno(this));
        buscarClaseBotonIngresar.setText("Ingresar");

        buscarClaseBotonCancelar.setText("Cancelar");
        buscarClaseBotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClaseBotonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(buscarClaseBotonCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarClaseBotonIngresar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarClaseBotonIngresar)
                    .addComponent(buscarClaseBotonCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarClaseBotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarClaseBotonCancelarActionPerformed
        escuchadorThread.interrupt();
        dispose();
        NetComp.vtnPrincipal.setVisible(true);
    }//GEN-LAST:event_buscarClaseBotonCancelarActionPerformed

    private void vntBuscarClaseClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_vntBuscarClaseClosing
        escuchadorThread.interrupt();
        this.dispose();
        NetComp.vtnPrincipal.setVisible(true);
    }//GEN-LAST:event_vntBuscarClaseClosing

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
            java.util.logging.Logger.getLogger(VtnBuscarClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VtnBuscarClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VtnBuscarClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VtnBuscarClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VtnBuscarClase().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarClaseBotonCancelar;
    private javax.swing.JButton buscarClaseBotonIngresar;
    private javax.swing.JTable buscarClasesTabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

    class CustomDataModel extends DefaultTableModel implements TableModelListener {

        private String[] columnNames;
        private ArrayList<InfoClase> data;

        public CustomDataModel() {
            columnNames = escuchador.columnas;
            data = escuchador.getClases();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public int getRowCount() {
            if (escuchador.getClases() != null) {
                return escuchador.getClases().size();
            } else {
                return 0;
            }
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public Object getValueAt(int row, int col) {
            InfoClase laClase = data.get(row);
            switch (col) {
                case 0:
                    return laClase.getNombre();
                case 1:
                    return "El Profe";
                case 2:
                    return laClase.getDescripcion();
                case 3:
                    return laClase.getTieneContrasenia();
                default:
                    return "";
            }
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public void tableChanged(TableModelEvent tme) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    fireTableDataChanged();
                    fireTableStructureChanged();
                    buscarClasesTabla.revalidate();
                    buscarClasesTabla.repaint();
                    jScrollPane1.repaint();
                }
            });
        }
    }
}
