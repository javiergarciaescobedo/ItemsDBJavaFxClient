package es.javiergarciaescobedo.masterdetailfxml;

import es.javiergarciaescobedo.masterdetailfxml.model.Item;
import es.javiergarciaescobedo.masterdetailfxml.model.Items;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class ItemsDownloader {

    public static Items downloadItems() {
        Items items = null;
        try {
            // Crear objeto JAXB para interpretar objetos 'Items' desde XML
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Generar lista de objetos desde XML descargado de URL
//            URL url = new URL("http://213.96.173.88:8088/ItemsSampleDBJavaWeb/Main");
            URL url = new URL("http://192.168.15.230:8088/ItemsSampleDBJavaWeb/Main");
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
            Logger.getLogger(ItemsDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }
    
}
