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
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    BufferedImage tmpImage;
    BufferedImage lastBufferedImage = null;
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
            lastBufferedImage = null;
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
                tmpImage = robot.createScreenCapture(screenRect);
                //Si la imagen no es nula, hay alguien conectado y es una nueva imagen
                if (tmpImage != null && manejador.enviarPantalla() && newImage(tmpImage)) {
                    //Esta es la nueva última imagen.
                    lastBufferedImage = tmpImage;
                    //Convierto la imagen a un Array de Bytes.
                    ImageIO.write(tmpImage, "JPEG", out);
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

    private boolean newImage(BufferedImage tmpImage) {
        Boolean iguales = true;
        //Si es la primer imagen
        if (lastBufferedImage == null){
            //Devuelvo true
            return true;
        }
        
        //Obtengo los buffers de las imágenes
        DataBuffer newImageDataBuffer = tmpImage.getRaster().getDataBuffer();
        DataBuffer lastImageDataBuffer = lastBufferedImage.getRaster().getDataBuffer();

        //Obtengo los tipos de los buffers.
        DataBufferInt newImageDBAsDBInt = (DataBufferInt) newImageDataBuffer;
        DataBufferInt lastImageDBAsDBInt = (DataBufferInt) lastImageDataBuffer;

        //Armo arreglos de ints con la información de los buffers
        for (int bank = 0; bank < newImageDBAsDBInt.getNumBanks(); bank++) {
            int[] nueva = newImageDBAsDBInt.getData(bank);
            int[] anterior = lastImageDBAsDBInt.getData(bank);

            //si la información es la misma
            if (Arrays.equals(nueva, anterior) == true) {
                //las imágenes son iguales y devuelvo false ya que NO debo enviarla
                iguales = false;
            }
        }
        //Si las imágenes son distintas, devuelvo true ya que debo enviarla.
        return iguales;
    }
}