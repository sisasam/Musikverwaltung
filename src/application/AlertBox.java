package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
	
	static String auswahl;
	

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
        layout.setPadding(new Insets(5,5,5,5));

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
        
    	ComboBox<String> genre = new ComboBox<String>();
        genre.getItems().addAll(
        		"Rock",
        		"Pop",
        		"HipHop",
        		"Bluse",
        		"..."
        		);
        Label label = new Label();
        label.setText("Leider Funktioniert die Auswahl nicht.");
        Button closeButton = new Button("Zurück");
        closeButton.setOnAction(e -> window.close());
        Button submitButton = new Button("Auswählen");
        submitButton.setOnAction(e -> {
        	display("Leider nicht", "Hier könnte Ihre Werbung stehen.");
        	});
        
        
        HBox subLayout = new HBox(10);
        subLayout.getChildren().addAll(submitButton, closeButton);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, genre, subLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(5,5,5,5));

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        
        
        return auswahl;
    }
    
    public static String interSuche(String title)
    {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        TextField interpret = new TextField();
        interpret.setPromptText("Interpreten Eingabe");
        Label label = new Label();
        label.setText("Leider Funktioniert die Auswahl nicht.");
        Button closeButton = new Button("Zurück");
        closeButton.setOnAction(e -> window.close());
        Button submitButton = new Button("Auswählen");
        submitButton.setOnAction(e -> {
        	display("Leider nicht", "Hier könnte Ihre Werbung stehen.");
        	});
        
        
        HBox subLayout = new HBox(10);
        subLayout.getChildren().addAll(submitButton, closeButton);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, interpret, subLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(5,5,5,5));

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        return auswahl;
    }

}