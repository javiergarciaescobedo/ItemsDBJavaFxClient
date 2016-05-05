package es.javiergarciaescobedo.itemsdbjavafxclient.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Categories {

    // A la etiqueta que identifique el inicio y fin de cada categoría en el XML, se le va
    //  a llamar 'categories' en lugar de 'categoriesList'
    @XmlElement(name = "category")
    private List<Category> categoriesList;

    public Categories() {
        categoriesList = new ArrayList();
    }
    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

}
