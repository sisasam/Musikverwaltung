package application;

import java.io.File;
import java.io.IOException;
import org.farng.mp3.TagException;

import interfaces.Song;

public class TitelEinbinden 
{
	public static void main(String[] args) throws IOException, TagException 
	{
		String title = new String();
		Song song = new Song("C:"+File.separator+"Users"+File.separator+"tarnd"+File.separator+"git"+File.separator+"Musik"+File.separator+"NikFos.mp3");
		title = song.getTitle();
		System.out.println(title);
		String genre = song.getGenre();
		System.out.println(genre);
	}
}
