/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseMaestro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zerg
 */
public class ManejadorSendFiles implements Runnable {

    private InetAddress ip;
    private int puerto;
    private File file;
    private static int bufferSize = 1000000; //1M
    private ObjectOutputStream oos;

    public ManejadorSendFiles(InetAddress ip, int elPuerto, File file) {
        this.puerto = elPuerto;
        this.file = file;
        this.ip = ip;
    }

    @Override
    public void run() {
        try {
            int count;
            Socket socket = new Socket(ip, puerto);
            oos = new ObjectOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[bufferSize];
            oos.flush();
            oos.writeObject(file);
            System.out.println("Envío el path " + file);
            while (!socket.isClosed() && (count = fis.read(buffer)) > 0) {
                oos.write(buffer, 0, count);
            }
            oos.close();
            fis.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorSendFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorSendFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
