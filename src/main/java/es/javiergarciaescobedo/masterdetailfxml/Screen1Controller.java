package es.javiergarciaescobedo.masterdetailfxml;

import es.javiergarciaescobedo.masterdetailfxml.model.Item;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
public class Screen1Controller implements Initializable {

    @FXML
    private TextField textFieldAstring;
    @FXML
    private TextField textFieldAnumber;
    @FXML
    private DatePicker datePicker;
    
    private TableView<Item> tableView;
    private Item itemSelected;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void setTableView(TableView<Item> tableView) {
        this.tableView = tableView;
    }
    
    public void showItemSelectedData() {
        // Obtener el objeto que está seleccionado en la tabla
        itemSelected = tableView.getSelectionModel().getSelectedItem();
        // Rellenar los controles de la pantalla con los datos de ese objeto
        textFieldAstring.setText(itemSelected.getAstring());
        textFieldAnumber.setText(String.valueOf(itemSelected.getAnumber()));
        UtilJavaFx.setDateInDatePicker(datePicker, itemSelected.getAdate());     
    }
    
    @FXML
    private void onMouseClickedButtonSave(MouseEvent event) {   
        boolean errorInData = false;
        
        // ACTUALIZAR LAS PROPIEDADES del objeto editado
        
        // ACTUALIZAR el campo String
        itemSelected.setAstring(textFieldAstring.getText());
        
        // ACTUALIZAR el campo numérico
        try {
            itemSelected.setAnumber(Integer.valueOf(textFieldAnumber.getText()));
        } catch(NumberFormatException e) {
            // Si no se introduce un valor numérico se deja seleccionado
            errorInData = true;
            textFieldAnumber.requestFocus();
        }
        
        // ACTUALIZAR el campo fecha
        itemSelected.setAdate(UtilJavaFx.getDateFromDatePicket(datePicker));
        
        // Cerrar esta pantalla si no hay ningún dato erróneo
        if(!errorInData) {
            // Actualizar el objeto en la tabla, asignando el mismo objeto
            //  en la posición actual de la lista
            int indexItemSelected = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(indexItemSelected, itemSelected);

            // Eliminar esta pantalla, obteniendo la posición que ocupa en la
            //  lista de hijos del elemento raíz (contenedor) principal
            int lastScreensNumber = Main.root.getChildren().size() - 1;
            Main.root.getChildren().remove(lastScreensNumber);
        }
    }
    

}
