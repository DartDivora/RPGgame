package nick.dev.audio;

import java.net.URL;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nick.dev.base.Handler;
import nick.dev.utilities.Utilities;

public class AudioManager {

	private Handler handler;
	private Media m = null;
	private MediaPlayer mp = null;
	@SuppressWarnings("unused")
	private JFXPanel fxPanel = null;
	private boolean mute = false;

	public void Track(String path) {
		URL resource = getClass().getResource(path);
		m = new Media(resource.toString());
		mp = new MediaPlayer(m);
	}

	public AudioManager() {
		fxPanel = new JFXPanel();
		mute = Boolean.parseBoolean(Utilities.getPropValue("mute", Utilities.getPropFile()));
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void play() {
		mp.play();
	}

	public void stop() {
		mp.stop();
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public void update() {
		if (mp != null && handler.getKeyManager().m) {
			mp.setMute(!mp.isMute());
		}
	}
}
