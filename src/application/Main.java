package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.farng.mp3.TagException;

import java.io.IOException;


public class Main extends Application 
{
	Stage window;
	Scene verwaltungsModus, benutzerModus;
    TableView<Tabelle> neuTabelle, playlist1;
    TextField pathEingabe;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
        window.setTitle("Musikverwaltung");
        window.setMinHeight(600);
        window.setMinWidth(1024);
        
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
        // Die Exceptions müssen noch behandelt werden. + Entscheidung ob die Eingabe vom Path die Lösung ist.
        Button addButton = new Button("Hinzufügen");
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

        Button deleteButton = new Button("Löschen");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button bModButton = new Button("Zum Benutzermodus");    //Um in Benutzer zu gelangen = bMod
        bModButton.setOnAction(e -> window.setScene(benutzerModus));

        //Layout für die Eingabe
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
        
        

        //Layout für die Tabelle
        HBox tabLayout = new HBox();
        tabLayout.getChildren().addAll(neuTabelle, playlist1);
        tabLayout.setPadding(new Insets(10,10,10,10));
        
        //Layout für die Mod Switches
        Label labelVerwaltungsmodus = new Label("Verwaltungsmodus");
        VBox modLayout = new VBox();
        modLayout.getChildren().addAll(labelVerwaltungsmodus,bModButton); //vModButton,
        
        //Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(modLayout);
        mainLayout.setLeft(tabLayout);
        mainLayout.setBottom(eingLayout);

        verwaltungsModus = new Scene(mainLayout,1024,600);

        //Starten im Verwaltungsmodus
        window.setScene(verwaltungsModus);
        window.show();

        /*
        *** Benutzermodus *
        *********************
        ***
        *
        */

        BorderPane playerLayout = new BorderPane();

        VBox modLayout2 = new VBox(10);
        modLayout2.setPadding(new Insets(5, 5, 5, 5));

        //Linke Seite Benutzermodus
        Label labelBenutzermodus = new Label("Benutzermodus");
        Button button2 = new Button("Zum Verwaltungsmodus");
        button2.setOnAction(e -> window.setScene(verwaltungsModus));
        //Liste der Playlists
        Label playlistAuswahlLabel = new Label("Plalist auswählen:");
        ListView<String> playListView = new ListView<String>(); //Datentyp anpassen!
        playListView.getItems().addAll("Testliste1","Testliste2", "Testliste3","Testliste4");
        playListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Button für Playlist
        Button playlistAuswahl = new Button("Playlist verwenden");
        playlistAuswahl.setOnAction(event -> {                       //!!! Funktion fehlt!
            playlistAuswahlClicked();
            System.out.println("Funktion für Playlist Wahl!");
        });
        playerLayout.getChildren().addAll(playListView);

        modLayout2.getChildren().addAll(labelBenutzermodus,button2,playlistAuswahlLabel,playListView,playlistAuswahl);

        playerLayout.setLeft(modLayout2);

        //Playaerelemente in horizontaler Anordnung
        HBox playerSteuerung = new HBox(40);
        playerSteuerung.setPadding(new Insets(5, 5, 5, 5));
        //Player buttons
        Button skipBack = new Button("Zurück");
        Button play = new Button("Abspielen");
        Button skipForward = new Button("Nächster");
        playerSteuerung.getChildren().addAll(skipBack,play,skipForward);
        playerLayout.setBottom(playerSteuerung);
        playerLayout.setAlignment(playerSteuerung, Pos.BOTTOM_CENTER);        // Playelemente Position



        benutzerModus = new Scene(playerLayout, 1024, 600);

    }

    //Hinzufügen Button
	/*
	 * Man kann hier mit der If Abfrage noch einfügen, dass er das leere Feld rot highlighted !
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

    //Löschen Button
    public void deleteButtonClicked()
    {
        ObservableList<Tabelle> Tabellenelected, allTabellen;
        allTabellen = neuTabelle.getItems();
        Tabellenelected = neuTabelle.getSelectionModel().getSelectedItems();

        Tabellenelected.forEach(allTabellen::remove);
    }
    
    //Verwaltungs Button
    /*
    public void vModButtonClicked()
    {
    }
    
    //Benutzer Button
    public void bModButtonClicked()
    {
    }
    */
    private void playlistAuswahlClicked()
    {
        ObservableList<String> auswahl;
        System.out.println("Boom");
    }
    //Einfügen der Anfangswerte in die Tabelle
    public ObservableList<Tabelle> getTabelle()
    {
        ObservableList<Tabelle> tabellen = FXCollections.observableArrayList();
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Dreck"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Müll"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Scheiß"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Ohrenkrebs"));
        tabellen.add(new Tabelle("Justin Bieber", "Baby", "Lieber Eier in Piranhabecken hängen"));
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
