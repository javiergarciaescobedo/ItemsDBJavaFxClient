package es.javiergarciaescobedo.itemsdbjavafxclient;

import es.javiergarciaescobedo.itemsdbjavafxclient.model.Items;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class HelperServletConnection {

    private static final Logger LOG = Logger.getLogger(HelperServletConnection.class.getName());
    public static final int ACTION_SELECT = 0;
    public static final int ACTION_INSERT = 1;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_DELETE = 3;

    public static Object requestServletDBAction(Object object, int action) {
        try {
            Properties properties = new Properties();
            properties.load(HelperServletConnection.class.getResourceAsStream("/properties/config.properties"));
            String server = properties.getProperty("server");
            String port = properties.getProperty("port");
            String servlet = properties.getProperty("servlet");
            
            String strConnection = "http://" + server + ":" + port + "/" + servlet;
            URL url = new URL(strConnection);
            LOG.fine("Connecting to: " + strConnection);
            URLConnection uc = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) uc;
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "text/xml");
            switch(action) {
                case ACTION_SELECT:
                    conn.setRequestMethod("GET");
                    LOG.fine("GET Method");
                    break;
                case ACTION_INSERT:
                    conn.setRequestMethod("POST");
                    LOG.fine("POST Method");
                    break;
                case ACTION_UPDATE:
                    conn.setRequestMethod("PUT");
                    LOG.fine("PUT Method");
                    break;
                case ACTION_DELETE:
                    conn.setRequestMethod("DELETE");
                    LOG.fine("DELETE Method");
                    break;
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            if(action != ACTION_SELECT) {
                // Enviar al servidor, en formato XML, el objeto que se ha pasado por parámetro
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.marshal(object, conn.getOutputStream());
            }

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
