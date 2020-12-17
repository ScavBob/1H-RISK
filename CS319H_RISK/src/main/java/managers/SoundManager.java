package managers;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {

    String musicFilePath = "/IdeaProjects/1H-RISK/CS319H_RISK/resources/musics/medal-of-honor-european-assault-soundtrack-main-theme-hq.mp3";
    private final Media musicFile = new Media(new File(musicFilePath).toURI().toString());
    private final MediaPlayer background_theme = new MediaPlayer(musicFile);

    String alternativePath = "/IdeaProjects/1H-RISK/CS319H_RISK/resources/musics/battlefield-1942-theme-song.mp3";
    private final Media alternative_music = new Media(new File(musicFilePath).toURI().toString());
    private final MediaPlayer alternative_background_theme = new MediaPlayer(alternative_music);

    String congratsPath = "/IdeaProjects/1H-RISK/CS319H_RISK/resources/musics/you-win-street-fighter-sound-effect.mp3";
    private final Media congrats = new Media(new File(congratsPath).toURI().toString());
    private final MediaPlayer congratsSound = new MediaPlayer(congrats);


    String clickPath = "/IdeaProjects/1H-RISK/CS319H_RISK/resources/musics/mouse-click-sound-effect-hd.mp3";
    private final Media click = new Media(new File(clickPath).toURI().toString());
    private final MediaPlayer clickSound = new MediaPlayer(click);


    public void mute() {
        background_theme.setVolume(0);
        congratsSound.setVolume(0);
        clickSound.setVolume(0);
    }

    public void setInitialVolume() {
        updateSoundVolumeInitialPosition();
    }

    public void updateSoundVolumeInitialPosition() {
        background_theme.setVolume(5);
        congratsSound.setVolume(5);
        clickSound.setVolume(5);
    }

    public void startPlayMusic() {
        background_theme.setCycleCount(Integer.MAX_VALUE);
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
