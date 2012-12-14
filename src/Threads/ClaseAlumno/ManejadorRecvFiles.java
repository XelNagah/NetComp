/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads.ClaseAlumno;

import Threads.ClaseMaestro.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zerg
 */
public class ManejadorRecvFiles implements Runnable{
private int puerto;
private File pathLocal;
private static int bufferSize=1000000; //1M
private ObjectInputStream ois;

    public ManejadorRecvFiles(int elPuerto, File pathLocal) {
        puerto = elPuerto;
        this.pathLocal=pathLocal;
    }

    @Override
    public void run() {
        try {
            int count;
            ServerSocket socketListen = new ServerSocket(puerto);
            System.out.println("Escucho archivo en el puerto " + puerto);
            Socket socket = socketListen.accept();
            System.out.println("Recibo conexión");
            ois=new ObjectInputStream(socket.getInputStream());
            byte[] buffer=new byte[bufferSize];
            //Obtengo el archivo remoto
            File pathRemoto =( File )ois.readObject();
            System.out.println("Recibo " + pathRemoto);
            //Extraigo el nombre
            String fileName = pathRemoto.getName();
            System.out.println("El nombre del archivos es " + fileName);
            //El archivo se escribe en "pathLocal/fileName"
            String file = pathLocal.getPath() + "/" + fileName;
            System.out.println("Escribo archivo en: " + file);
            //Abro el archivo en disco
            FileOutputStream fos=new FileOutputStream(file);
            //Recibo el archivo y escribo en disco
            while( !socket.isClosed() && (count = ois.read(buffer)) > 0 ){
                fos.write(buffer, 0, count);
            }
            ois.close();
            fos.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManejadorRecvFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorSendFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorSendFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}