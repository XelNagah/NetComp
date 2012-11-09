/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.BuscadorDeClases;

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
    //Periodo de espera (se multiplica por 1000 para obtener milisegundos en el sleep) 
    //Velocidad de timeout
    final int period = 2;

    public ManejadorDeClases() {
    }
    
    synchronized void manejarClases() {
        while(corriendo){
            //Itero por las clases
            for(Iterator<InfoClase> it = clases.iterator(); it.hasNext(); ){
                //Guardo la clase en una variable
                InfoClase laClase = it.next();
                //Si la clase superó el timeout
                if (laClase.getTimeOut() <= 0) {
                    //Remover
                    it.remove();
                    //Y actualizar Tabla
                    customDataModel.fireTableDataChanged();
                }
                //Si no superó el timeout, restarle el periodo al timeout de la clase.
                laClase.updateTimeOut(period);
            }
            try {
                //Repetir casa period segundos
                Thread.sleep(period*1000);
            } catch (InterruptedException ex) {
                //Si me interrumpen, salir del bucle.
                corriendo = false;
            }
        }
    }

    @Override
    public void run() {
        //Establezco la bandera para ejecutar.
        corriendo = true;
        //Comienzo a manejar clases
        manejarClases();
    }

    public void setLasClases(ArrayList<InfoClase> lasClases) {
        this.clases = lasClases;
    }

    public void setCustomDataModel(DefaultTableModel customDataModel) {
        this.customDataModel = customDataModel;
    }
    
}
