package testdrivers;

import interfaces.ElectronicLock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class simulates an electronic lock. 
 * 
 * @version 1.0
 * @author Anna Axelsson
 */
public class ElectronicLockTestDriver implements ElectronicLock {
	private Label label;
	
	/**
	 * Creates a graphically simulated electronic. 
	 * Can only be instantiated inside an JavaFX Application Thread.
	 * @param title The title of the window.
	 * @param xPos The horizontal location of the window on the screen.
	 * @param yPos The vertical location of the window on the screen.
	 */
	public ElectronicLockTestDriver(String title, int xPos, int yPos) {
		Pane root = new StackPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setStyle("-fx-background-color: #0096c9;"
				+ "-fx-font-size: 14;");
		root.setPrefWidth(300);
		
		label = new Label("LOCKED");
		root.getChildren().add(label);
	
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setX(xPos);
		stage.setY(yPos);
		stage.show();
	}

	/**
	 *  Open the lock.
	 *  @param timeOpen time it should be open (s) 
	 */
	public void open(int timeOpen) {
		label.setText("OPEN");
		System.out.println("open");
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(timeOpen * 1000),
		        e -> {label.setText("LOCKED");}));
		timeline.play();
	}
}


