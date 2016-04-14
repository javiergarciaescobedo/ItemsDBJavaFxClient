package es.javiergarciaescobedo.itemsdbjavafxclient;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class HelperJavaFx {
    
    public static void setDateFormatColumn(TableColumn dateColumn, int dateFormat) {
        DateFormat format = DateFormat.getDateInstance(dateFormat);
        dateColumn.setCellFactory(myDateTableCell -> {
            return new TableCell<Object, Date>() {
                @Override
                protected void updateItem(Date date, boolean dateIsEmpty) {
                    super.updateItem(date, dateIsEmpty);
                    if (date == null || dateIsEmpty) {
                        setText(null);
                    } else {
                        setText(format.format(date));
                    }
                }
            };
        });        
    }    
    
    public static void setDateInDatePicker(DatePicker datePicker, Date date) {
        if(date != null) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
            datePicker.setValue(localDate);
        }
    }
    
    public static Date getDateFromDatePicket(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if(localDate != null) {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);  
            return date;
        } else {
            return null;
        }
    }
    
}
