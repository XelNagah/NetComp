/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package netcomp;
/**
 *
 * @author zerg
 */

public class TestingClass {
    public static String miIp;
    
        public static void main(String[] args) {
            
            
            String stringXML = GenTools.XMLGenerator("puto", "Matias Fritz");
            System.out.println(stringXML);
            String xmlParseado = GenTools.XMLParser("puto", stringXML);
            System.out.println(xmlParseado);
            String otroStringXML = GenTools.XMLAppend("otroputo", "El Orshi", stringXML);
            System.out.println(otroStringXML);
            String xmlWrapper = GenTools.XMLWrapper("msg", otroStringXML);
            System.out.println(xmlWrapper);
            System.out.println("Parseo algo que NO existe:");
            System.out.println(GenTools.XMLParser("bbebekbej",otroStringXML));
        }
}
