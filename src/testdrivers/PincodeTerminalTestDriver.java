package hardware_testdrivers;

import java.util.ArrayList;

import hardware_interfaces.PincodeObserver;
import hardware_interfaces.PincodeTerminal;
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
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class graphically simulated a pincode terminal. 
 * 
 * @version 1.0
 * @author Anna Axelsson 
 */
public class PincodeTerminalTestDriver implements PincodeTerminal {
	private static final String[] KEYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "0", "*"};
	Rectangle red;
	Rectangle green;
	private ArrayList<PincodeObserver> pincodeHandlers = new ArrayList<>();
	
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
		root.setStyle("-fx-background-color: #d0e1d8;"
				+ "-fx-font-size: 18;");
		root.setPrefWidth(300);
		
		HBox ledBox = new HBox();
		ledBox.setPadding(new Insets(10, 10, 10, 10));
		ledBox.setSpacing(10);
		ledBox.setAlignment(Pos.TOP_CENTER);
		red = new Rectangle(50, 20);
		green = new Rectangle(50, 20);
		ledBox.getChildren().addAll(red, green);
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
	 * Turn on LED for lightTime seconds.
	 * @param color PinCodeTerminal.RED_LED or PinCodeTerminal.GREEN_LED 
	 * @param time Turn on LED for time seconds
	 */
	public void lightLED(int color, int time) {
		if (color == RED_LED) {
			lightLED(red, Color.RED, time );
		}
		if (color == GREEN_LED) {
			lightLED(green, Color.GREEN, time );
		}
	}
	
	private void lightLED(Shape shape, Paint color, int lightTime) {
		Paint oldColor = shape.getFill();
		shape.setFill(color);
		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(lightTime * 1000),
				e -> {shape.setFill(oldColor);}));
		timeline.play();
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
