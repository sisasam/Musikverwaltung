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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application 
{
	Stage window;
    TableView<Tabelle> table;
    TextField nameInput, priceInput, quantityInput;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
        window.setTitle("thenewboston - JavaFX");

        //Name column
        TableColumn<Tabelle, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price column
        TableColumn<Tabelle, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity column
        TableColumn<Tabelle, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //Name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        //Price input
        priceInput = new TextField();
        priceInput.setPromptText("Price");

        //Quantity input
        quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");

        //Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked()); //setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, quantityInput, addButton, deleteButton);

        table = new TableView<>();
        table.setItems(getTabelle());
        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    //Add button clicked
    public void addButtonClicked(){
        Tabelle Tabelle = new Tabelle();
        Tabelle.setName(nameInput.getText());
        Tabelle.setPrice(Double.parseDouble(priceInput.getText()));
        Tabelle.setQuantity(Integer.parseInt(quantityInput.getText()));
        table.getItems().add(Tabelle);
        nameInput.clear();
        priceInput.clear();
        quantityInput.clear();
    }

    //Delete button clicked
    public void deleteButtonClicked(){
        ObservableList<Tabelle> Tabellenelected, allTabellen;
        allTabellen = table.getItems();
        Tabellenelected = table.getSelectionModel().getSelectedItems();

        Tabellenelected.forEach(allTabellen::remove);
    }

    //Einfügen der Anfangswerte in die Tabelle
    public ObservableList<Tabelle> getTabelle(){
        ObservableList<Tabelle> Tabellen = FXCollections.observableArrayList();
        Tabellen.add(new Tabelle("Laptop", 859.00, 20));
        Tabellen.add(new Tabelle("Bouncy Ball", 2.49, 198));
        Tabellen.add(new Tabelle("Toilet", 99.00, 74));
        Tabellen.add(new Tabelle("The Notebook DVD", 19.99, 12));
        Tabellen.add(new Tabelle("Corn", 1.49, 856));
        return Tabellen;
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
