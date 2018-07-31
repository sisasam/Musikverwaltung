package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Player
{
    protected String path;
    protected Media media;
    protected MediaPlayer mediaPlayer;
    protected MediaView mediaView;

    public Player(String path)
    {
        this.path = path;
        this.media = new Media(new File(path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaView = new MediaView(mediaPlayer);
    }

}
