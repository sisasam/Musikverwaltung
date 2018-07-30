package application;

import java.io.File;
import java.io.IOException;
import org.farng.mp3.TagException;

import interfaces.Song;

public class TitelEinbinden 
{
	public TitelEinbinden()
	{
		
	}
	
	public Tabelle einbinden(String path) throws IOException, TagException 
	{
		Tabelle table = new Tabelle();
		String title = new String();
		Song song = new Song(path);
		
		title = song.getTitle();
		table.setTitel(title);
		String genre = song.getGenre();
		table.setGenre(genre);
		String inter = song.getInterpret();
		table.setInterpret(inter);
		
		return table;
	}
}
