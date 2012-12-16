/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mensajes;

import java.util.ArrayList;

/**
 *
 * @author zerg
 */
public class TipoEventosGUI {

    //Eventos maestro GUI
    public final static int actualizarListaArchivos = 1;
    public final static int desconectarse = 2;
    public final static int listaAlumnosUpdate = 3;
    public final static int listaArchivosUpdate = 4;
    public final static int enviarArchivo = 5;
    public final static int stopCompartirPantalla = 6;
    
    //Eventos alumno GUI
    public final static int pedirArchivo = 10;
    public final static int verPantalla = 11;
    
    //General
    public final static int imagenPantalla = 999;
    
    private int eventId;
    private ArrayList<Object> params;

    public TipoEventosGUI(int eventId) {
        this.eventId = eventId;
        this.params = new ArrayList<Object>();
    }

    public TipoEventosGUI(int eventId, ArrayList<Object> losParams) {
        this.eventId = eventId;
        this.params = losParams;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public ArrayList<Object> getParams() {
        return params;
    }

    public void setParams(ArrayList<Object> params) {
        this.params = params;
    }
    
    public void addParam(Object elObjeto){
        params.add(elObjeto);
    }
}