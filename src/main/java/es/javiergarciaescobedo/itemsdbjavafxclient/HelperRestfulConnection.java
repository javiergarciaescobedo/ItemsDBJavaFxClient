package es.javiergarciaescobedo.itemsdbjavafxclient;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class HelperRestfulConnection {

    private static final Logger LOG = Logger.getLogger(HelperRestfulConnection.class.getName());

    public static Object requestServletDBAction(Object object, String action, String servlet) {
        try {
            Properties properties = new Properties();
            properties.load(HelperRestfulConnection.class.getResourceAsStream("/properties/config.properties"));
            String server = properties.getProperty("server");
            String port = properties.getProperty("port");
            String app = properties.getProperty("app");
            
            String strConnection = "http://" + server + ":" + port + "/" + app + "/" + servlet;
            URL url = new URL(strConnection);
            LOG.fine("Connecting to: " + strConnection + " with " + action + " method");
            URLConnection uc = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) uc;
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "text/xml");
            conn.setRequestMethod(action);

            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            if(!action.equals("GET")) {
                // Enviar al servidor, en formato XML, el objeto que se ha pasado por parámetro
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.marshal(object, conn.getOutputStream());
            }

            // Recoger la respuesta del servidor
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object response = jaxbUnmarshaller.unmarshal(isr);
            isr.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        
}
