package es.javiergarciaescobedo.masterdetailfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
public class Screen0 implements Initializable {

    @FXML
    private TableView<Item> tableView;

    public static Item itemSelected;
    public static int indexItemSelected;
    public static ObservableList<Item> observableListItems;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn tableColumnAstring = new TableColumn("A String");
        tableColumnAstring.setMinWidth(50);
        tableColumnAstring.setPrefWidth(100);
        tableColumnAstring.setMaxWidth(150);
        tableColumnAstring.setCellValueFactory(new PropertyValueFactory("astring"));
 
        TableColumn tableColumnAnumber = new TableColumn("A Number");
        tableColumnAnumber.setMinWidth(50);
        tableColumnAnumber.setPrefWidth(100);
        tableColumnAnumber.setMaxWidth(150);
        tableColumnAnumber.setCellValueFactory(new PropertyValueFactory("anumber"));
 
        TableColumn tableColumnAdate = new TableColumn("A Date");
        tableColumnAdate.setMinWidth(50);
        tableColumnAdate.setPrefWidth(100);
        tableColumnAdate.setMaxWidth(150);
        tableColumnAdate.setCellValueFactory(new PropertyValueFactory("adate"));
        UtilJavaFx.setDateFormatColumn(tableColumnAdate, "dd/MM/yyyy");
 
        ArrayList<Item> listItems = new ArrayList();
        // Cargar la lista de items con datos de ejemplo
        listItems.add(new Item("item0", 0, Calendar.getInstance().getTime()));
        listItems.add(new Item("item1", 1, Calendar.getInstance().getTime()));
        listItems.add(new Item("item2", 2, Calendar.getInstance().getTime()));
        
        // Pasar la lista a la tabla
        observableListItems = FXCollections.observableArrayList(listItems);
        tableView.setItems(observableListItems);
        
        tableView.getColumns().clear();
        tableView.getColumns().addAll(tableColumnAstring, tableColumnAnumber, tableColumnAdate);
        // Permitir que se reajusten los tamaños de las columnas al cambiar
        //  el tamaño de la ventana
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }    

    @FXML
    private void onMouseClickedButtonEdit(MouseEvent event) {
        itemSelected = tableView.getSelectionModel().getSelectedItem();
        indexItemSelected = tableView.getSelectionModel().getSelectedIndex();
        try {
            Parent parentScreen1 = FXMLLoader.load(getClass().getResource("/fxml/Screen1.fxml"));
            Main.root.getChildren().add(parentScreen1);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
