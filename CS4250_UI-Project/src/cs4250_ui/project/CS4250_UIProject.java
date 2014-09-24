package cs4250_ui.project;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public final class CS4250_UIProject extends Application {

    // This was created for testing purposes
    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(final Stage stage) {
        stage.setTitle("File Chooser Sample");

        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Open Image");
        final Button openTextButton = new Button("Open Text");   

        openButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    configureFileChooserImage(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        openFile(file);
                    }
                }
            });

        openTextButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    configureFileChooserText(fileChooser);                               
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        openFile(file);
                    }
                }
            });

        // Build sample scene with two buttons for loading text or images
        // Can be swapped out for whatever UI components that are implemented
        final GridPane inputGridPane = new GridPane();

        GridPane.setConstraints(openButton, 0, 1);
        GridPane.setConstraints(openTextButton, 1, 1);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton, openTextButton);
        
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private static void configureFileChooserImage(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
    }
    
    private static void configureFileChooserText(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Text Files");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
            );
    }
    
    private void openFile(File file) {
        /*
            Charles and Jaime, This is our test to get image and text loading working.  
            This is called by the handlers of the FileChooser.
        */ 
       
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(CS4250_UIProject.class.getName()).log(
                Level.SEVERE, null, ex
            );
        }
    }
    
    /*      
            Charles & Jaime, sample usage with preferred FileChooser for 
            save location.  Extracts an image from an ImageView.
            Not 100% positive saveImage or saveText work without integration 
            with UI.  Will work with you if there are any hangups with these 
            methods.
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            File file = fileChooser.showSaveDialog(stage);
            saveImage(file, imgView);
    */
    private boolean saveImage(File file, ImageView imgView){
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imgView.getImage(),
                    null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }
        
        return true;
    }
    
    private boolean saveText(File file, String text){
        if (file != null) {
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
                writer.write(text, 0, text.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
                return false;
            }
        }
        
        return true;
    }
}
    