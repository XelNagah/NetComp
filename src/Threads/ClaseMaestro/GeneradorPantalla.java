/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import Mensajes.TipoEventosGUI;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author zerg
 */
public class GeneradorPantalla implements Runnable {

    boolean corriendo;
    ManejadorConexiones manejador;
    Rectangle screenRect;
    Robot robot;
    BufferedImage image;
    ByteArrayOutputStream out;

    public GeneradorPantalla(ManejadorConexiones elManejador) {
        manejador = elManejador;
    }

    @Override
    public void run() {
        try {
            corriendo = true;
            out = new ByteArrayOutputStream();
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(GeneradorPantalla.class.getName()).log(Level.SEVERE, null, ex);
        }
        screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        while (corriendo) {
            try {
                Thread.sleep(400);
                //Preparo el evento
                TipoEventosGUI elEvento;
                //Capturo pantalla
                image = robot.createScreenCapture(screenRect);
                //Si la imagen no es nula
                if (image != null && manejador.enviarPantalla()) {
                    //Convierto la imagen a un Array de Bytes.
                    ImageIO.write(image, "JPEG", out);
                    //Creo un arreglo de parámetros para el evento
                    ArrayList<Object> losParams = new ArrayList<Object>();
                    //Le agrego la imagen convertida a bytes
                    losParams.add(out);
                    //Creo el evento
                    elEvento = new TipoEventosGUI(TipoEventosGUI.imagenPantalla, losParams);
                    //Y finalmente envío la pantalla al manejador de conexiones
                    //para que la envíe a los alumnos que esperan recibirla.
                    manejador.enviarPantalla(elEvento);
                    out.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(GeneradorPantalla.class.getName()).log(Level.SEVERE, null, ex);
                //corriendo = false;
            } catch (InterruptedException ex) {
                try {
                    //Logger.getLogger(GeneradorPantalla.class.getName()).log(Level.SEVERE, null, ex);
                    out.close();
                } catch (IOException ex1) {
                    Logger.getLogger(GeneradorPantalla.class.getName()).log(Level.SEVERE, null, ex1);
                }
                corriendo = false;
            }
        }
    }
}