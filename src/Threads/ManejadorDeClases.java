/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import netcomp.InfoClase;

/**
 *
 * @author zerg
 */
public class ManejadorDeClases implements Runnable {

    ArrayList<InfoClase> clases;
    Boolean corriendo;
    DefaultTableModel customDataModel;
    final int period = 2;

    public ManejadorDeClases() {
    }
    
    synchronized void manejarClases() {
        while(corriendo){
            for(Iterator<InfoClase> it = clases.iterator(); it.hasNext(); ){
                InfoClase laClase = it.next();
                if (laClase.getTimeOut() <= 0) {
                    it.remove();
                    customDataModel.fireTableDataChanged();
                }
                laClase.updateTimeOut(period);
            }
            try {
                Thread.sleep(period*1000);
            } catch (InterruptedException ex) {
                corriendo = false;
            }
        }
    }

    @Override
    public void run() {
        corriendo = true;
        manejarClases();
    }

    public void setLasClases(ArrayList<InfoClase> lasClases) {
        this.clases = lasClases;
    }

    public void setCustomDataModel(DefaultTableModel customDataModel) {
        this.customDataModel = customDataModel;
    }
    
}
