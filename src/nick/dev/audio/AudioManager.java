package nick.dev.audio;

import java.net.URL;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nick.dev.utilities.Utilities;

public class AudioManager {

	private Media m = null;
	private MediaPlayer mp = null;
	@SuppressWarnings("unused")
	private JFXPanel fxPanel = null;
	private boolean mute = true;

	public AudioManager() {
		fxPanel = new JFXPanel();
		mute = Boolean.parseBoolean(Utilities.getPropValue("mute", Utilities.getPropFile()));
	}

	public void Track(String path) {
		URL resource = getClass().getResource(path);
		m = new Media(resource.toString());
		mp = new MediaPlayer(m);
	}

	public void play() {
		if (!mute) {
			mp.play();
		}
	}

	public void stop() {
		mp.stop();
	}

	public static void main(String[] args) {
		AudioManager am = new AudioManager();
		am.Track("/music/Adventure.mp3");
		am.play();
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

}
