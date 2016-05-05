package es.javiergarciaescobedo.itemsdbjavafxclient.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
@XmlRootElement
public class Category implements Serializable {

    private Integer id;
    private String name;

    public Category() {
    }

    public Category(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
