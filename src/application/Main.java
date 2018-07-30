package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.farng.mp3.TagException;

import java.io.IOException;


public class Main extends Application 
{
	Stage verwaltungsModus,benutzerModus;
    TableView<Tabelle> neuTabelle, playlist1;
    TextField pathEingabe;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		verwaltungsModus = primaryStage;
        verwaltungsModus.setTitle("Musikverwaltung");
        verwaltungsModus.setMinHeight(600);
        verwaltungsModus.setMinWidth(1024);
        
        //Nr Spalte
        TableColumn<Tabelle, String> nrSpalte = new TableColumn<>("Nr.");
        nrSpalte.setPrefWidth(20);	//bevorzugte Spaltenbreite
        nrSpalte.setMinWidth(20);	//minimale Spaltenbreite
        nrSpalte.setMaxWidth(30);	//maximale Spaltenbreite
        nrSpalte.setCellValueFactory(new PropertyValueFactory<>("nr"));

        //Titel Spalte
        TableColumn<Tabelle, String> titelSpalte = new TableColumn<>("Titel");
        titelSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
        titelSpalte.setMinWidth(50);	//minimale Spaltenbreite
        titelSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        titelSpalte.setCellValueFactory(new PropertyValueFactory<>("titel"));

        //Interpreten Spalte
        TableColumn<Tabelle, Double> interpretenSpalte = new TableColumn<>("Interpret");
        interpretenSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
        interpretenSpalte.setMinWidth(50);	//minimale Spaltenbreite
        interpretenSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        interpretenSpalte.setCellValueFactory(new PropertyValueFactory<>("interpret"));

        //Genre Spalte
        TableColumn<Tabelle, String> genreSpalte = new TableColumn<>("Genre");
        genreSpalte.setPrefWidth(170);	//bevorzugte Spaltenbreite
        genreSpalte.setMinWidth(50);	//minimale Spaltenbreite
        genreSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        genreSpalte.setCellValueFactory(new PropertyValueFactory<>("genre"));

        //Titel Eingabe
        pathEingabe = new TextField();
        pathEingabe.setPromptText("Geben Sie den Path der Musikdatei ein, Bsp.: C:\\Users\\...\\Musik\\Bsp.mp3");
        pathEingabe.setMinWidth(300);

        //Die beinden Eingaben werden nicht mehr gebraucht, da die Informationen automatisch von den MP3's geladen wird
//        //Interpreten Eingabe
//        interEingabe = new TextField();
//        interEingabe.setPromptText("Interpret");
//
//        //Genre Eingabe
//        genreEingabe = new TextField();
//        genreEingabe.setPromptText("Genre");

        //Button
        // Die Exceptions m�ssen noch behandelt werden. + Entscheidung ob die Eingabe vom Path die L�sung ist.
        Button addButton = new Button("Hinzuf�gen");
        addButton.setOnAction(e -> {
			try
			{
				addButtonClicked();
			}
			catch (IOException e1)
			{
				Alert notFound = new Alert(Alert.AlertType.ERROR, "File Path not found");
				
				e1.printStackTrace();
			}
			catch (TagException e1)
			{
				Alert notFound = new Alert(Alert.AlertType.ERROR, "File Path not found");
				
				e1.printStackTrace();
			}
		});
        Button deleteButton = new Button("L�schen");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button vModButton = new Button("Verwaltungsmodus"); //Um in Verwaltung zu gelangen = vMod
        vModButton.setOnAction(e -> vModButtonClicked());
        Button bModButton = new Button("Benutzermodus");    //Um in Benutzer zu gelangen = bMod
        bModButton.setOnAction(e -> bModButtonClicked());

        //Layout f�r die Eingabe
        HBox eingLayout = new HBox();
        eingLayout.setPadding(new Insets(10,10,10,10));
        eingLayout.setSpacing(10);
        eingLayout.getChildren().addAll(pathEingabe, addButton, deleteButton);

        //Tabelle erstellen
        neuTabelle = new TableView<>();
//        neuTabelle.setPrefWidth(300); //Sollte ich das rein nehmen?
        neuTabelle.setItems(getTabelle());
        neuTabelle.getColumns().addAll(nrSpalte, titelSpalte, interpretenSpalte, genreSpalte);
        
        playlist1 = new TableView<>();
        playlist1.setItems(getTabelle());
        playlist1.getColumns().addAll(nrSpalte, titelSpalte, interpretenSpalte, genreSpalte);
        
        

        //Layout f�r die Tabelle
        HBox tabLayout = new HBox();
        tabLayout.getChildren().addAll(neuTabelle, playlist1);
        tabLayout.setPadding(new Insets(10,10,10,10));
        
        //Layout f�r die Mod Switches
        HBox modLayout = new HBox();
        modLayout.getChildren().addAll(vModButton, bModButton);
        
        //Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(modLayout);
        mainLayout.setLeft(tabLayout);
        mainLayout.setBottom(eingLayout);

        Scene scene = new Scene(mainLayout);
        verwaltungsModus.setScene(scene);
        verwaltungsModus.show();
    }

    //Hinzuf�gen Button
	/*
	 * Man kann hier mit der If Abfrage noch einf�gen, dass er das leere Feld rot highlighted !
	 */
    public void addButtonClicked() throws IOException, TagException
    {
    	if(!pathEingabe.getText().isEmpty() )
        {
    		TitelEinbinden eingabe = new TitelEinbinden();
    		Tabelle tabelle = new Tabelle();
    		String path = pathEingabe.getText();
    		
//    		tabelle.setTitel(pathEingabe.getText());
//    		tabelle.setInterpret(interEingabe.getText());
//    		tabelle.setGenre(genreEingabe.getText());
    		neuTabelle.getItems().add(eingabe.einbinden(path));
    		pathEingabe.clear();
//    		interEingabe.clear();
//    		genreEingabe.clear();
    	}
    }

    //L�schen Button
    public void deleteButtonClicked()
    {
        ObservableList<Tabelle> Tabellenelected, allTabellen;
        allTabellen = neuTabelle.getItems();
        Tabellenelected = neuTabelle.getSelectionModel().getSelectedItems();

        Tabellenelected.forEach(allTabellen::remove);
    }
    
    //Verwaltungs Button
    public void vModButtonClicked()
    {
    	
    }
    
    //Benutzer Button
    public void bModButtonClicked()
    {
    	
    }
    

    //Einf�gen der Anfangswerte in die Tabelle
    public ObservableList<Tabelle> getTabelle()
    {
        ObservableList<Tabelle> tabellen = FXCollections.observableArrayList();
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Dreck"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "M�ll"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Schei�"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Ohrenkrebs"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Lieber Eier in Piranhabecken h�ngen"));
//        Tabellen.add(new Tabelle(TitelEinbinden.einbinden()));
        return tabellen;
    }
    
//    public ObservableList<Tabelle> getPlaylist()
//    {
//    	TitelEinbinden ein = new TitelEinbinden();
//    	ObservableList<Tabelle> playlist = FXCollections.observableArrayList();
//    	playlist.add(ein.einbinden(path))
//    	return playlist
//    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
