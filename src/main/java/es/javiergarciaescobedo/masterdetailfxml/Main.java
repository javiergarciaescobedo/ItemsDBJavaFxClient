package es.javiergarciaescobedo.masterdetailfxml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 */
public class Main extends Application {
    
    public static StackPane root;
    
    @Override
    public void start(Stage primaryStage) {
        try {            
//            FXMLLoader personDetailLoader = new FXMLLoader(getClass().getResource("/fxml/Screen0.fxml"));
//            Parent personDetailParent = (Parent)personDetailLoader.load();
            Parent personDetailParent = FXMLLoader.load(getClass().getResource("/fxml/Screen0.fxml"));
            
            root = new StackPane();
            root.getChildren().add(personDetailParent);     
            
            Scene scene = new Scene(root, 300, 250);            
            primaryStage.setTitle("Master Detail FXML");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
