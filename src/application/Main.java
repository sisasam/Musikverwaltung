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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.farng.mp3.TagException;
import javafx.scene.text.*;


import java.beans.MethodDescriptor;
import java.io.IOException;


public class Main extends Application 
{
	Stage window;
	Scene verwaltungsModus, benutzerModus;
    TableView<Tabelle> neuTabelle, playlist1;
    TextField pathEingabe;
    Text MD, PL;
    
    
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
        window.setTitle("Musikverwaltung");
        window.setMinHeight(600);
        window.setMinWidth(1024);
        
        TableSetting TS = new TableSetting();
        
        //TODO das ersetzen des Spaltenlayouts mit neuer Klasse um weitere Playlisten hinzuzufügen

        //Titel Spalte
        TableColumn<Tabelle, String> titelSpalte = new TableColumn<>("Titel");
//        titelSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
//        titelSpalte.setMinWidth(50);	//minimale Spaltenbreite
//        titelSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        TS.setting(titelSpalte);
        titelSpalte.setCellValueFactory(new PropertyValueFactory<>("titel"));

        //Titel Spalte
        TableColumn<Tabelle, String> titelSpalte2 = new TableColumn<>("Titel");
//        titelSpalte2.setPrefWidth(100);	//bevorzugte Spaltenbreite
//        titelSpalte2.setMinWidth(50);	//minimale Spaltenbreite
//        titelSpalte2.setMaxWidth(150);	//maximale Spaltenbreite
        TS.setting(titelSpalte);
        titelSpalte2.setCellValueFactory(new PropertyValueFactory<>("titel"));


        //Interpreten Spalte
        TableColumn<Tabelle, Double> interpretenSpalte = new TableColumn<>("Interpret");
        interpretenSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
        interpretenSpalte.setMinWidth(50);	//minimale Spaltenbreite
        interpretenSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        interpretenSpalte.setCellValueFactory(new PropertyValueFactory<>("interpret"));

        //Interpreten Spalte
        TableColumn<Tabelle, Double> interpretenSpalte2 = new TableColumn<>("Interpret");
        interpretenSpalte2.setPrefWidth(100);	//bevorzugte Spaltenbreite
        interpretenSpalte2.setMinWidth(50);	//minimale Spaltenbreite
        interpretenSpalte2.setMaxWidth(150);	//maximale Spaltenbreite
        interpretenSpalte2.setCellValueFactory(new PropertyValueFactory<>("interpret"));
        //Genre Spalte
        TableColumn<Tabelle, String> genreSpalte = new TableColumn<>("Genre");
        genreSpalte.setPrefWidth(170);	//bevorzugte Spaltenbreite
        genreSpalte.setMinWidth(50);	//minimale Spaltenbreite
        genreSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        genreSpalte.setCellValueFactory(new PropertyValueFactory<>("genre"));
        //Genre Spalte2
        TableColumn<Tabelle, String> genreSpalte2 = new TableColumn<>("Genre");
        genreSpalte2.setPrefWidth(170);	//bevorzugte Spaltenbreite
        genreSpalte2.setMinWidth(50);	//minimale Spaltenbreite
        genreSpalte2.setMaxWidth(150);	//maximale Spaltenbreite
        genreSpalte2.setCellValueFactory(new PropertyValueFactory<>("genre"));

        //Titel Eingabe
        pathEingabe = new TextField();
        pathEingabe.setPromptText("Geben Sie den Path der Musikdatei ein, Bsp.: C:\\Users\\...\\Musik\\Bsp.mp3");
        pathEingabe.setMinWidth(300);

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
        Button inDiePlaylist = new Button("In die Playlist");
        inDiePlaylist.setOnAction(e -> inDiePlaylistClicked());
        Button ausDerPlaylist = new Button("Aus der Playlist");
        ausDerPlaylist.setOnAction(e -> ausDerPlaylistClicked());

