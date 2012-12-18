/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netcomp;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;
import javax.swing.ImageIcon;

/**
 *
 * @author zerg
 */
public class GenTools {

    //Obtiene la primer dirección IP local que no sea loopback
    public static InetAddress getIp(boolean preferIpv4, boolean preferIPv6) throws SocketException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
                InetAddress addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }
    
    //Obtiene la primer dirección IP local que no sea loopback
    public static InterfaceAddress getIf(boolean preferIpv4, boolean preferIPv6) throws SocketException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            ArrayList<InterfaceAddress> a = (ArrayList<InterfaceAddress>) i.getInterfaceAddresses();
            for (Iterator it= a.iterator() ; it.hasNext() ;) {
                InterfaceAddress addr = (InterfaceAddress) it.next();
                if (!addr.getAddress().isLoopbackAddress() && addr.getAddress() != null) {
                    if (addr.getAddress() instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr.getAddress() instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }
    
    //Verifica si una cadena representa una dirección IP.
    public static boolean chequearIp(String sip) {
        String[] parts = sip.split("\\.");
        for (String s : parts) {
            int i = Integer.parseInt(s);
            if (i < 0 || i > 255) {
                return false;
            }
        }
        return true;
    }

    public static String XMLWrapper(String tag, String elMensaje) {
        //return "<" + tag + ">" + "\n" + elMensaje + "\n" + "</" + tag + ">";
        return "<" + tag + ">" + "" + elMensaje + "" + "</" + tag + ">";
    }

    public static String XMLGenerator(String tag, String valor) {
        return "<" + tag + ">" + valor + "</" + tag + ">";
    }

    public static String XMLParser(String tag, String elString) {
        String[] vector = elString.split("<" + tag + ">|<\\/" + tag + ">");
        //System.out.println(vector[0]);
        //System.out.println(vector[1]);
        //System.out.println(vector[2]);
        if (vector.length < 2) {
            return null;
        } else {
            return vector[1];
        }
    }

    public static String XMLAppend(String tag, String valor, String xmlStringOriginal) {
        //return xmlStringOriginal + "\n" + XMLGenerator(tag, valor);
        return xmlStringOriginal + "" + XMLGenerator(tag, valor);
    }

    //Encuentra un puerto libre en la interfaz
    public static int findFreePort()
            throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();
        return port;
    }

    public static UUID uniqueId(String[] args) {
        return UUID.randomUUID();
    }
    
    public static ImageIcon createImageIcon(String filename,
            String description) {
        File archivo = new File(filename);
        if (archivo != null) {
            //try {
                
                //return new ImageIcon(archivo.getCanonicalPath(), description);
                return new ImageIcon(ClassLoader.getSystemResource(filename), description);
            /*} catch (IOException ex) {
                Logger.getLogger(GenTools.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } else {
            System.err.println("Couldn't find file: " + filename);
            return null;
        }
        //return null;
    }
}
