package es.javiergarciaescobedo.masterdetailfxml;

import es.javiergarciaescobedo.masterdetailfxml.model.Item;
import es.javiergarciaescobedo.masterdetailfxml.model.Items;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class HelperServletConnection {

    public static Items downloadItems() {
        Items items = null;
        try {
            // Crear objeto JAXB para interpretar objetos 'Items' desde XML
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Generar lista de objetos desde XML descargado de URL
            URL url = new URL("http://213.96.173.88:8088/ItemsSampleDBJavaWeb/Main");
//            URL url = new URL("http://192.168.15.230:8088/ItemsSampleDBJavaWeb/Main");
            InputStream is = url.openStream();
            items = (Items) jaxbUnmarshaller.unmarshal(is);
            
            // Mostrar el contenido de la lista obtenida
            for (Item item : items.getItemsList()) {
                System.out.println("id: " + item.getId());
                System.out.println("astring: " + item.getAstring());
                System.out.println("anumber: " + item.getAnumber());
                System.out.println("adate: " + item.getAdate());
                System.out.println();
            }
        } catch (Exception ex) {
            Logger.getLogger(HelperServletConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }
    
    public static List requestServletDBAction(Class className, Object object, int action) {
        try {
            Properties properties = new Properties();
            properties.load(HelperServletConnection.class.getResourceAsStream("/config.properties"));
            String server = properties.getProperty("server");
//            String dbName = properties.getProperty("db_name");
//            String dbUser = properties.getProperty("db_user");
//            String dbPassword = properties.getProperty("db_password");

            System.out.println("server" + server);
        
            URL url = new URL("http://213.96.173.88:8088/ItemsSampleDBJavaWeb/RequestItems?op=DELETE");
            URLConnection uc = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) uc;
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "text/xml");

            // Enviar al servidor, en formato XML, el objeto que se ha pasado por parámetro
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(object, conn.getOutputStream());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
