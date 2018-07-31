package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.farng.mp3.TagException;

import java.io.File;
import java.io.IOException;


public class Main extends Application 
{
	Stage window;
	Scene verwaltungsModus, benutzerModus;
    TableView<Tabelle> neuTabelle, playlist1, playlist2, playlist3;
    TextField pathEingabe;
    Text MD, PL;
    boolean pl1,pl2,pl3;
    ComboBox<String> playlistAusw;
    
    
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
        window.setTitle("Musikverwaltung");
        window.setMinHeight(600);
        window.setMinWidth(1024);
        
        TableSetting TS = new TableSetting();

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
        
        AlertBox penis = new AlertBox();

        Button deleteButton = new Button("Löschen");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button bModButton = new Button("Zum Benutzermodus");    //Um in Benutzer zu gelangen = bMod
        bModButton.setOnAction(e -> window.setScene(benutzerModus));
        Button inDiePlaylist = new Button("In die Playlist");
        inDiePlaylist.setOnAction(e -> inDiePlaylistClicked());
        Button ausDerPlaylist = new Button("Aus der Playlist");
        ausDerPlaylist.setOnAction(e -> ausDerPlaylistClicked());
        Button genrePlay = new Button("Aus der Playlist");
        genrePlay.setOnAction(e -> penis.genreSuche("JPenis", "Gooodie"));
        Button interPlay = new Button("Aus der Playlist");
        interPlay.setOnAction(e -> penis.display("penis", "penis"));

        //Layout für die Eingabe
        HBox eingLayout = new HBox();
        eingLayout.setPadding(new Insets(10,10,10,10));
        eingLayout.setSpacing(10);
        eingLayout.getChildren().addAll(addButton, deleteButton);

        //Tabelle erstellen
        neuTabelle = new TableView<>();
        TS.setting(neuTabelle);
        neuTabelle.setItems(getTabelle());
        
        playlist1 = new TableView<>();
        TS.setting(playlist1);
        playlist1.setItems(getTabelle());
        playlist2 = new TableView<>();
        TS.setting(playlist2);
        playlist3 = new TableView<>();
        TS.setting(playlist3);
        //TEST
        ObservableList<Tabelle> tabellen1 = FXCollections.observableArrayList();
        tabellen1.add(new Tabelle("Justin Bieber", "Baby", "Playlist1"));
        ObservableList<Tabelle> tabellen2 = FXCollections.observableArrayList();
        tabellen2.add(new Tabelle("Justin Bieber", "Baby", "Playlist2"));
        ObservableList<Tabelle> tabellen3 = FXCollections.observableArrayList();
        tabellen3.add(new Tabelle("Justin Bieber", "Baby", "Playlist3"));
        playlist1.setItems(tabellen1);
        playlist2.setItems(tabellen2);
        playlist3.setItems(tabellen3);
        //TEST ENDE
        
        //Musikdatenbank TEXT
        MD = new Text();
        MD.setText("Musikdatenbank");
        MD.setFont(new Font(20));
        
        //Combo Box für Auswahl der Playlisten
        
        ComboBox<String> playlistAusw = new ComboBox<String>();
        playlistAusw.getItems().addAll(
            "Playlist 1",
            "Playlist 2",
            "Playlist 3" 
        );
        playlistAusw.setPromptText("Wählen Sie eine Playlist aus.");

        //Layout für die Tabelle
        VBox tabLayout = new VBox();
        tabLayout.getChildren().addAll(MD, neuTabelle);
        tabLayout.setPadding(new Insets(10,10,10,10));
        
        //Layout für die Playlist
        VBox plLayout = new VBox();
        plLayout.getChildren().addAll(playlistAusw);
        plLayout.setPadding(new Insets(10,10,10,10));
        
