package es.javiergarciaescobedo.masterdetailfxml;

import java.util.Date;

public class Item {
 
    private String astring;
    private int anumber;
    private Date adate;

    public Item() {
    }

    public Item(String astring, int anumber, Date adate) {
        this.astring = astring;
        this.anumber = anumber;
        this.adate = adate;
    }

    public String getAstring() {
        return astring;
    }

    public void setAstring(String astring) {
        this.astring = astring;
    }

    public int getAnumber() {
        return anumber;
    }

    public void setAnumber(int anumber) {
        this.anumber = anumber;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }


}
