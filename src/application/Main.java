package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.farng.mp3.TagException;
import javafx.scene.text.*;


import java.beans.MethodDescriptor;
import java.io.File;
import java.io.IOException;


public class Main extends Application 
{
	Stage window;
	Scene verwaltungsModus, benutzerModus;
    TableView<Tabelle> neuTabelle, playlist1, playlist2, playlist3;
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
        
        //Musikdatenbank TEXT
        MD = new Text();
        MD.setText("Musikdatenbank");
        MD.setFont(new Font(20));
        
        //Combo Box für Auswahl der Playlisten
        
        final ComboBox playlistAusw = new ComboBox();
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
        	switch(playlistAusw.getValue().toString())
        	{
        	case "Playlist 1":
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
        	}
        	case "Playlist 2":
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
        	}
        	case "Playlist 3":
        	{
        		if(plLayout.getChildren().contains(playlist2))
        		{
        			plLayout.getChildren().remove(playlist2);
        			plLayout.getChildren().addAll(playlist3);
        		}
        		else if(plLayout.getChildren().contains(playlist2))
        		{
        			plLayout.getChildren().remove(playlist2);
            		plLayout.getChildren().addAll(playlist3);
        		}
        		else if (!plLayout.getChildren().contains(playlist3))
        		{
        			plLayout.getChildren().addAll(playlist3);
        		}
        	}
        }
        });
        
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
//    	if(!pathEingabe.getText().isEmpty() )
//        {
//    		TitelEinbinden eingabe = new TitelEinbinden();
//    		Tabelle tabelle = new Tabelle();
//    		String path = pathEingabe.getText();
//            neuTabelle.getItems().add(eingabe.einbinden(path));
//    		pathEingabe.clear();
//    	}
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
            final FileChooser fileChooser) {      
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
