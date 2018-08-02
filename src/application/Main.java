package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.farng.mp3.TagException;

import backendapi.AdminModeApi;
import backendapi.UserModeApi;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
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
import logic.Song;


public class Main extends Application 
{

	//GUI und Funktionalität
	Stage window;
	Scene verwaltungsModus, benutzerModus;
    TableView<Tabelle> neuTabelle, playlist1, playlist2, playlist3;
    Text MD;
    boolean pl1,pl2,pl3;
    int marianAusw;
    String pathForPlay;


	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//Setup der Funktionalität, Initialisierung

//		String current = new java.io.File( "." ).getCanonicalPath();
//        System.out.println("Current dir:"+current);
        UserModeApi userModeApi = new UserModeApi();
        //Erzeugen der txt dateien für Musikdatenbank
        AdminModeApi admin = new AdminModeApi();
		admin.addPlaceOfSongPersistence("./Musik/admin.txt");
		//Erzeugen der txt dateien für Playlistnamen
        userModeApi.addPlaceOfPlaylistTitlePersistence("./Musik/PlaylistTitlePersistence.txt");
        //Erzeugen der txt dateien für Playlistdateipfade
        userModeApi.addPlaceOfPlaylistPersistence("./Musik/PlaylistPersistence.txt");
		
		
		//Initialisierung der Musikdatenbank!
		if (fileEmpty())
		{
			addAllSongs();
		}
		
		
        
        
        

		window = primaryStage;
        window.setTitle("Musikverwaltung");
        window.setMinHeight(600);
        window.setMinWidth(1024);
        
        TableSetting TS = new TableSetting();
        Button playlistAuswahl;

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
        
        AlertBox auswahl = new AlertBox();
        
        //Für die Auswahl der Playlist nach Genre

