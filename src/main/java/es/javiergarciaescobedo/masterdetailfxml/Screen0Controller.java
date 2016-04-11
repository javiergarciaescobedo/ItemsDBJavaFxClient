package es.javiergarciaescobedo.masterdetailfxml;

import es.javiergarciaescobedo.masterdetailfxml.model.Item;
import es.javiergarciaescobedo.masterdetailfxml.model.Items;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
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
public class Screen0Controller implements Initializable {

    @FXML
    private TableView<Item> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Columna para ID
        TableColumn tableColumnId = new TableColumn("Id");
        tableColumnId.setMinWidth(50);
        tableColumnId.setPrefWidth(100);
        tableColumnId.setMaxWidth(150);
        tableColumnId.setCellValueFactory(new PropertyValueFactory("id"));
 
        // Columna para String
        TableColumn tableColumnAstring = new TableColumn("A String");
        tableColumnAstring.setMinWidth(50);
        tableColumnAstring.setPrefWidth(100);
        tableColumnAstring.setMaxWidth(150);
        tableColumnAstring.setCellValueFactory(new PropertyValueFactory("astring"));
 
        // Columna para número
        TableColumn tableColumnAnumber = new TableColumn("A Number");
        tableColumnAnumber.setMinWidth(50);
        tableColumnAnumber.setPrefWidth(100);
        tableColumnAnumber.setMaxWidth(150);
        tableColumnAnumber.setStyle( "-fx-alignment: CENTER-RIGHT;");
        tableColumnAnumber.setCellValueFactory(new PropertyValueFactory("anumber"));
 
        // Columna para fecha
        TableColumn tableColumnAdate = new TableColumn("A Date");
        tableColumnAdate.setMinWidth(50);
        tableColumnAdate.setPrefWidth(100);
        tableColumnAdate.setMaxWidth(150);
        tableColumnAdate.setStyle( "-fx-alignment: CENTER;");
        tableColumnAdate.setCellValueFactory(new PropertyValueFactory("adate"));
        UtilJavaFx.setDateFormatColumn(tableColumnAdate, DateFormat.MEDIUM);

        // Descargar la lista con items que hay en la BD
        Items items = ItemsDownloader.downloadItems();
        // Pasar la lista a la tabla
        ObservableList<Item> observableListItems = FXCollections.observableArrayList(
                items.getItemsList());
        tableView.setItems(observableListItems);
        
        tableView.getColumns().clear();
        tableView.getColumns().addAll(tableColumnId, tableColumnAstring, tableColumnAnumber, tableColumnAdate);
        // Permitir que se reajusten los tamaños de las columnas al cambiar
        //  el tamaño de la ventana
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }    

    @FXML
    private void onMouseClickedButtonEdit(MouseEvent event) {
        showScreen1();
    }

    @FXML
    private void onMouseClickedButtonNew(MouseEvent event) {
        Item item = new Item();
        tableView.getItems().add(item);
        tableView.getSelectionModel().select(item);
        showScreen1();
    }

    @FXML
    private void onMouseClickedButtonRemove(MouseEvent event) {        
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
    }
    
    private void showScreen1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Screen1.fxml"));
            Parent parentScreen1 = loader.load();
            Screen1Controller screen1Controller = loader.getController();
            // Pasar una referencia a la tabla para que Screen1 pueda acceder a su contenido
            screen1Controller.setTableView(tableView);
            // Rellenar Screen1 con los datos del objeto actual
            screen1Controller.showItemSelectedData();
            // Mostrar los elementos de Screen1 colgándolo del contenedor raiz
            Main.root.getChildren().add(parentScreen1);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
