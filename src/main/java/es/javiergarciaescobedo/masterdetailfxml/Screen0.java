/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.javiergarciaescobedo.masterdetailfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;

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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn firstNameCol = new TableColumn("prueba");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("astring"));
 
        ArrayList<Item> listItems = new ArrayList();
        listItems.add(new Item("item0", 0, null));
        ObservableList<Item> observableListItems = FXCollections.observableArrayList(listItems);
        tableView.setItems(observableListItems);
        
        tableView.getColumns().clear();
        tableView.getColumns().addAll(firstNameCol);
    }    

    @FXML
    private void onMouseClickedButtonEdit(MouseEvent event) {
        itemSelected = tableView.getSelectionModel().getSelectedItem();
        indexItemSelected = tableView.getSelectionModel().getSelectedIndex();
        try {
            FXMLLoader loaderScreen1 = new FXMLLoader(getClass().getResource("/fxml/Screen1.fxml"));
            Parent parentScreen1 = loaderScreen1.load();
            Main.root.getChildren().add(parentScreen1);
            AnchorPane.setTopAnchor(parentScreen1, 0.0);
            AnchorPane.setRightAnchor(parentScreen1, 0.0);
            AnchorPane.setBottomAnchor(parentScreen1, 0.0);
            AnchorPane.setLeftAnchor(parentScreen1, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