        playlistAusw.setOnAction(e -> {
        	if (playlistAusw.getValue().toString() == "Playlist 1")
        	{
        		if(plLayout.getChildren().contains(playlist2))
        		{
        			plLayout.getChildren().remove(playlist2);
        			plLayout.getChildren().addAll(playlist1);
        		}
        		else if(plLayout.getChildren().contains(playlist3))
        		{
        			plLayout.getChildren().remove(playlist3);
            		plLayout.getChildren().addAll(playlist1);
        		}
        		else if (!plLayout.getChildren().contains(playlist1))
        		{
        			plLayout.getChildren().addAll(playlist1);
        		}
        		pl2=false;
    			pl3=false;
    			pl1=true;
        	}
        	else if (playlistAusw.getValue().toString() == "Playlist 2")
        	{
        		if(plLayout.getChildren().contains(playlist1))
        		{
        			plLayout.getChildren().remove(playlist1);
        			plLayout.getChildren().addAll(playlist2);
        		}
        		else if(plLayout.getChildren().contains(playlist3))
        		{
        			plLayout.getChildren().remove(playlist3);
            		plLayout.getChildren().addAll(playlist2);
        		}
        		else if (!plLayout.getChildren().contains(playlist2))
        		{
        			plLayout.getChildren().addAll(playlist2);
        		}
        		pl2=true;
    			pl3=false;
    			pl1=false;
        	}
        	else if (playlistAusw.getValue().toString() == "Playlist 3")
        	{
        		if(plLayout.getChildren().contains(playlist2))
        		{
        			plLayout.getChildren().remove(playlist2);
        			plLayout.getChildren().addAll(playlist3);
        		}
        		else if(plLayout.getChildren().contains(playlist1))
        		{
        			plLayout.getChildren().remove(playlist1);
            		plLayout.getChildren().addAll(playlist3);
        		}
        		else if (!plLayout.getChildren().contains(playlist3))
        		{
        			plLayout.getChildren().addAll(playlist3);
        		}
        		pl2=false;
    			pl3=true;
    			pl1=false;
        	}
        });
        
        //Layout für die Mod Switches
        Label labelVerwaltungsmodus = new Label("Verwaltungsmodus");
        labelVerwaltungsmodus.setFont(new Font(20));
        VBox modLayout = new VBox(10);
        modLayout.getChildren().addAll(labelVerwaltungsmodus,bModButton);
        modLayout.setPadding(new Insets(5, 5, 5, 5));
        
        VBox playlistSwitcher = new VBox();
        playlistSwitcher.getChildren().addAll(ausDerPlaylist, inDiePlaylist);
        playlistSwitcher.setAlignment(Pos.CENTER);
        playlistSwitcher.setPadding(new Insets(10, 10, 10, 10));
        playlistSwitcher.setSpacing(10);
        
        VBox playlistMaker = new VBox();
        playlistMaker.getChildren().addAll(genrePlay, interPlay);
        playlistMaker.setAlignment(Pos.CENTER);
        playlistMaker.setPadding(new Insets(10, 10, 10, 10));
        playlistMaker.setSpacing(10);
        playlistMaker.setStyle("-fx-padding: 5;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 2;" + 
                "-fx-border-color: blue;");
        
        VBox playlistLayout = new VBox();
        playlistLayout.getChildren().addAll(playlistSwitcher, playlistMaker);
        
        
        //Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(modLayout);
        mainLayout.setRight(plLayout);
        mainLayout.setBottom(eingLayout);
        mainLayout.setLeft(tabLayout);
        mainLayout.setCenter(playlistLayout);

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
        labelBenutzermodus.setFont(new Font(20));
        button2.setOnAction(e -> window.setScene(verwaltungsModus));
        //Liste der Playlists
        Label playlistAuswahlLabel = new Label("Playlist auswählen:");
        playlistAuswahlLabel.setFont(new Font(20));
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

        modLayout2.getChildren().addAll(labelBenutzermodus,button2,
                playlistAuswahlLabel,playListView,playlistAuswahl);

        //Playaerelemente in horizontaler Anordnung
        HBox playerSteuerung = new HBox(40);
        playerSteuerung.setPadding(new Insets(5, 5, 5, 5));
        playerSteuerung.setAlignment(Pos.CENTER);
        playerSteuerung.alignmentProperty().isBound();
        //Player buttons
        Button skipBack = new Button("Zurück");
        Button stop = new Button("Stop");
        Button play = new Button("Abspielen");
        Button skipForward = new Button("Nächster");
        playerSteuerung.getChildren().addAll(skipBack,stop,play,skipForward);