        //Layout für die Eingabe
        HBox eingLayout = new HBox();
        eingLayout.setPadding(new Insets(10,10,10,10));
        eingLayout.setSpacing(10);
        eingLayout.getChildren().addAll(pathEingabe, addButton, deleteButton);

        //Tabelle erstellen
        neuTabelle = new TableView<>();
//        neuTabelle.setPrefWidth(300); //Sollte ich das rein nehmen?
        neuTabelle.setItems(getTabelle());
        neuTabelle.getColumns().addAll(titelSpalte, interpretenSpalte, genreSpalte);
        
        playlist1 = new TableView<>();
//        playlist1.setItems(getTabelle());
        playlist1.getColumns().addAll(titelSpalte2, interpretenSpalte2, genreSpalte2);
        
        //Musikdatenbank TEXT
        MD = new Text();
        MD.setText("Musikdatenbank");
        MD.setFont(new Font(20));
        
        PL = new Text();
        PL.setText("Playlist");
        PL.setFont(new Font(20));

        //Layout für die Tabelle
        VBox tabLayout = new VBox();
        tabLayout.getChildren().addAll(MD, neuTabelle);
        tabLayout.setPadding(new Insets(10,10,10,10));
        
        //Layout für die Playlist
        VBox plLayout = new VBox();
        plLayout.getChildren().addAll(PL, playlist1);
        plLayout.setPadding(new Insets(10,10,10,10));
        
        //Layout für die Mod Switches
        Label labelVerwaltungsmodus = new Label("Verwaltungsmodus");
        VBox modLayout = new VBox();
        modLayout.getChildren().addAll(labelVerwaltungsmodus,bModButton); //vModButton,
        
        VBox playlistSwitcher = new VBox();
        playlistSwitcher.getChildren().addAll(ausDerPlaylist, inDiePlaylist);
        playlistSwitcher.setAlignment(Pos.CENTER);
        playlistSwitcher.setPadding(new Insets(10, 10, 10, 10));
        playlistSwitcher.setSpacing(10);
        
        
        //Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(modLayout);
        mainLayout.setRight(plLayout);
        mainLayout.setBottom(eingLayout);
        mainLayout.setLeft(tabLayout);
        mainLayout.setCenter(playlistSwitcher);

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
        playlistAuswahl.setOnAction(event -> {
        	// TODO Funktion fehlt!!
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

    private void inDiePlaylistClicked()
	{
		// TODO von der Liste in die Playlist übernehmen. (Funktioniert noch nicht. AAAAAber fast.)
    	ObservableList<Tabelle> Tabellenelected, allTabellen, Tabellenelected2, allTabellen2;
        allTabellen = neuTabelle.getItems();
        Tabellenelected = neuTabelle.getSelectionModel().getSelectedItems();
        
        allTabellen2 = playlist1.getItems();
        Tabellenelected2 = playlist1.getSelectionModel().getSelectedItems();
        
        Tabellenelected.forEach(allTabellen2::add);
        
        
        
        
        
        
	}

	private void ausDerPlaylistClicked()
	{
		// TODO Die Funktion bearbeiten, (aktuell nur gleiche Fkt. wie Löschen Button)
		 ObservableList<Tabelle> Tabellenelected, allTabellen;
	        allTabellen = playlist1.getItems();
	        Tabellenelected = playlist1.getSelectionModel().getSelectedItems();

	        Tabellenelected.forEach(allTabellen::remove);
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
            neuTabelle.getItems().add(eingabe.einbinden(path));
    		pathEingabe.clear();
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
        return tabellen;
    }

    // Wird im Moment noch nicht verwendet --- Funktion eventuell für automatisches Erstellen aller Songs
    public ObservableList<Tabelle> getPlaylist(ObservableList<Tabelle> path) throws IOException, TagException
    {
//        TitelEinbinden ein = new TitelEinbinden();
//        ObservableList<Tabelle> playlist = FXCollections.observableArrayList();
//        playlist.add(ein.einbinden(path));
        return path;
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
