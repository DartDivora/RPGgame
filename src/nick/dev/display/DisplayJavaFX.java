package nick.dev.display;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class DisplayJavaFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("This is a test");
		Group g = new Group();
		Canvas canvas = new Canvas(1000, 1000);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		render(gc);
		g.getChildren().add(canvas);
		primaryStage.setScene(new Scene(g));
		primaryStage.show();
	}

	private void render(GraphicsContext gc) {

	}

	public static void main(String[] args) {
		launch(args);
	}

}
