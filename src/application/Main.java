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
      //Nr Spalte2
        TableColumn<Tabelle, String> nrSpalte2 = new TableColumn<>("Nr.");
        nrSpalte2.setPrefWidth(20);	//bevorzugte Spaltenbreite
        nrSpalte2.setMinWidth(20);	//minimale Spaltenbreite
        nrSpalte2.setMaxWidth(30);	//maximale Spaltenbreite
        nrSpalte2.setCellValueFactory(new PropertyValueFactory<>("nr"));

        //Titel Spalte
        TableColumn<Tabelle, String> titelSpalte = new TableColumn<>("Titel");
        titelSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
        titelSpalte.setMinWidth(50);	//minimale Spaltenbreite
        titelSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        titelSpalte.setCellValueFactory(new PropertyValueFactory<>("titel"));
      //Titel Spalte
        TableColumn<Tabelle, String> titelSpalte2 = new TableColumn<>("Titel");
        titelSpalte2.setPrefWidth(100);	//bevorzugte Spaltenbreite
        titelSpalte2.setMinWidth(50);	//minimale Spaltenbreite
        titelSpalte2.setMaxWidth(150);	//maximale Spaltenbreite
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
				//Die machen nen Scheiß!!
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
        neuTabelle.setItems(getTabelle());
        neuTabelle.getColumns().addAll(nrSpalte, titelSpalte, interpretenSpalte, genreSpalte);
        
        playlist1 = new TableView<>();
       // playlist1.setItems(getTabelle());
        playlist1.getColumns().addAll(nrSpalte2, titelSpalte2, interpretenSpalte2, genreSpalte2);
        
        
        //Layout für die Mod Switches
        HBox modLayout = new HBox();
        modLayout.getChildren().addAll(bModButton);
        
        //Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(modLayout);
        mainLayout.setLeft(neuTabelle);
        mainLayout.setBottom(eingLayout);
        mainLayout.setRight(playlist1);

        verwaltungsModus = new Scene(mainLayout,1024,600);

        //Starten im Verwaltungsmodus
        window.setScene(verwaltungsModus);
        window.show();

        /* Benutzermodus */
        BorderPane playerLayout = new BorderPane();

        VBox layout2 = new VBox(10);
        //Playaerelemente
        HBox playerSteuerung = new HBox(40);
        Button skipBack = new Button("Zurück");
        Button play = new Button("Abspielen");
        Button skipForward = new Button("Nächster");
        playerSteuerung.getChildren().addAll(skipBack,play,skipForward);

        playerLayout.setBottom(playerSteuerung);

        //Linke Seite Benutzermodus
        Label labelBenutzermodus = new Label("Benutzermodus");
        Button button2 = new Button("Zum Verwaltungsmodus");
        button2.setOnAction(e -> window.setScene(verwaltungsModus));
        layout2.getChildren().addAll(labelBenutzermodus,button2);

        playerLayout.setLeft(layout2);

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
    		tabelle.setTitel(eingabe.einbinden(path).getTitel());
    		tabelle.setGenre(eingabe.einbinden(path).getGenre());
    		tabelle.setInterpret(eingabe.einbinden(path).getInterpret());
    		playlist1.getItems().add(tabelle);
    		pathEingabe.clear();
//    		interEingabe.clear();
//    		genreEingabe.clear();
    	}
    }

    //Löschen Button
    public void deleteButtonClicked()
    {
        ObservableList<Tabelle> Tabellenelected, allTabellen;
        allTabellen = playlist1.getItems();
        Tabellenelected = playlist1.getSelectionModel().getSelectedItems();

        Tabellenelected.forEach(allTabellen::remove);
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
//        tabellen.add(new Tabelle(TitelEinbinden.einbinden()));
        return tabellen;
    }
    
    public ObservableList<Tabelle> getPlaylist(String path) throws IOException, TagException
    {
    	TitelEinbinden ein = new TitelEinbinden();
    	ObservableList<Tabelle> playlist = FXCollections.observableArrayList();
    	playlist.add(ein.einbinden(path));
    	return playlist;
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
