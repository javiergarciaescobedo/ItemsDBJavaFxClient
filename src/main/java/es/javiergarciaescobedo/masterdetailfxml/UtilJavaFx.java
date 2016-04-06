package es.javiergarciaescobedo.masterdetailfxml;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class UtilJavaFx {
    
    public static void setDateFormatColumn(TableColumn dateColumn, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        dateColumn.setCellFactory(myDateTableCell -> {
            return new TableCell<Object, Date>() {
                @Override
                protected void updateItem(Date date, boolean dateIsEmpty) {
                    super.updateItem(date, dateIsEmpty);
                    if (date == null || dateIsEmpty) {
                        setText(null);
                    } else {
                        setText(simpleDateFormat.format(date));
                    }
                }
            };
        });
        
    }    
}
