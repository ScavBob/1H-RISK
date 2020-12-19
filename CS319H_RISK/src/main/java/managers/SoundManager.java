package managers;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

import game.Game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {

    private String musicFilePath;
    private Media musicFile;
    private MediaPlayer background_theme;

    private String alternativePath;
    private Media alternative_music;
    private MediaPlayer alternative_background_theme;

    private String congratsPath;
    private Media congrats;
    private MediaPlayer congratsSound;

    private String clickPath;
    private Media click;
    private MediaPlayer clickSound;

    private ArrayList<Media> backgroundMusic = new ArrayList<Media>();

    public SoundManager()
    {
        musicFilePath = getClass().getResource("/musics/medal-of-honor-european-assault-soundtrack-main-theme-hq.mp3").toExternalForm();
        System.out.println(musicFilePath);
        musicFile = new Media(musicFilePath);
        background_theme = new MediaPlayer(musicFile);

        alternativePath = getClass().getResource("/musics/battlefield-1942-theme-song.mp3").toExternalForm();
        alternative_music = new Media(alternativePath);
        alternative_background_theme = new MediaPlayer(alternative_music);

        congratsPath = getClass().getResource("/musics/you-win-street-fighter-sound-effect.mp3").toExternalForm();
        congrats = new Media(congratsPath);
        congratsSound = new MediaPlayer(congrats);

        clickPath = getClass().getResource("/musics/mouse-click-sound-effect-hd.mp3").toExternalForm();
        click = new Media(clickPath);
        clickSound = new MediaPlayer(click);

        backgroundMusic.add(musicFile);
        backgroundMusic.add(alternative_music);

        setVolume(10);
    }

    public void mute() {
        background_theme.setVolume(0);
        congratsSound.setVolume(0);
        clickSound.setVolume(0);
    }

    public void setVolume(int x )
    {
        // x : 0 - 10
        System.out.println("x:  " + x);
        background_theme.setVolume((x + 0.0) / 30 );
    }

    public void updateSoundVolumeInitialPosition() {
        background_theme.setVolume(0.05);
        congratsSound.setVolume(1);
        clickSound.setVolume(1);
    }

    public void startPlayMusic() {
        background_theme.setCycleCount(Integer.MAX_VALUE);
        background_theme.seek(Duration.ZERO);
        background_theme.play();

    }
    public void playCongrats() {
        congratsSound.seek(Duration.ZERO);
        congratsSound.play();
    }

    public void playPrimary() {
        background_theme.seek(Duration.ZERO);
        background_theme.play();
    }

    public void playClick() {
        clickSound.seek(Duration.ZERO);
        clickSound.play();
    }
}
