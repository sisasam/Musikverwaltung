package application;

import java.io.IOException;

import org.farng.mp3.TagException;

import logic.Song;


public class TitelEinbinden 
{
	public TitelEinbinden()
	{
		
	}
	
	public Tabelle einbinden(String path) throws IOException, TagException 
	{
		Song song = new Song(path);
		Tabelle table = new Tabelle();
		String title = new String();
		
		title = song.getTitle();
		table.setTitel(title);
		String genre = song.getGenre();
		table.setGenre(genre);
		String inter = song.getInterpret();
		table.setInterpret(inter);
		table.setPath(path);
		
		return table;
	}
	
	public Tabelle einbinden( Song song /*String path*/) throws IOException, TagException 
	{
//		Song song = new Song(path);
		Tabelle table = new Tabelle();
		String title = new String();
		
		title = song.getTitle();
		table.setTitel(title);
		
		String genre = song.getGenre();
		table.setGenre(genre);
		
		String inter = song.getInterpret();
		table.setInterpret(inter);
		
		String path = song.getFilepath();
		table.setPath(path);
		
		return table;
	}
}
