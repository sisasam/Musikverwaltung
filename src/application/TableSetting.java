package application;

import javafx.scene.control.TableColumn;

public class TableSetting
{
	public void setting(TableColumn<Tabelle, String> titelSpalte)
	{
		 titelSpalte.setPrefWidth(100);	//bevorzugte Spaltenbreite
	     titelSpalte.setMinWidth(50);	//minimale Spaltenbreite
	     titelSpalte.setMaxWidth(150);	//maximale Spaltenbreite
	}

}
