package nick.dev.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Display implements ActionListener {

	private JFrame frame;
	private JMenuBar menubar;
	private String title, saveAction, loadAction;
	private int width, height;
	private Canvas canvas;
	private JMenu mainMenu;
	private JMenuItem save, load;
	private boolean saveGame, loadGame = false;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.saveAction = "Save game...";
		this.loadAction = "Load game...";

		menubar = new JMenuBar();
		mainMenu = new JMenu("Menu");
		menubar.add(mainMenu);
		save = new JMenuItem(saveAction);
		load = new JMenuItem(loadAction);
		save.addActionListener(this);
		load.addActionListener(this);
		mainMenu.add(save);
		mainMenu.add(load);
		createDisplay();
	}

	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame.setJMenuBar(menubar);
		frame.add(canvas);
		frame.pack();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public boolean isSaveGame() {
		return saveGame;
	}

	public void setSaveGame(boolean saveGame) {
		this.saveGame = saveGame;
	}

	public boolean isLoadGame() {
		return loadGame;
	}

	public void setLoadGame(boolean loadGame) {
		this.loadGame = loadGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(saveAction)) {
			System.out.println("This will save a game!");
			this.setSaveGame(true);
		} else if (e.getActionCommand().equals(loadAction)) {
			System.out.println("This will load a game!");
			this.setLoadGame(true);
		}
	}

}
