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

	public OverworldState(StateManager stateManager) {
		super(stateManager);
		world = new World(Utilities.getPropValue("world3"));
		Handler.setWorld(world);
	}

	@Override
	public void update() {
		world.update();
		
		if (Handler.getKeyManager().keyIsPressed(Keys.Space)) {
			this.stateManager.changeState(Types.Battle);
		}
		
		this.checkForDialog();
	}

	@Override
	public void render(Graphics g) {
		// Clear Screen
		g.clearRect(0, 0, Handler.getWidth(), Handler.getHeight());
		world.render(g);
	}

	@Override
	public void onEnter() {
		Handler.getAudioManager().playTrack(Tracks.Overworld);
	}

	@Override
	public void onExit() {
		//Handler.getAudioManager().stopCurrentTrack();
	}
	
	private void checkForDialog() {
		if (Handler.getKeyManager().keyIsPressed(Keys.Talk)) {
			Player player = world.getEntityManager().getPlayer();
			float checkX = player.getX();
			float checkY = player.getY();
			Direction dir = player.getDirection();
			
			switch(dir) {
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
			
			Integer npcID = world.getEntityManager().entityExistsHere((int)checkX, (int)checkY);
			if (npcID != null) {
				NPC npc = (NPC) world.getEntityManager().getEntity(npcID);
				Integer dialogueID = npc.getDialogueID();
				
				StateArgument arg = new StateArgument();
				arg.setDialogLine(dialogueID);
				this.stateManager.changeState(Types.Dialog, arg);			
			}
		}
	}

}
