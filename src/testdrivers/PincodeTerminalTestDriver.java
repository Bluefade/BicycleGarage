package testdrivers;

import java.util.ArrayList;

import interfaces.PincodeObserver;
import interfaces.PincodeTerminal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class simulates a pincode terminal. 
 * 
 * @version 1.2
 * @author Anna Axelsson 
 */
public class PincodeTerminalTestDriver implements PincodeTerminal {
	private static final String[] KEYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "0", "*"};
	private ArrayList<PincodeObserver> pincodeHandlers = new ArrayList<>();
	private LED red;
	private LED green;

	
	/** 
	 * Creates a graphically simulated pincode terminal. 
	 * Can only be instantiated inside an JavaFX Application Thread.
	 * @param title The title of the window.
	 * @param xPos The horizontal location of the window on the screen.
	 * @param yPos The vertical location of the window on the screen.
	 */
	public PincodeTerminalTestDriver(String title, int xPos, int yPos) {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setStyle("-fx-background-color: #0096c9;"
				+ "-fx-font-size: 18;");
		root.setPrefWidth(300);
		
		HBox ledBox = new HBox();
		ledBox.setPadding(new Insets(10, 10, 10, 10));
		ledBox.setSpacing(10);
		ledBox.setAlignment(Pos.TOP_CENTER);
		Rectangle redRectangle = new Rectangle(50, 20);
		//red.setFill(offColor);
		Rectangle greenRectangle = new Rectangle(50, 20);
		// green.setFill(offColor);
		ledBox.getChildren().addAll(redRectangle, greenRectangle);
		red = new LED(Color.RED, redRectangle);
		green = new LED(Color.GREEN, greenRectangle);
		root.setCenter(ledBox);
		
		TilePane tilePane = new TilePane();		
		tilePane.setPrefRows(4);
		tilePane.setPrefColumns(3);
		for (int i = 0; i < KEYS.length; i++) {
			Button b = new Button(KEYS[i]);
			b.setPrefSize(40, 40);
			b.setOnAction(e -> {for (PincodeObserver h : pincodeHandlers) {
				h.handleCharacter(b.getText().charAt(0));
			}});
			tilePane.getChildren().add(b);
		}	
		Group group = new Group(tilePane);
		root.setLeft(group);
	
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setX(xPos);
		stage.setY(yPos);
		stage.show();
	}

	
	/** 
	 * Turns on LED for lightTime seconds.
	 * @param color PinCodeTerminal.RED_LED or PinCodeTerminal.GREEN_LED 
	 * @param time Turn on LED for time seconds
	 */
	public void lightLED(int color, int time) {
		if (color == RED_LED) {
			red.lightLED(time);
		}
		if (color == GREEN_LED) {
			green.lightLED(time);
		}
	}
	
	private class LED {
		private Paint color;
		private Rectangle lamp;
		private Color offColor = Color.BLACK;
		private Timeline timeline;
		
		private LED(Paint color, Rectangle lamp) {
			this.color = color;
			this.lamp = lamp;
			lamp.setFill(offColor);	
		}
		
		private void lightLED(int lightTime) {	
			lamp.setFill(color);
			if (timeline != null ) {
				timeline.stop();
			}			
			timeline = new Timeline(new KeyFrame(
					Duration.millis(lightTime * 1000),
					e -> {lamp.setFill(offColor);}));
			timeline.play();
		}
		
	}

	@Override
	/**
	 *  Registers a new observer to call when a user has pressed a key. 
	 *  @param observer an observer to add
	 */
	public void registerObserver(PincodeObserver pincodeHandler) {
		pincodeHandlers.add(pincodeHandler);
	}
}
