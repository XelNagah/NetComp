/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import netcomp.GUI.VtnClaseAlumno;

/**
 *
 * @author zerg
 */
public class ManejadorDeAlumno implements Runnable {
    boolean corriendo;
    long periodo = 300;
    VtnClaseAlumno ventana;
    
    public ManejadorDeAlumno(VtnClaseAlumno laVentana) {
        ventana = laVentana;
    }

    private void manejar(){
        while(corriendo){
            try {
                Thread.sleep(periodo);
            } catch (InterruptedException ex) {
                corriendo = false;
                break;
            }
            //Hacer algo
        }
    }
    
    @Override
    public void run() {
        corriendo = true;
        manejar();
    }
    
}
