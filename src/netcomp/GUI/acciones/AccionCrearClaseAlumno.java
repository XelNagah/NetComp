/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import netcomp.Alumno;
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
            Alumno elAlumno = new Alumno("Juan", "Perez");
            Socket unSocket = conectar(laClase);
            System.out.println(unSocket);
            elAlumno.setSocket(unSocket);
            System.out.println("Seteo el Socket al alumo en: " + laClase.getNombre() + " en el puerto " + laClase.getPuerto());
            vtnClaseAlumno.setAlumno(elAlumno);
            vtnClaseAlumno.conectar();
            vtnClaseAlumno.setVisible(true);
            vtnClaseAlumno.setTitle("Clase NetComp - " + laClase.getNombre());
            vtnClaseAlumno.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            //laClase.imprimeInfo();
        } else {
        }
    }
    
    private Socket conectar(InfoClase laClase){
        String ip = laClase.getIp();
        int puerto = laClase.getPuerto();
        Socket elSocket;
        try {
            System.out.println("Intento conectarme a " + ip + ":" + puerto);
            elSocket = new Socket(ip, puerto);
            return elSocket;
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionCrearClaseAlumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No me pude conectar.");
            return null;
        } catch (IOException ex) {
            Logger.getLogger(AccionCrearClaseAlumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No me pude conectar.");
            return null;
        }
    }
}
