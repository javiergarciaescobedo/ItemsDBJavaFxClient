package es.javiergarciaescobedo.itemsdbjavafxclient.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlElement
    private int id;
    @XmlElement
    private String astring;
    @XmlElement
    private Integer anumber;
    @XmlElement
    private Date adate;
    @XmlElement
    private Boolean aboolean;
    @XmlElement
    private Double adouble;
    @XmlElement
    private BigDecimal aprice;
    @XmlElement
    private Date atime;
    @XmlElement
    private Category category;

    public Item() {
    }

    public Item(int id, String astring, Integer anumber, Date adate, Boolean aboolean, Double adouble, BigDecimal aprice, Date atime, Category category) {
        this.id = id;
        this.astring = astring;
        this.anumber = anumber;
        this.adate = adate;
        this.aboolean = aboolean;
        this.adouble = adouble;
        this.aprice = aprice;
        this.atime = atime;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAstring() {
        return astring;
    }

    public void setAstring(String astring) {
        this.astring = astring;
    }

    public Integer getAnumber() {
        return anumber;
    }

    public void setAnumber(Integer anumber) {
        this.anumber = anumber;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }

    public Boolean getAboolean() {
        return aboolean;
    }

    public void setAboolean(Boolean aboolean) {
        this.aboolean = aboolean;
    }

    public Double getAdouble() {
        return adouble;
    }

    public void setAdouble(Double adouble) {
        this.adouble = adouble;
    }

    public BigDecimal getAprice() {
        return aprice;
    }

    public void setAprice(BigDecimal aprice) {
        this.aprice = aprice;
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
