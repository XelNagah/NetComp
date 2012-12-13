/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp.GUI.acciones;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import netcomp.Alumno;
import netcomp.ConexionAlumno;
import netcomp.Configuracion;
import netcomp.GUI.VtnBuscarClase;
import netcomp.GUI.VtnClaseAlumno;
import netcomp.InfoClase;
import netcomp.NetComp;

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
            if (laClase.getTieneContrasenia()) {
                JPasswordField pf = new JPasswordField();
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Ingresar contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (okCxl == JOptionPane.OK_OPTION) {
                    String password = new String(pf.getPassword());
                    conectar(laClase, password);
                } else {
                    NetComp.vtnPrincipal.setVisible(true);
                }
            } else {
                conectar(laClase);
            }
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

    private void conectar(InfoClase laClase) {
        //Creo un alumno con los datos de la configuración de NetComp
        Alumno elAlumno = crearAlumno();
        conexion = new ConexionAlumno(elAlumno, laClase);
        //Configuro el alumno a la ventana de clase de alumno
        vtnClaseAlumno.setAlumno(elAlumno);
        vtnClaseAlumno.setConexionAlumno(conexion);
        vtnClaseAlumno.setVisible(true);
        vtnClaseAlumno.setTitle(elAlumno.getNombre() + " " + elAlumno.getApellido() + " @ " + laClase.getNombre());
        vtnClaseAlumno.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //laClase.imprimeInfo();
    }

    private void conectar(InfoClase laClase, String elPassword) {
        //Creo un alumno con los datos de la configuración de NetComp
        Alumno elAlumno = crearAlumno();
        conexion = new ConexionAlumno(elAlumno, laClase, elPassword);
        //Configuro el alumno a la ventana de clase de alumno
        if (conexion.getConectado()) {
            vtnClaseAlumno.setAlumno(elAlumno);
            vtnClaseAlumno.setConexionAlumno(conexion);
            vtnClaseAlumno.setVisible(true);
            vtnClaseAlumno.setTitle(elAlumno.getNombre() + " " + elAlumno.getApellido() + " @ " + laClase.getNombre());
            vtnClaseAlumno.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        } else {
            JOptionPane.showMessageDialog(null,"Contraseña incorrecta.","Inane error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
