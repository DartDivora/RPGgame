package nick.dev.audio;

import java.net.URL;
import java.util.HashMap;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
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
		Overworld, Battle, TalkSFX, MenuChangeSFX
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
		this.isMuted = Boolean.parseBoolean(Utilities.getPropValue("mute"));

		this.addTrack(Tracks.Overworld, Utilities.getPropValue("musicOverworld"));
		this.addTrack(Tracks.Battle, Utilities.getPropValue("musicBattle"));
		this.addSFX(Tracks.MenuChangeSFX, Utilities.getPropValue("menuChangeSFX"));
		this.addRepeatingSFX(Tracks.TalkSFX, Utilities.getPropValue("talkSFX"));
	}

	/*****************************************************************
	 * Used by the constructor to add a track to the list of tracks.
	 * 
	 * @param track
	 * @param path
	 *****************************************************************/
	private void addTrack(Tracks track, String path) {
		URL resource = getClass().getResource(path);
		Media m = new Media(resource.toString());

		this.tracks.put(track, new MediaPlayer(m));
	}
	
	/*****************************************************************
	 * Used by the constructor to add a SFX to the list of tracks.
	 * 
	 * @param track
	 * @param path
	 *****************************************************************/
	private void addSFX(Tracks track, String path) {
		URL resource = getClass().getResource(path);
		Media m = new Media(resource.toString());

		this.tracks.put(track, new MediaPlayer(m));
	}
	/*****************************************************************
	 * Do not keep this!! Have to figure out how to either fix or replace
	 * MediaPlayer before we can get rid of this. Starting and stopping a
	 * MediaPlayer object is too slow.
	 * 
	 * @param track
	 * @param path
	 *****************************************************************/
	private void addRepeatingSFX(Tracks track, String path) {
		URL resource = getClass().getResource(path);
		Media m = new Media(resource.toString());

		MediaPlayer mp = new MediaPlayer(m);
		mp.setCycleCount(Integer.MAX_VALUE);

		this.tracks.put(track, mp);
	}

	/*****************************************************************
	 * This method is called every frame. Currently only checks to see if we try
	 * to mute audio.
	 *****************************************************************/
	public void update() {
		if (Handler.getKeyManager().keyIsPressed(Keys.Mute)) {
			if (this.isMuted) {
				this.unmute();
			} else {
				this.mute();
			}
		}
	}

	/*****************************************************************
	 * Play specified track. Stops the currently playing track to play this one.
	 * 
	 * @param track
	 *****************************************************************/
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
	
	/*****************************************************************
	 * Play specified sfx. 
	 * 
	 * @param track
	 *****************************************************************/
	public void playSFX(Tracks track) {
		if (!this.isMuted) {
			tracks.get(track).seek(Duration.ZERO);
			tracks.get(track).play();
		}
	}

	/*****************************************************************
	 * Play Sound Effect. Still a track, but it doesn't stop the current track.
	 * This will repeat until stopRepeatingSFX is called on the track.
	 * 
	 * DO NOT KEEP THIS, MAKE A REAL SOLUTION. Unfortunately, the solution
	 * involves either using something else to play audio or slowing down the
	 * game a lot.
	 * 
	 * @param track
	 *****************************************************************/
	public void playRepeatingSFX(Tracks track) {
		if (!this.isMuted && tracks.get(track).getStatus() != Status.PLAYING) {
			tracks.get(track).play();
		}
	}

	/*****************************************************************
	 * Stops a repeating SFX.
	 * 
	 * @param track
	 *****************************************************************/
	public void stopRepeatingSFX(Tracks track) {
		if (!this.isMuted && tracks.get(track).getStatus() == Status.PLAYING) {
			tracks.get(track).stop();
		}
	}

	/*****************************************************************
	 * Resumes playing the current track - starts from where it was paused or
	 * from the beginning if it was stopped.
	 *****************************************************************/
	public void resumeCurrentTrack() {
		if (currentTrack != null && !this.isMuted) {
			tracks.get(currentTrack).play();
		}
	}

	/*****************************************************************
	 * Pauses the current track - when played again, it will start from where it
	 * left off.
	 *****************************************************************/
	public void pauseCurrentTrack() {
		if (currentTrack != null) {
			tracks.get(currentTrack).pause();
		}
	}

	/*****************************************************************
	 * Stops the current track - when played again, it will start from the
	 * beginning.
	 *****************************************************************/
	public void stopCurrentTrack() {
		if (currentTrack != null) {
			tracks.get(currentTrack).stop();
		}
	}

	public void pauseAllTracks() {
		for (HashMap.Entry<Tracks, MediaPlayer> entry : tracks.entrySet()) {
			entry.getValue().pause();
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
		this.pauseAllTracks();
	}
}
