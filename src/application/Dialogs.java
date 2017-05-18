package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
* <h1>Dialogs</h1>
* Handles all dialog and alert windows such as information alerts, 
* confirmation alerts as well as input dialogs for single or double inputs. 
*
* @version 1.0
* @author Group 9
*/

public class Dialogs {
	
	/** Shows an information alert.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param infoText the string to show in the dialog content area
	 */
	public static void alert(String title, String headerText, String infoText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(infoText);
		alert.showAndWait();
	}
	
	/** Shows an error alert.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param infoText the string to show in the dialog content area
	 */
	public static void alertError(String title, String headerText, String infoText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(infoText);
		alert.showAndWait();
	}
	
	/** Shows an confirmation alert with buttons "Yes" and "No".
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param question the string to show in the dialog content area
	 * @return An Optional that contains the result
	 */
	public static boolean confirmDialog(String title, String headerText, String question) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(question);

		ButtonType buttonTypeOne = new ButtonType("Yes");
		ButtonType buttonTypeTwo = new ButtonType("No");		
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    return true;
		} else if (result.get() == buttonTypeTwo) {
		    return false;
		} else {
		    return false;
		}
	}
	
	/** Shows a login dialog with one passwordField used for password input.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param label the string to show in the dialog content area before the input field
	 * @return An Optional that contains the result
	 */
	public static String logInDialog(String title, String headerText, String question) {
	Dialog<String> dialog = new Dialog<>();
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));
    dialog.setTitle(title);
    Text headerTxt = new Text(headerText);
    headerTxt.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, 18));
    grid.add(headerTxt, 0, 0, 2, 1);
    
	dialog.setGraphic(new ImageView(Dialogs.class.getResource("login.png").toString()));

    Label pw = new Label("Password:");
    grid.add(pw, 0, 2);
    PasswordField passwordField = new PasswordField();
    grid.add(passwordField, 1, 2);
    
    dialog.getDialogPane().setContent(grid);
	ButtonType buttonTypeLogin = new ButtonType("Login", ButtonData.OK_DONE);
	ButtonType buttonTypeCancel = new ButtonType("Exit", ButtonData.CANCEL_CLOSE);
	dialog.getDialogPane().getButtonTypes().addAll(buttonTypeCancel, buttonTypeLogin);

	// Enable/Disable login button depending on whether a username was entered.
	Node loginButton = dialog.getDialogPane().lookupButton(buttonTypeLogin);
	loginButton.setDisable(true);

	// Do some validation (using the Java 8 lambda syntax).
	passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
	    loginButton.setDisable(newValue.trim().isEmpty());
	});
	
	dialog.showAndWait();
	dialog.setResult(passwordField.getText());
	return dialog.getResult();
  }

	/** Shows an input dialog with one input field.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param label the string to show in the dialog content area before the input field
	 * @return An Optional that contains the result
	 */
	public static Optional<String> oneInputDialog(String title, String headerText, String label) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(label + ": ");
		return dialog.showAndWait();
	}
	
	/** Shows a login dialog with one input field for password.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param label the string to show in the dialog content area before the input field
	 * @return An Optional that contains the result
	 */
	public static Optional<String> oldLogInDialog(String title, String headerText, String label) {
		TextInputDialog dialog = new TextInputDialog();
		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(Dialogs.class.getResource("login.png").toString()));

		// Set the button types.
		dialog.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		ButtonType exitButtonType = new ButtonType("Exit", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(exitButtonType, loginButtonType);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(label + ": ");
		return dialog.showAndWait();
	}

	/** Shows an input dialog with two input fields.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param labels the strings to show in the dialog content area before the input fields
	 * @return An Optional that contains the result
	 */
	public static Optional<String[]> twoInputsDialog(String title, String headerText, String[] labels) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		Label label1 = new Label(labels[0] + ": ");
		Label label2 = new Label(labels[1] + ": ");
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(tf1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(tf2, 2, 2);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonTypeCancel, buttonTypeOk);
		
		dialog.setResultConverter(new Callback<ButtonType, String>() {
			public String call(ButtonType b) {
				String inputs = null;
				if (b == buttonTypeOk) {
					if (tf1.getText().equals("") || tf2.getText().equals("")) {
						return null;
					}
					inputs = tf1.getText() + ":" + tf2.getText();				
				}
				return inputs;
			}
		});
		tf1.requestFocus();

		Optional<String> result = dialog.showAndWait();
		String[] input = null;
		if (result.isPresent()) {
			input = result.get().split(":");
		}
		return Optional.ofNullable(input);
	}
	
	/** Shows a multiple choice dialog with drop down selector.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param labels the strings to show in the dialog content area before the input fields
	 * @param bicycles the bicycles to choose from
	 * @return An Optional that contains the result
	 */
	public static Optional<Bicycle> choiceDialog(String title, String headerText, String label, Set<Bicycle> bicycle ) {
		//create choice dialog
		Iterator<Bicycle> i = bicycle.iterator();
		ChoiceDialog<Bicycle> dialog = new ChoiceDialog<>(i.next(), bicycle);
		
		//Create titles
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(label);
		
		return dialog.showAndWait();
	}
	
}