        Button deleteButton = new Button("Löschen");
        deleteButton.setOnAction(e -> {
			try
			{
				deleteButtonClicked();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (TagException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        Button bModButton = new Button("Zum Benutzermodus");    //Um in Benutzer zu gelangen = bMod
        bModButton.setOnAction(e -> window.setScene(benutzerModus));
        Button inDiePlaylist = new Button("In die Playlist");
        inDiePlaylist.setOnAction(e -> inDiePlaylistClicked());
        Button ausDerPlaylist = new Button("Aus der Playlist");
        Button playlistEntfernen = new Button("Playlist entfernen");
        Button playlistHinzu = new Button("Playlist hinzufügen");
        playlistHinzu.setOnAction(event -> {
            TextBox hinzu = new TextBox();
            ArrayList<String> nameNeuPlaylist = new ArrayList<String>();
            nameNeuPlaylist.add(hinzu.display("Neue Playlist",
                    "Name der Playlist einfügen"));
            ArrayList<String> dateipfadPlaylist = new ArrayList<String>();
            dateipfadPlaylist.add("./Musik/"+nameNeuPlaylist+".txt");
            try
            {
                userModeApi.addPlaylists(nameNeuPlaylist,"./Musik/PlaylistTitlePersistence.txt",
                        "./Musik/PlaylistPersistence.txt",dateipfadPlaylist,true);
            }
            catch (IOException e)
            {
                e.printStackTrace(); //TODO
            }
        });
        ausDerPlaylist.setOnAction(e -> ausDerPlaylistClicked());
        Button genrePlay = new Button("Genre");
        genrePlay.setOnAction(e -> {
        	String auswahlGenre = auswahl.genreSuche("Genre Playlist");
        });
        Button interPlay = new Button("Interpreten");
        interPlay.setOnAction(e -> {
        	auswahl.interSuche("Interpreten Playlist");
        	});



        //Layout für die Eingabe
        HBox eingLayout = new HBox();
        eingLayout.setPadding(new Insets(10,10,10,10));
        eingLayout.setSpacing(10);
        eingLayout.getChildren().addAll(addButton, deleteButton);

        //Tabellen erstellen
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
        
        
        //Auswahl aus der ComboBox, welche angezeigt werden
        //TODO Marian übernehmen der angezeigten Playlist.
        playlistAusw.setOnAction(e -> {
        	int hV = 0;
        	if (playlistAusw.getValue().toString() == "Playlist 1")
        	{
        		hV = 1;
        	}
        	if (playlistAusw.getValue().toString() == "Playlist 2")
        	{
        		hV = 2;
        	}
        	if (playlistAusw.getValue().toString() == "Playlist 3")
        	{
        		hV = 3;
        	}
        	auswahl(hV, plLayout);
        	
//        	if (playlistAusw.getValue().toString() == "Playlist 1")
//        	{
//        		if(plLayout.getChildren().contains(playlist2))
//        		{
//        			plLayout.getChildren().remove(playlist2);
//        			plLayout.getChildren().addAll(playlist1);
//        		}
//        		else if(plLayout.getChildren().contains(playlist3))
//        		{
//        			plLayout.getChildren().remove(playlist3);
//            		plLayout.getChildren().addAll(playlist1);
//        		}
//        		else if (!plLayout.getChildren().contains(playlist1))
//        		{
//        			plLayout.getChildren().addAll(playlist1);
//        		}
//        		pl2=false;
//    			pl3=false;
//    			pl1=true;
//        	}
//        	else if (playlistAusw.getValue().toString() == "Playlist 2")
//        	{
//        		if(plLayout.getChildren().contains(playlist1))
//        		{
//        			plLayout.getChildren().remove(playlist1);
//        			plLayout.getChildren().addAll(playlist2);
//        		}
//        		else if(plLayout.getChildren().contains(playlist3))
//        		{
//        			plLayout.getChildren().remove(playlist3);
//            		plLayout.getChildren().addAll(playlist2);
//        		}
//        		else if (!plLayout.getChildren().contains(playlist2))
//        		{
//        			plLayout.getChildren().addAll(playlist2);
//        		}
//        		pl2=true;
//    			pl3=false;
//    			pl1=false;
//        	}
//        	else if (playlistAusw.getValue().toString() == "Playlist 3")
//        	{
//        		if(plLayout.getChildren().contains(playlist2))
//        		{
//        			plLayout.getChildren().remove(playlist2);
//        			plLayout.getChildren().addAll(playlist3);
//        		}
//        		else if(plLayout.getChildren().contains(playlist1))
//        		{
//        			plLayout.getChildren().remove(playlist1);
//            		plLayout.getChildren().addAll(playlist3);
//        		}
//        		else if (!plLayout.getChildren().contains(playlist3))
//        		{
//        			plLayout.getChildren().addAll(playlist3);
//        		}
//        		pl2=false;
//    			pl3=true;
//    			pl1=false;
//        	}
        });
        
        //Layout für die Mod Switches
        Label labelVerwaltungsmodus = new Label("Verwaltungsmodus");
        labelVerwaltungsmodus.setFont(new Font(20));
        VBox modLayout = new VBox(10);
        modLayout.getChildren().addAll(labelVerwaltungsmodus,bModButton);
        modLayout.setPadding(new Insets(5, 5, 5, 5));
        
        VBox playlistSwitcher = new VBox();
        playlistSwitcher.getChildren().addAll(ausDerPlaylist, inDiePlaylist, playlistHinzu,playlistEntfernen);
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
//        //Erzeugen der txt dateien für Playlistnamen
//        userModeApi.addPlaceOfPlaylistTitlePersistence("./Musik/PlaylistTitlePersistence.txt");
//        //Erzeugen der txt dateien für Playlistdateipfade
//        userModeApi.addPlaceOfPlaylistPersistence("./Musik/PlaylistPersistence.txt");

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

        ListView<String> playListView = new ListView<String>();
//        for(String current: userModeApi.showAllExistingPlaylists("./Musik/PlaylistTitlePersistence.txt","./Musik/PlaylistPersistence.txt"))
//        {
//        playListView.getItems().addAll(current);
//        }
//        playListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
/////////////////////////////////////////////////////////////////////////////////// VON TORE ANFANG
        playListView.getItems().addAll(
        		"Playlist 1",
        		"Playlist 2",
        		"Playlist 3"
        		);
        playListView.setOnMouseClicked(e ->{
        	 if (playListView.getSelectionModel().getSelectedItem() == "Playlist 1")
             {
             	marianAusw = 1;
             }
             else if (playListView.getSelectionModel().getSelectedItem() == "Playlist 2")
             {
             	marianAusw = 2;
             }
             else if (playListView.getSelectionModel().getSelectedItem() == "Playlist 3")
             {
             	marianAusw = 3;
             }
        });
       
        //Mittige Tabelle für Abspielinformationen
        VBox abspielInformationen = new VBox();
        //Label Tabelle
        Label aktPlaylistLabel = new Label("Aktuelle Playlist");
        aktPlaylistLabel.setFont(new Font(20));
        aktPlaylistLabel.setMinSize(20,20);
        //Tabelle zentriert
//        TableView<Tabelle> aktuellePlaylistTabelle = new TableView<>();
        abspielInformationen.setPadding(new Insets(10,10,10,10));
//        aktuellePlaylistTabelle.setItems(getTabelle()); //TODO noch setItems vervollständigen //Von Tore rausgenommen
        abspielInformationen.getChildren().addAll(aktPlaylistLabel/*TEST,aktuellePlaylistTabelle TEST*/);
/////////////////////////////////////////////////////////////////////////////////// VON TORE ENDE
        //Button für Playlist
//        TableView<Tabelle> aktuellePlaylistTabelle = new TableView<>(); //von Tore rausgenommen dafür die Zeile 379
        playlistAuswahl = new Button("Playlist verwenden");
        playlistAuswahl.setOnAction(event -> {
/////////////////////////////////////////////////////////////////////////////////// VON TORE ANFANG //TODO abspielinformationen nach vorne holen
        	auswahl(marianAusw, abspielInformationen);
        	System.out.println(marianAusw);
/////////////////////////////////////////////////////////////////////////////////// VON TORE ENDE
//            String penis = playListView.getSelectionModel().getSelectedItem();
//            try
//            {
//
//                TitelEinbinden fuerAktList = new TitelEinbinden();
//                for (Song current:  admin.generateSongList("./Musik/["+penis+"].txt"))
//                {
//                    try
//                    {
//                        aktuellePlaylistTabelle.getItems().add(fuerAktList.einbinden(current));
//                    } catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    } catch (TagException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (FileNotFoundException e)
//            {
//                e.printStackTrace();
//            }
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

//        //Mittige Tabelle für Abspielinformationen
//        VBox abspielInformationen = new VBox(2);
//        //Label Tabelle
//        Label aktPlaylistLabel = new Label("Aktuelle Playlist");
//        aktPlaylistLabel.setFont(new Font(20));
//        aktPlaylistLabel.setMinSize(20,20);
//        //Tabelle zentriert
//       // TableView<Tabelle> aktuellePlaylistTabelle = new TableView<>();
//        TS.setting(aktuellePlaylistTabelle);
//        abspielInformationen.setPadding(new Insets(10,10,10,10));
//        aktuellePlaylistTabelle.setItems(getTabelle()); //TODO noch setItems vervollständigen
//        abspielInformationen.getChildren().addAll(aktPlaylistLabel,aktuellePlaylistTabelle);
        /*
        * MediaPlayer
        *
        *
        * */
        pathForPlay = "";
        if(pl1)
        {
        	System.out.println(playlist1.getSelectionModel().getSelectedItem().getPath());
        	playlist1.setOnMouseClicked(e -> {
        		pathForPlay = playlist1.getSelectionModel().getSelectedItem().getPath();
        		System.out.println(playlist1.getSelectionModel().getSelectedItem().getPath()); //TODO Hier weiter versuchen den Path zu bekommen
        	});
        }
        else if (pl2)
        {
        	playlist1.setOnMouseClicked(e -> {
        		pathForPlay = playlist2.getSelectionModel().getSelectedItem().getPath();
        	});
        }
        else if (pl3)
        {
        	pathForPlay = playlist2.getSelectionModel().getSelectedItem().getPath();
        }
        
        System.out.println(pathForPlay);
        if (pathForPlay != "")
        {
        Media media = new Media(new File(pathForPlay).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(false);
        MediaView mediaView = new MediaView(mediaPlayer);
        //Player testSong1 = new Player("/Users/mariangeissler/Desktop/ets.mp3");
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
        }
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
	
	//Checks if first line is empty
	private boolean fileEmpty() throws IOException, TagException 
	{
		FileReader fr = new FileReader("./Musik/admin.txt");
	    BufferedReader br = new BufferedReader(fr);

	    boolean leer = false;

	    if(br.readLine() == null)
	    {
	      leer = true;
	    }

	    br.close();
		return leer;
	}

	//Bindet alle Songs ein.
    private void addAllSongs() throws IOException, TagException 
	{
    	AdminModeApi admin = new AdminModeApi();
    	List<String> songs = new ArrayList<String>();
    	
    	File dir = new File("./Musik/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
          for (File child : directoryListing) 
          {
        	  if ( child.getCanonicalPath().toLowerCase().endsWith( ".mp3")) 
        	  {
        		  songs.add(child.getCanonicalPath());
        	  }
          }
        } 
        else 
        {
          // Handle the case where dir is not really a directory.
          // Checking dir.isDirectory() above would not be sufficient
          // to avoid race conditions with another process that deletes
          // directories.
        }
        admin.addSongs(songs, "./Musik/admin.txt", true);
		
	}
    
    private void auswahl(int gewählt, VBox layout)
    {
    	if (gewählt == 1)
    	{
    		if(layout.getChildren().contains(playlist2))
    		{
    			layout.getChildren().remove(playlist2);
    			layout.getChildren().addAll(playlist1);
    		}
    		else if(layout.getChildren().contains(playlist3))
    		{
    			layout.getChildren().remove(playlist3);
    			layout.getChildren().addAll(playlist1);
    		}
    		else if (!layout.getChildren().contains(playlist1))
    		{
    			layout.getChildren().addAll(playlist1);
    		}
    		pl2=false;
			pl3=false;
			pl1=true;
    	}
    	else if (gewählt == 2)
    	{
    		if(layout.getChildren().contains(playlist1))
    		{
    			layout.getChildren().remove(playlist1);
    			layout.getChildren().addAll(playlist2);
    		}
    		else if(layout.getChildren().contains(playlist3))
    		{
    			layout.getChildren().remove(playlist3);
    			layout.getChildren().addAll(playlist2);
    		}
    		else if (!layout.getChildren().contains(playlist2))
    		{
    			layout.getChildren().addAll(playlist2);
    		}
    		pl2=true;
			pl3=false;
			pl1=false;
    	}
    	else if (gewählt == 3)
    	{
    		if(layout.getChildren().contains(playlist2))
    		{
    			layout.getChildren().remove(playlist2);
    			layout.getChildren().addAll(playlist3);
    		}
    		else if(layout.getChildren().contains(playlist1))
    		{
    			layout.getChildren().remove(playlist1);
    			layout.getChildren().addAll(playlist3);
    		}
    		else if (!layout.getChildren().contains(playlist3))
    		{
    			layout.getChildren().addAll(playlist3);
    		}
    		pl2=false;
			pl3=true;
			pl1=false;
    	}
    }

	private void inDiePlaylistClicked() //TODO Richy's Funktionen (Speicheern der paths in txt)
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
    	AdminModeApi admin = new AdminModeApi();
    	List<String> filePath = new ArrayList<String>();
    	FileChooser fc = new FileChooser();
    	configureFileChooser(fc);
    	File selectedFile = fc.showOpenDialog(null);
    	
    	if(selectedFile != null)
    	{
    		TitelEinbinden eingabe = new TitelEinbinden();
    		neuTabelle.getItems().add(eingabe.einbinden(selectedFile.getAbsolutePath()));
    		filePath.add(selectedFile.getAbsolutePath());
    		admin.addSongs(filePath, "./Musik/admin.txt", true);
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
    public void deleteButtonClicked() throws IOException ,TagException
    {
        ObservableList<Tabelle> Tabellenelected, allTabellen;
        allTabellen = neuTabelle.getItems();
        Tabellenelected = neuTabelle.getSelectionModel().getSelectedItems();

        Tabellenelected.forEach(allTabellen::remove);
        AdminModeApi admin = new AdminModeApi();
        List<String> deleteSong = new ArrayList<String>();
        deleteSong.add(neuTabelle.getSelectionModel().getSelectedItem().getPath());
        admin.deleteSongs("./Musik/admin.txt", deleteSong);
    }
    
    public void playlistAuswahlClicked()
    {
    }
    
    //Einfügen der Anfangswerte in die Tabelle
    public ObservableList<Tabelle> getTabelle() throws IOException, TagException 
    {
    	ObservableList<Tabelle> tabellen = FXCollections.observableArrayList();
    	
		AdminModeApi admin = new AdminModeApi();
//		admin.addPlaceOfSongPersistence("./Musik/admin.txt");
		
    	 List<Song> ListAllSongs = new ArrayList<Song>();
 		ListAllSongs = admin.generateSongList("./Musik/admin.txt");
 		TitelEinbinden eingabe = new TitelEinbinden();
  
 		    for (Song current: ListAllSongs) 
 		    { 
 	    		tabellen.add(eingabe.einbinden(current));
 		    }
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
    
    public String getFilePath(TableView<Tabelle> tabelleView)
    {
    	Tabelle tabelle = tabelleView.getSelectionModel().getSelectedItem();
    	String path = tabelle.getPath();
    	
    	return path;
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
