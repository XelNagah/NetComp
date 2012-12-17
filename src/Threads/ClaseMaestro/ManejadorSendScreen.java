/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import Mensajes.TipoEventosGUI;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcomp.ConexionClase;

/**
 *
 * @author zerg
 */
public class ManejadorSendScreen implements Runnable {

    static boolean corriendo;
    static ServerSocket serverSocket;
    static Socket objSocket;
    Rectangle screenRect;
    OutputStream objOut;
    ByteArrayOutputStream out;
    BufferedImage image;
    Robot robot;
    ConexionClase conexion;
    BlockingQueue<TipoEventosGUI> queue;

    public ManejadorSendScreen(ServerSocket elServerSocket, ConexionClase laConexion) {
        serverSocket = elServerSocket;
        conexion = laConexion;
        queue = new ArrayBlockingQueue<TipoEventosGUI>(50);
    }

    @Override
    public void run() {
        try {
            inicializarVariables();
            while (corriendo == true) {
                TipoEventosGUI elEvento = queue.take();
                if (out.size() == 0) {
                    out = (ByteArrayOutputStream) elEvento.getParams().get(0);
                }
                if (objSocket.isConnected()) {
                    try {
                        enviarImagen(objOut, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                        corriendo = false;
                        conexion.setRecibePantalla(false);
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ManejadorSendScreen.class.getName()).log(Level.SEVERE, null, ex);
            corriendo = false;
            conexion.setRecibePantalla(false);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorSendScreen.class.getName()).log(Level.SEVERE, null, ex);
            corriendo = false;
            conexion.setRecibePantalla(false);
        } catch (AWTException ex) {
            Logger.getLogger(ManejadorSendScreen.class.getName()).log(Level.SEVERE, null, ex);
            corriendo = false;
            conexion.setRecibePantalla(false);
        }
    }

    public BlockingQueue<TipoEventosGUI> getQueue() {
        return queue;
    }

    public void enviarImagen(OutputStream objOut, ByteArrayOutputStream out) throws IOException {
        if (out.size() > 0) {
            objOut.write(new byte[]{
                        (byte) (out.size() >> 24), (byte) (out.size() >> 16), (byte) (out.size() >> 8), (byte) out.size()
                    });
            objOut.write(out.toByteArray());
        }
        out.reset();
        objOut.flush();
    }

    public void inicializarVariables() throws AWTException, IOException, HeadlessException {
        corriendo = true;
        objSocket = serverSocket.accept();
        conexion.setRecibePantalla(true);
        robot = new Robot();
        image = null;
        screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        objOut = objSocket.getOutputStream();
        out = new ByteArrayOutputStream();
    }
}