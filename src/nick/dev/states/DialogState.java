package nick.dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.input.KeyManager.Keys;

/**
 * This class will display dialog in the game. Extends the State class.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class DialogState extends State {

	private Font f;
	// private Integer messageID = 0;
	private String testMessage = "This is what I'm talking about, you know that's right";
	private String currMessage = "";
	private Integer currMessageChar = 0;
	private Integer framesBetweenChars = 2;
	private Integer framesSinceLastChar = 0;

	private Integer dialogStartX = 0;
	private Integer dialogStartY = 0;
	private Integer dialogBoxLength = 0;
	private Integer dialogBoxHeight = 0;
	private Integer dialogBoxInnerMargin = 10;

	public DialogState(StateManager stateManager) {
		super(stateManager);
		f = new Font("arial", Font.BOLD, 25);
		// TODO discuss implementing the DialogManager.
	}

	public void reinitialize() {
		this.currMessage = "";
		this.currMessageChar = 0;
		this.framesSinceLastChar = 0;
	}

	@Override
	public void update() {

		// Check if talk button is pressed - if it was and the message isn't
		// all displayed, then show the message. If it was all displayed,
		// leave the dialog state.
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			if (this.currMessageChar == this.testMessage.length()) {
				this.stateManager.leaveState();
			} else {
				this.currMessageChar = this.testMessage.length();
				this.currMessage = this.testMessage;
				Handler.getAudioManager().stopRepeatingSFX(Tracks.TalkSFX);
			}
		}

		// This is only here in case the window size changes. We'll probably
		// want to set it to a certain size and then just black-box around it,
		// but maybe not.
		this.dialogStartX = 0;
		this.dialogStartY = Handler.getHeight() * 2 / 3 + 1;
		this.dialogBoxLength = Handler.getWidth();
		this.dialogBoxHeight = Handler.getHeight() / 3;

		// If the current message isn't all being shown, then just see if it's
		// time to put a new character and make the fun sound.
		if (currMessageChar != testMessage.length()) {
			this.framesSinceLastChar++;
			if (this.framesSinceLastChar >= this.framesBetweenChars) {
				this.currMessage += this.testMessage.substring(this.currMessageChar, this.currMessageChar + 1);

				// This is kind of awful but it works for now...
				// DO NOT KEEP
				Handler.getAudioManager().playRepeatingSFX(Tracks.TalkSFX);

				this.currMessageChar++;
				this.framesSinceLastChar = 0;
			}
		} else {
			Handler.getAudioManager().stopRepeatingSFX(Tracks.TalkSFX);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(f);
		g.setColor(Color.black);

		g.fillRect(this.dialogStartX, this.dialogStartY, this.dialogBoxLength, this.dialogBoxHeight);
		g.setColor(Color.white);
		g.drawString(this.currMessage, this.dialogStartX + this.dialogBoxInnerMargin,
				this.dialogStartY + this.dialogBoxInnerMargin * 3);
	}

	@Override
	public void onEnter() {
		this.reinitialize();
	}

	@Override
	public void onExit() {
	}

}
