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
            
            String strAction = "select";
            switch(action) {
                case ACTION_SELECT:
                    strAction = "select";
                    break;
                case ACTION_INSERT:
                    strAction = "insert";
                    break;
                case ACTION_UPDATE:
                    strAction = "update";
                    break;
                case ACTION_DELETE:
                    strAction = "delete";
                    break;
            }
            
            String strConnection = "http://" + server + ":" + port + "/" + servlet + "?action=" + strAction;
            URL url = new URL(strConnection);
            LOG.fine("Connecting to: " + strConnection);
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
