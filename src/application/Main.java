package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application 
{
	Stage window;
    TableView<Tabelle> neuTabelle;
    TextField titleEingabe, interEingabe, genreEingabe;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
        window.setTitle("Musikverwaltung");
        window.setMinHeight(550);
        window.setMinWidth(900);
        
        //Nr Spalte
        TableColumn<Tabelle, String> nrSpalte = new TableColumn<>("Nr.");
        nrSpalte.setPrefWidth(70);	//bevorzugte Spaltenbreite
        nrSpalte.setMinWidth(50);	//minimale Spaltenbreite
        nrSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        nrSpalte.setCellValueFactory(new PropertyValueFactory<>("nr"));

        //Titel Spalte
        TableColumn<Tabelle, String> titelSpalte = new TableColumn<>("Titel");
        titelSpalte.setPrefWidth(70);	//bevorzugte Spaltenbreite
        titelSpalte.setMinWidth(50);	//minimale Spaltenbreite
        titelSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        titelSpalte.setCellValueFactory(new PropertyValueFactory<>("titel"));

        //Interpreten Spalte
        TableColumn<Tabelle, Double> interpretenSpalte = new TableColumn<>("Interpret");
        interpretenSpalte.setPrefWidth(70);	//bevorzugte Spaltenbreite
        interpretenSpalte.setMinWidth(50);	//minimale Spaltenbreite
        interpretenSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        interpretenSpalte.setCellValueFactory(new PropertyValueFactory<>("interpret"));

        //Genre Spalte
        TableColumn<Tabelle, String> genreSpalte = new TableColumn<>("Genre");
        genreSpalte.setPrefWidth(70);	//bevorzugte Spaltenbreite
        genreSpalte.setMinWidth(50);	//minimale Spaltenbreite
        genreSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        genreSpalte.setCellValueFactory(new PropertyValueFactory<>("genre"));

        //Titel Eingabe
        titleEingabe = new TextField();
        titleEingabe.setPromptText("Title");
        titleEingabe.setMinWidth(100);

        //Interpreten Eingabe
        interEingabe = new TextField();
        interEingabe.setPromptText("Interpret");

        //Genre Eingabe
        genreEingabe = new TextField();
        genreEingabe.setPromptText("Genre");

        //Button
        Button addButton = new Button("Hinzufügen");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Löschen");
        deleteButton.setOnAction(e -> deleteButtonClicked());
        Button vModButton = new Button("Verwaltungsmodus");
        vModButton.setOnAction(e -> vModButtonClicked());
        Button bModButton = new Button("Benutzermodus");
        bModButton.setOnAction(e -> bModButtonClicked());

        //Layout für die Eingabe
        HBox eingLayout = new HBox();
        eingLayout.setPadding(new Insets(10,10,10,10));
        eingLayout.setSpacing(10);
        eingLayout.getChildren().addAll(titleEingabe, interEingabe, genreEingabe, addButton, deleteButton);

        //Tabelle erstellen
        neuTabelle = new TableView<>();
        neuTabelle.setPrefWidth(210);
        neuTabelle.setItems(getTabelle());
        neuTabelle.getColumns().addAll(nrSpalte, titelSpalte, interpretenSpalte, genreSpalte);

        //Layout für die Tabelle
        VBox tabLayout = new VBox();
        tabLayout.getChildren().addAll(neuTabelle);
        
        //Layout für die Mod Switches
        HBox modLayout = new HBox();
        modLayout.getChildren().addAll(vModButton, bModButton);
        
        //Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(modLayout);
        mainLayout.setLeft(tabLayout);
        mainLayout.setBottom(eingLayout);

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();
    }

    //Hinzufügen Button
	/*
	 * Man kann hier mit der If Abfrage noch einfügen, dass er das leere Feld rot highlighted !
	 */
    public void addButtonClicked(){
    	if(!titleEingabe.getText().isEmpty() && !interEingabe.getText().isEmpty() && !genreEingabe.getText().isEmpty() )
        {
    		Tabelle tabelle = new Tabelle();
    		tabelle.setTitel(titleEingabe.getText());
    		tabelle.setInterpret(interEingabe.getText());
    		tabelle.setGenre(genreEingabe.getText());
    		neuTabelle.getItems().add(tabelle);
    		titleEingabe.clear();
    		interEingabe.clear();
    		genreEingabe.clear();
    	}
    }

    //Löschen Button
    public void deleteButtonClicked(){
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
    

    //Einfügen der Anfangswerte in die Tabelle
    public ObservableList<Tabelle> getTabelle(){
        ObservableList<Tabelle> Tabellen = FXCollections.observableArrayList();
        Tabellen.add(new Tabelle("Justin Bieber", "Baby", "Dreck"));
        Tabellen.add(new Tabelle("Justin Bieber", "Baby", "Müll"));
        Tabellen.add(new Tabelle("Justin Bieber", "Baby", "Scheiß"));
        Tabellen.add(new Tabelle("Justin Bieber", "Baby", "Ohrenkrebs"));
        Tabellen.add(new Tabelle("Justin Bieber", "Baby", "Lieber Eier in Piranhabecken hängen"));
        return Tabellen;
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
