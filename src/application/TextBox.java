package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TextBox
{

    //Create variable
    static String playlistName;

    public static String display(String title, String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        //Anlegen Textfeld
        TextField userEingabe = new TextField();

        //Create buttons
        Button yesButton = new Button("Anlegen");
        Button noButton = new Button("Verwerfen");

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            playlistName = userEingabe.getText();
            window.close();
        });
        noButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label,userEingabe, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return playlistName;
    }
}
