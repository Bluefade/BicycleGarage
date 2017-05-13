package testdrivers;

import interfaces.BarcodePrinter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is an class representing a barcode printer. 
 * 
 * @version 1.0
 * @author Anna Axelsson 
 */
public class BarcodePrinterTestDriver implements BarcodePrinter {
	private TextArea textArea;
	private int serialNr = 0;

	
	/**
	 * Creates a graphically simulated barcode printer. 
	 * Can only be instantiated inside an JavaFX Application Thread.
	 * @param title The title of the window.
	 * @param xPos The horizontal location of the window on the screen.
	 * @param yPos The vertical location of the window on the screen.
	 */
	public BarcodePrinterTestDriver(String title, int xPos, int yPos) {
		Pane root = new StackPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setStyle("-fx-background-color: #d0e1d8;"
				+ "-fx-font-size: 14;");
		root.setPrefWidth(300);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefHeight(100);
		root.getChildren().add(textArea);
			
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setX(xPos);
		stage.setY(yPos);
		stage.show();
	}
	
	/**
	 * Prints a barcode.
	 * @param barcode a string of 5 characters, every character can be '0', '1',... '9'. 
	 * @throws IllegalArgumentException if barcode does not consists of five digits
	 */
	public void printBarcode(String barcode) {
		if (barcode.length() != 5 || ! barcode.matches("[0-9]+")) {
			throw new IllegalArgumentException();
		}
		textArea.appendText("Event " + ++serialNr + ": Printing " + barcode + "\n");
	}
}
