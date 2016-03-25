package nick.dev.display;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import nick.dev.utilities.Utilities;

public class DisplayJavaFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Integer width = gd.getDisplayMode().getWidth();
		Integer height = gd.getDisplayMode().getHeight();

		Utilities.Debug(width);
		Utilities.Debug(height);
		width = Integer.parseInt(Utilities.getPropValue("gameWidth"));
		height = Integer.parseInt(Utilities.getPropValue("gameHeight"));
		primaryStage.setTitle(Utilities.getPropValue("gameTitle"));
		Group g = new Group();
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		render(gc);
		g.getChildren().add(canvas);
		Scene s = new Scene(g);

		// Apparently this is how MouseListeners work in JavaFX...
		s.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Circle circle = new Circle(event.getSceneX(), event.getSceneY(), 30);
				circle.setFill(Color.BLUE);
				g.getChildren().add(circle);
			}
		});
		primaryStage.setScene(s);
		primaryStage.show();
	}

	private void render(GraphicsContext gc) {

	}

	public static void main(String[] args) {
		launch(args);
	}

}
