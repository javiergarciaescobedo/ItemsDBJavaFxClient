package es.javiergarciaescobedo.masterdetailfxml;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
public class Screen1 implements Initializable {

    @FXML
    private TextField textFieldAstring;

    private Item item;
    @FXML
    private TextField textFieldAnumber;
    @FXML
    private DatePicker datePicker;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        item = Screen0.itemSelected;
        textFieldAstring.setText(item.getAstring());
        textFieldAnumber.setText(String.valueOf(item.getAnumber()));
        setDateInDatePicker(datePicker, item.getAdate());
    }    

    @FXML
    private void onMouseClickedButtonBack(MouseEvent event) {        
        // Actualizar las propiedades del objeto editado
        item.setAstring(textFieldAstring.getText());
        
        // Actualizar el objeto en la tabla
        Screen0.observableListItems.set(Screen0.indexItemSelected, item);
        
        // Eliminar esta pantalla
        int lastScreensNumber = Main.root.getChildren().size() - 1;
        Main.root.getChildren().remove(lastScreensNumber);
    }
    
    public void setDateInDatePicker(DatePicker datePicker, Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
        datePicker.setValue(localDate);
    }
}
