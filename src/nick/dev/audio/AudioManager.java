package nick.dev.audio;

import java.net.URL;
import java.util.HashMap;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;
import nick.dev.utilities.Utilities;

/**
 * This class manages all Audio played in the game.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class AudioManager {

	public enum Tracks {
		Overworld, Battle
	};

	@SuppressWarnings("unused")
	private JFXPanel fxPanel = new JFXPanel();
	private boolean isMuted = false;

	/**
	 * List of all currently loaded tracks.
	 */
	private HashMap<Tracks, MediaPlayer> tracks = new HashMap<Tracks, MediaPlayer>();
	private Tracks currentTrack = null;

	/**
	 * This constructor adds the handler and initializes the tracks.
	 */
	public AudioManager() {
		this.isMuted = Boolean.parseBoolean(Utilities.getPropValue("mute", Utilities.getPropFile()));

		this.addTrack(Tracks.Overworld, Utilities.getPropValue("musicOverworld", Utilities.getPropFile()));
		this.addTrack(Tracks.Battle, Utilities.getPropValue("musicBattle", Utilities.getPropFile()));
	}

	/**
	 * Used by the constructor to add a track to the list of tracks.
	 * 
	 * @param track
	 * @param path
	 */
	private void addTrack(Tracks track, String path) {
		URL resource = getClass().getResource(path);
		Media m = new Media(resource.toString());

		this.tracks.put(track, new MediaPlayer(m));
	}

	/**
	 * This method is called every frame. Currently only checks to see if we try
	 * to mute audio.
	 */
	public void update() {
		if (Handler.getKeyManager().keyIsPressed(Keys.Mute)) {
			if (this.isMuted) {
				this.unmute();
			} else {
				this.mute();
			}
		}
	}

	/**
	 * Play specified track. Stops the currently playing track to play this one.
	 * 
	 * @param track
	 */
	public void playTrack(Tracks track) {
		if (currentTrack != track) {
			if (currentTrack != null) {
				tracks.get(currentTrack).stop();
			}
			currentTrack = track;
			if (!this.isMuted) {
				tracks.get(currentTrack).play();
			}
		}
	}

	/**
	 * Play Sound Effect. Still a track, but it doesn't stop the current track.
	 * Should only be used for short sound effects since otherwise you get
	 * multiple songs at once...
	 * 
	 * @param track
	 */

	public void playSFX(Tracks track) {
		if (!this.isMuted) {
			tracks.get(track).play();
		}
	}

	/**
	 * Resumes playing the current track - starts from where it was paused or
	 * from the beginning if it was stopped.
	 */
	public void resumeCurrentTrack() {
		if (currentTrack != null && !this.isMuted) {
			tracks.get(currentTrack).play();
		}
	}

	/**
	 * Pauses the current track - when played again, it will start from where it
	 * left off.
	 */
	public void pauseCurrentTrack() {
		if (currentTrack != null) {
			tracks.get(currentTrack).pause();
		}
	}

	/**
	 * Stops the current track - when played again, it will start from the
	 * beginning.
	 */
	public void stopCurrentTrack() {
		if (currentTrack != null) {
			tracks.get(currentTrack).stop();
		}
	}

	/**
	 * Unmutes all tracks.
	 */
	public void unmute() {
		this.isMuted = false;
		this.resumeCurrentTrack();
	}

	/**
	 * Mutes all tracks.
	 * 
	 */
	public void mute() {
		// TODO: Make all tracks mute, just need to iterate through all tracks
		// and pause them.
		this.isMuted = true;
		this.pauseCurrentTrack();
	}
}
