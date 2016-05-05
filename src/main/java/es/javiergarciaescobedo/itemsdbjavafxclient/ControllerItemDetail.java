package es.javiergarciaescobedo.itemsdbjavafxclient;

import es.javiergarciaescobedo.itemsdbjavafxclient.model.Categories;
import es.javiergarciaescobedo.itemsdbjavafxclient.model.Category;
import es.javiergarciaescobedo.itemsdbjavafxclient.model.Item;
import es.javiergarciaescobedo.itemsdbjavafxclient.model.Items;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
public class ControllerItemDetail implements Initializable {

    public static byte INSERT_MODE = 0;
    public static byte EDIT_MODE = 1;
    
    private TableView<Item> tableView;
    private Item item;
    private byte mode; // Inserting or editing

    @FXML
    private TextField textFieldAstring;
    @FXML
    private TextField textFieldAnumber;
    @FXML
    private DatePicker datePicker;    
    @FXML
    private GridPane paneToolBar;
    @FXML
    private CheckBox checkBoxAboolean;
    @FXML
    private TextField textFieldAprice;
    @FXML
    private TextField textFieldAdouble;
    @FXML
    private Spinner<?> spinnerHours;
    @FXML
    private Spinner<?> spinnerMinutes;
    //Se especifica manualmente que es un ComboBox de Category
    @FXML    
    private ComboBox<Category> comboBoxCategory;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Especificar tipos de valores para los spinner de horas y minutos
        SpinnerValueFactory svfHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        spinnerHours.setValueFactory(svfHours);
        spinnerHours.setEditable(true);
        SpinnerValueFactory svfMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        spinnerMinutes.setValueFactory(svfMinutes);
        spinnerMinutes.setEditable(true);
        
        // Descargar la lista con items que hay en la BD
        Categories categories = new Categories();
        categories = (Categories)HelperRestfulConnection.requestServletDBAction(
                categories, "GET", "RequestCategories");
        // Pasar la lista a la tabla
        ObservableList observableListItems = FXCollections.observableArrayList(categories.getCategoriesList());
        comboBoxCategory.setItems(observableListItems);
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

        // Mostrar un valor de tipo fecha en un DatePicker
        HelperJavaFx.setDateInDatePicker(datePicker, item.getAdate());  

        // Mostrar un valor numérico con decimales
        textFieldAdouble.setText(String.valueOf(item.getAdouble()));

        // Mostrar datos de horas y minutos en Spinners respectivos
        SimpleDateFormat dateFormatHours = new SimpleDateFormat("h");
        spinnerHours.getEditor().setText(dateFormatHours.format(item.getAtime()));
        SimpleDateFormat dateFormatMinutes = new SimpleDateFormat("m");
        spinnerMinutes.getEditor().setText(dateFormatMinutes.format(item.getAtime()));
        
        // Mostrar valor boolean activando o no un checkbox
        if(item.getAboolean() != null) {
            checkBoxAboolean.setSelected(item.getAboolean());
        }
        
        // Mostrar un valor de tipo moneda (DECIMAL en BD) (formato con 2 decimales)
        if(item.getAprice() != null) {
            // Se pone formato americano para usar el punto como separador de decimales
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            // Rellenar con 0 si hay menos de 2 decimales
            numberFormat.setMinimumFractionDigits(2);
            // No se usa separador de millares
            numberFormat.setGroupingUsed(false);
            textFieldAprice.setText(numberFormat.format(item.getAprice().floatValue()));
        }
        
        // Seleccionar la categoría en el combobox
        comboBoxCategory.getSelectionModel().select(item.getCategory());
    }
    
    @FXML
    private void onMouseClickedButtonSave(MouseEvent event) {   
        boolean errorInData = false;
        
        // ACTUALIZAR LAS PROPIEDADES DEL OBJETO EDITADO
        
        // Actualizar el campo String
        item.setAstring(textFieldAstring.getText());
        
        // Actualizar el campo numérico
        try {
            item.setAnumber(Integer.valueOf(textFieldAnumber.getText()));
        } catch(NumberFormatException e) {
            // Si no se introduce un valor numérico se deja seleccionado
            errorInData = true;
            textFieldAnumber.requestFocus();
        }
        
        // Actualizar el campo fecha
        item.setAdate(HelperJavaFx.getDateFromDatePicket(datePicker));

        // Actualizar el valor numérico con decimales
        try {
            item.setAdouble(Double.valueOf(textFieldAdouble.getText()));
        } catch(NumberFormatException e) {
            // Si no se introduce un valor numérico se deja seleccionado
            errorInData = true;
            textFieldAdouble.requestFocus();
        }
        
        // Actualizar datos de horas y minutos desde Spinners respectivos
        try {
            SimpleDateFormat dateFormatHours = new SimpleDateFormat("h:m");
            String strHours = spinnerHours.getEditor().getText();
            String strMinutes = spinnerMinutes.getEditor().getText();
            // Convertir los datos de horas y minutos a tipo Date
            Date time = dateFormatHours.parse(strHours+":"+strMinutes);
            item.setAtime(time);
        } catch (ParseException ex) {
            errorInData = true;
            spinnerHours.requestFocus();
        }
        
        // Actualizar valor boolean
        item.setAboolean(checkBoxAboolean.isSelected());
        
        // Actualizar valor de tipo moneda
        try {
            item.setAprice(BigDecimal.valueOf(Double.valueOf(textFieldAprice.getText())));
        } catch(NumberFormatException e) {
            // Si no se introduce un valor numérico se deja seleccionado
            errorInData = true;
            textFieldAprice.requestFocus();
        }
        
        // Actualizar la categoría del ítem
        item.setCategory(comboBoxCategory.getSelectionModel().getSelectedItem());
        
        // CERRAR ESTA PANTALLA sólo si no hay ningún dato erróneo
        if(!errorInData) {
            // ENVIAR AL SERVIDOR LA ACCIÓN A REALIZAR
            Items items = new Items();
            items.getItemsList().add(item);
            if(mode == INSERT_MODE) {
                items = (Items)HelperRestfulConnection.requestServletDBAction(
                        items, "POST", "RequestItems");
            } else { // EDIT_MODE
                items = (Items)HelperRestfulConnection.requestServletDBAction(
                        items, "PUT", "RequestItems");
            }
            
            // Actualizar el ítem con los datos recibidos del servidor
            item = items.getItemsList().get(0);

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