        //Mittige Tabelle für Abspielinformationen
        VBox abspielInformationen = new VBox(2);
        //Label Tabelle
        Label aktPlaylistLabel = new Label("Aktuelle Playlist");
        aktPlaylistLabel.setFont(new Font(20));
        aktPlaylistLabel.setMinSize(20,20);
        //Tabelle zentriert
        TableView<Tabelle> aktuellePlaylistTabelle = new TableView<>();
        TS.setting(aktuellePlaylistTabelle);
        aktuellePlaylistTabelle.setItems(getTabelle()); //TODO noch setItems vervollständigen
        abspielInformationen.getChildren().addAll(aktPlaylistLabel,aktuellePlaylistTabelle);
        /*
        * MediaPlayer
        *
        * */
        String path = "C:\\Users\\tarnd\\git\\Musik\\NikFos.mp3"/*"/Users/mariangeissler/Desktop/ets.mp3"*/; //TODO mit Richy's Funktion ersetzen
        Media media = new Media(new File(path).toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(false);
        MediaView mediaView = new MediaView(mediaPlayer);

        //Play mit Funktion versehen
        play.setOnAction(event -> {
            if ("Pause".equals(play.getText()))
            {
                mediaView.getMediaPlayer().pause();
                play.setText("Abspielen");
            } else {
                mediaView.getMediaPlayer().play();
                mediaPlayer.play();
                play.setText("Pause");
            }
            });
        //Stop mit Funktion versehen
        stop.setOnAction(event -> {
            mediaView.getMediaPlayer().stop();
            play.setText("Abspielen");
        });

        //Player im Layout setzen
        playerLayout.setCenter(mediaView);
        /*
         * MediaPlayer Ende
         *
         * */
        //Layout setzen
        playerLayout.setCenter(abspielInformationen);
        playerLayout.setLeft(modLayout2);
        playerLayout.setBottom(playerSteuerung);
        playerLayout.setAlignment(playerSteuerung, Pos.BOTTOM_CENTER);// Playelemente Position



        benutzerModus = new Scene(playerLayout, 1024, 600);
        /*
        *
        * Benutzermodus Ende
        */

    }

    private void inDiePlaylistClicked()
	{
    	ObservableList<Tabelle> Tabellenelected, allTabellen, Tabellenelected2, allTabellen2;
        allTabellen = neuTabelle.getItems();
        Tabellenelected = neuTabelle.getSelectionModel().getSelectedItems();
        if(pl1)
        {
        	allTabellen2 = playlist1.getItems();
            Tabellenelected2 = playlist1.getSelectionModel().getSelectedItems();
            Tabellenelected.forEach(allTabellen2::add);
        }
        else if (pl2)
        {
        	allTabellen2 = playlist2.getItems();
            Tabellenelected2 = playlist2.getSelectionModel().getSelectedItems();
            Tabellenelected.forEach(allTabellen2::add);
        }
        else if (pl3)
        {
        	allTabellen2 = playlist3.getItems();
            Tabellenelected2 = playlist3.getSelectionModel().getSelectedItems();
            Tabellenelected.forEach(allTabellen2::add);
        }
        
        
        
        
        
        
        
        
	}

	private void ausDerPlaylistClicked()
	{
		// TODO Abfangen des Falles, das die Playlist leer ist.
		if(pl1)
		{
			ObservableList<Tabelle> Tabellenelected, allTabellen;
	        allTabellen = playlist1.getItems();
	        Tabellenelected = playlist1.getSelectionModel().getSelectedItems();

	        Tabellenelected.forEach(allTabellen::remove);
		}
		else if(pl2)
		{
			ObservableList<Tabelle> Tabellenelected, allTabellen;
	        allTabellen = playlist2.getItems();
	        Tabellenelected = playlist2.getSelectionModel().getSelectedItems();

	        Tabellenelected.forEach(allTabellen::remove);
		}
		else if(pl3)
		{
			ObservableList<Tabelle> Tabellenelected, allTabellen;
	        allTabellen = playlist3.getItems();
	        Tabellenelected = playlist3.getSelectionModel().getSelectedItems();

	        Tabellenelected.forEach(allTabellen::remove);
		}
		
	}

	//Hinzufügen Button
	/*
	 * Man kann hier mit der If Abfrage noch einfügen, dass er das leere Feld rot highlighted !
	 */
    public void addButtonClicked() throws IOException, TagException
    {
    	FileChooser fc = new FileChooser();
    	configureFileChooser(fc);
    	File selectedFile = fc.showOpenDialog(null);
    	
    	if(selectedFile != null)
    	{
    		TitelEinbinden eingabe = new TitelEinbinden();
    		neuTabelle.getItems().add(eingabe.einbinden(selectedFile.getAbsolutePath()));
    	}
    	else
    	{
    		/* Some weird ass shhit like printing out "you'r maaaaaa!" */
    	}
    }
    
    //File Chooser bearbeiten(nur mp3 Dateien)
    private static void configureFileChooser(
            final FileChooser fileChooser) 
    		{      
                fileChooser.setTitle("Wähle eine Musikdatei aus");
                fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
                );                 
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MP3", "*.mp3")
                );
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
