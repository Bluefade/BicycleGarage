package testdrivers;

import java.util.ArrayList;

import interfaces.BarcodeObserver;
import interfaces.BarcodeScanner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is an class representing a barcode scanner. 
 * 
 * @version 1.0
 * @author Anna Axelsson
 */
public class BarcodeScannerTestDriver implements BarcodeScanner {
	private ArrayList<BarcodeObserver> barcodeHandlers = new ArrayList<>();
	
	/** 
	 * Creates a graphically simulated barcode scanner. 
	 * Can only be instantiated inside an JavaFX Application Thread.
	 * @param title The title of the window.
	 * @param xPos The horizontal location of the window on the screen
	 * @param yPos The vertical location of the window on the screen
	 */
	public BarcodeScannerTestDriver(String title, int xPos, int yPos) {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setStyle("-fx-background-color: #d0e1d8;"
				+ "-fx-font-size: 14;");
		root.setPrefWidth(300);
		
		TextField tf = new TextField();
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		Label label = new Label("Barcode:");
		hbox.getChildren().addAll(label, tf);
		root.setTop(hbox);

		Button button = new Button("Scan");
		button.setStyle("-fx-background-color: #adcab8;");
		button.setOnAction(e -> { 
			String code = tf.getText();
			tf.setText("");
			for (BarcodeObserver h : barcodeHandlers) {
				h.handleBarcode(code);
			}
		});
		root.setBottom(button);
		
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setX(xPos);
		stage.setY(yPos);
		stage.show();
	}	
	
	@Override
	/**
	 *  Registers a new observer to call when a user has used the scanner. 
	 *  @param handler a observer to add
	 */
	public void registerObserver(BarcodeObserver observer) {
		barcodeHandlers.add(observer);
	}
}
