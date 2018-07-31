package application;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableSetting
{
	public void setting(TableView<Tabelle> playlist1)
	{
		TableColumn<Tabelle, String> titelSpalte = new TableColumn<>("Titel");
		titelSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
	    titelSpalte.setMinWidth(50);	//minimale Spaltenbreite
	    titelSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        titelSpalte.setCellValueFactory(new PropertyValueFactory<>("titel"));

        TableColumn<Tabelle, Double> interpretenSpalte = new TableColumn<>("Interpret");
        interpretenSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
        interpretenSpalte.setMinWidth(50);	//minimale Spaltenbreite
        interpretenSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        interpretenSpalte.setCellValueFactory(new PropertyValueFactory<>("interpret"));
        
        TableColumn<Tabelle, String> genreSpalte = new TableColumn<>("Genre");
        genreSpalte.setPrefWidth(170);	//bevorzugte Spaltenbreite
        genreSpalte.setMinWidth(50);	//minimale Spaltenbreite
        genreSpalte.setMaxWidth(150);	//maximale Spaltenbreite
        genreSpalte.setCellValueFactory(new PropertyValueFactory<>("genre"));
        
        playlist1.getColumns().addAll(titelSpalte, interpretenSpalte, genreSpalte);
	}

}
