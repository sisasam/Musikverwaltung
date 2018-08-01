package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
	
	static String auswahl;
	
	ComboBox<String> genre = new ComboBox<String>();

    public static void display(String title, String message) 
    {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Okay");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    
    public String genreSuche(String title)
    {
        Stage window = new Stage();
        

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        
        genre.getItems().addAll(
        		"Fuck",
        		"Me",
        		"right",
        		"up"
        		);
        Button closeButton = new Button("Zurück");
        closeButton.setOnAction(e -> window.close());
        Button submitButton = new Button("Auswählen");
        //TODO Dropdown für Auswahl von Genre.
        submitButton.setOnAction(e -> {
        	String auswahl = genre.getValue();
        	});
        

        VBox layout = new VBox(10);
        layout.getChildren().addAll(closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        
        
        return auswahl;
    }
    
    private String genreAus()
    {
    	String auswahl = genre.getValue();
    	return auswahl;
    }
    
    public static String interSuche(String title)
    {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        
        Button closeButton = new Button("Zurück");
        closeButton.setOnAction(e -> window.close());
        Button submitButton = new Button("Auswählen");
        //TODO Dropdown für Auswahl von Genre.
        submitButton.setOnAction(e -> {
        	String auswahl = "";
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        return auswahl;
    }

}