package es.javiergarciaescobedo.itemsdbjavafxclient;

import es.javiergarciaescobedo.itemsdbjavafxclient.model.Item;
import es.javiergarciaescobedo.itemsdbjavafxclient.model.Items;
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
public class ControllerItemDetail implements Initializable {

    @FXML
    private TextField textFieldAstring;
    @FXML
    private TextField textFieldAnumber;
    @FXML
    private DatePicker datePicker;
    
    public static byte INSERT_MODE = 0;
    public static byte EDIT_MODE = 1;
    
    private TableView<Item> tableView;
    private Item item;
    private byte mode; // Inserting or editing
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public void setTableView(TableView<Item> tableView) {
        this.tableView = tableView;
    }
    
    public void showItemSelectedData() {
        // Obtener el objeto que está seleccionado en la tabla
        item = tableView.getSelectionModel().getSelectedItem();
        // Rellenar los controles de la pantalla con los datos de ese objeto
        textFieldAstring.setText(item.getAstring());
        textFieldAnumber.setText(String.valueOf(item.getAnumber()));
        HelperJavaFx.setDateInDatePicker(datePicker, item.getAdate());     
    }
    
    @FXML
    private void onMouseClickedButtonSave(MouseEvent event) {   
        boolean errorInData = false;
        
        // ACTUALIZAR LAS PROPIEDADES DEL OBJETO EDITADO
        
        // ACTUALIZAR el campo String
        item.setAstring(textFieldAstring.getText());
        
        // ACTUALIZAR el campo numérico
        try {
            item.setAnumber(Integer.valueOf(textFieldAnumber.getText()));
        } catch(NumberFormatException e) {
            // Si no se introduce un valor numérico se deja seleccionado
            errorInData = true;
            textFieldAnumber.requestFocus();
        }
        
        // ACTUALIZAR el campo fecha
        item.setAdate(HelperJavaFx.getDateFromDatePicket(datePicker));
        
        // Enviar al servidor la acción a realizar
        Items items = new Items();
        items.getItemsList().add(item);
        if(mode == INSERT_MODE) {
            items = (Items)HelperServletConnection.requestServletDBAction(
                    items, HelperServletConnection.ACTION_INSERT);
        } else { // EDIT_MODE
            items = (Items)HelperServletConnection.requestServletDBAction(
                    items, HelperServletConnection.ACTION_UPDATE);
        }
        // Actualizar el ítem con los datos recibidos del servidor
        item = items.getItemsList().get(0);

        // Cerrar esta pantalla sólo si no hay ningún dato erróneo
        if(!errorInData) {
            // Actualizar el objeto en la tabla, asignando el mismo objeto
            //  en la posición actual de la lista
            int indexItemSelected = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().set(indexItemSelected, item);

            // Muestra el objeto como seleccionado en la tabla
            tableView.requestFocus();
            tableView.getSelectionModel().select(item);
            tableView.getFocusModel().focus(
                    tableView.getSelectionModel().getSelectedIndex());
            tableView.scrollTo(item);

            // Eliminar esta pantalla, obteniendo la posición que ocupa en la
            //  lista de hijos del elemento raíz (contenedor) principal
            int lastScreensNumber = Main.root.getChildren().size() - 1;
            Main.root.getChildren().remove(lastScreensNumber);
        }
    }
    

}
