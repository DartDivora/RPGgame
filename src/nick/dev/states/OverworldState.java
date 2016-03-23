package nick.dev.states;

import java.awt.Graphics;

import nick.dev.audio.AudioManager.Tracks;
import nick.dev.base.Handler;
import nick.dev.entities.Entity.Direction;
import nick.dev.entities.Player;
import nick.dev.entities.NPC;
import nick.dev.input.KeyManager.Keys;
import nick.dev.maps.Map;
import nick.dev.utilities.Utilities;
import nick.dev.worlds.World;

public class OverworldState extends State {

	private World world;

	/*****************************************************
	 * Constructor, sets up the world.
	 *****************************************************/
	public OverworldState(StateManager stateManager) {
		super(stateManager);
		world = new World(Utilities.getPropValue("world3"));
		Handler.setWorld(world);
	}

	/*****************************************************
	 * Update and check for keys, etc.
	 *****************************************************/
	@Override
	public void update() {
		world.update();

		if (Handler.getKeyManager().keyIsPressed(Keys.Space)) {
			this.stateManager.changeState(Types.Battle);
		}
		if (Handler.getKeyManager().keyIsPressed(Keys.Menu)) {
			this.stateManager.changeState(Types.Menu);
		}		

		this.checkForDialog();
	}

	/*****************************************************
	 * Render the world.
	 *****************************************************/
	@Override
	public void render(Graphics g) {
		// Clear Screen
		g.clearRect(0, 0, Handler.getWidth(), Handler.getHeight());
		world.render(g);
	}

	/*****************************************************
	 * Called when entering the state.
	 *****************************************************/
	@Override
	public void onEnter() {
		Handler.getAudioManager().playTrack(Tracks.Overworld);
	}

	/*****************************************************
	 * Called when exiting the state.
	 *****************************************************/
	@Override
	public void onExit() {
		// Handler.getAudioManager().stopCurrentTrack();
	}

	/*****************************************************
	 * Checks to see if the player is facing something it can talk to.
	 *****************************************************/
	private void checkForDialog() {
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {

			// Get the player and its position/direction.
			Player player = world.getEntityManager().getPlayer();
			float checkX = player.getX();
			float checkY = player.getY();
			Direction dir = player.getDirection();

			// Change where we are checking depending on direction.
			// Basically, look in front of the player.
			switch (dir) {
			case Up:
				checkY -= Map.TileHeight;
				break;
			case Right:
				checkX += Map.TileWidth;
				break;
			case Down:
				checkY += Map.TileHeight;
				break;
			case Left:
				checkX -= Map.TileWidth;
				break;
			default:
				break;
			}

			// See if there's an NPC in a box in front of our player.
			Integer npcID = world.getEntityManager().entityExistsHere((int) checkX, (int) checkY);
			if (npcID != null) {

				// Get the NPC that our box collided with and send his
				// dialogueID
				// off to the Dialog State.
				NPC npc = (NPC) world.getEntityManager().getEntity(npcID);
				Integer dialogueID = npc.getDialogueID();

				StateArgument arg = new StateArgument();
				arg.setDialogLine(dialogueID);
				this.stateManager.changeState(Types.Dialog, arg);
			}
		}
	}

}
