package nick.dev.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import nick.dev.base.Handler;

/**
 * This class sets the basic Canvas and JFrames. May be removed in use of a
 * better way to render.
 * 
 * @author nsanft,acharles
 * @version 1.1
 */
public class Display implements ActionListener {

	private JFrame frame;
	private JMenuBar menubar;
	private String title, saveAction, loadAction;
	private int width, height;
	private Canvas canvas;
	private JMenu mainMenu;
	private JMenuItem save, load;
	private JFileChooser jfc = new JFileChooser();

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

		jfc = new JFileChooser();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(saveAction)) {
			System.out.println("This will save a game!");
			if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				System.out.println(jfc.getSelectedFile());
				Handler.getSaveManager().saveGame(jfc.getSelectedFile().toString());
			}
		} else if (e.getActionCommand().equals(loadAction)) {
			System.out.println("This will load a game!");
			if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				System.out.println(jfc.getSelectedFile());
				Handler.getSaveManager().loadGame(jfc.getSelectedFile().toString());
			}
		}
	}

}
