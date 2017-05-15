package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
* <h1>Dialogs</h1>
* Handles all dialog and alert windows such as information alerts, 
* confirmation alerts as well as input dialogs for single or double inputs. 
*
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
	
	/** Shows an input dialog with one input field.
	 * @param title the title of the pop up window
	 * @param headerText the string to show in the dialog header area
	 * @param label the string to show in the dialog content area before the input field
	 * @return An Optional that contains the result
	 */
	public static Optional<String> logInDialog(String title, String headerText, String label) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(label + ": ");
		GridPane grid = new GridPane();
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeLogin = new ButtonType("Log in", ButtonData.OK_DONE);
		ButtonType buttonTypeExit = new ButtonType("Exit", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonTypeLogin, buttonTypeExit);
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
	
}
