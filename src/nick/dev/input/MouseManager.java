package nick.dev.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import nick.dev.utilities.Utilities;

public class MouseManager implements MouseListener, MouseMotionListener {

	public boolean[] mouseClicks;
	public boolean leftClick, rightClick;
	private Integer x, y;

	public MouseManager() {
		mouseClicks = new boolean[MouseInfo.getNumberOfButtons()];
	}

	public void update() {
		leftClick = mouseClicks[1];
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClicks[e.getButton()] = true;
		Utilities.Debug("I got clicked!");
		Utilities.Debug(e.getX());
		Utilities.Debug(e.getY());
		x = e.getX();
		y = e.getY();
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void resetXY() {
		this.x = null;
		this.y = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
