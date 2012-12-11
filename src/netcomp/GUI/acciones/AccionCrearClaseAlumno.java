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
import netcomp.ConexionAlumno;
import netcomp.Configuracion;
import netcomp.GUI.VtnBuscarClase;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.InfoClase;

/**
 *
 * @author zerg
 */
public class AccionCrearClaseAlumno extends AbstractAction {

    public static VtnClaseAlumno vtnClaseAlumno;
    VtnBuscarClase vtnBuscarClase;
    InfoClase clase;
    ConexionAlumno conexion;
    int index;

    public AccionCrearClaseAlumno(VtnBuscarClase parent) {
        vtnClaseAlumno = new VtnClaseAlumno();
        vtnBuscarClase = parent;
        this.index = -1;
    }

    public AccionCrearClaseAlumno(VtnBuscarClase parent, int row) {
        vtnClaseAlumno = new VtnClaseAlumno();
        vtnBuscarClase = parent;
        this.index = row;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (index != -1) {
            //La clase seleccionada llega por doble click.
        } else {
            //Averiguo que clase está seleccionada en la tabla
            index = vtnBuscarClase.getTabla().getSelectedRow();
        }
        if (index >= 0) {
            //Creo la clase
            InfoClase laClase = vtnBuscarClase.getClases().get(index);
            //Le digo a la ventana de buscar clases que deje de escuchar
            vtnBuscarClase.dejarDeEscuchar();
            //Escondo el buscador de clases
            vtnBuscarClase.dispose();
            //Creo un alumno con los datos de la configuración de NetComp
            Alumno elAlumno = crearAlumno();
            conexion = new ConexionAlumno(elAlumno, laClase);
            //Configuro el alumno a la ventana de clase de alumno
            vtnClaseAlumno.setAlumno(elAlumno);
            vtnClaseAlumno.setConexionAlumno(conexion);
            vtnClaseAlumno.setVisible(true);
            vtnClaseAlumno.setTitle("Clase NetComp - " + laClase.getNombre());
            vtnClaseAlumno.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            //laClase.imprimeInfo();
        } else {
            //Si no hay nada seleccionado en la tabla de clases, no hago nada.
        }
    }

    private Alumno crearAlumno() {
        Configuracion.inicializar();
        String nombre = Configuracion.getNombre();
        String apellido = Configuracion.getApellido();
        return new Alumno(nombre, apellido);
    }

    @Deprecated
    private Socket conectar(InfoClase laClase) {
        String ip = laClase.getIp();
        int puerto = laClase.getPuerto();
        Socket elSocket;
        try {
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
