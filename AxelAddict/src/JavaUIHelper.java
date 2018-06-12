import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaUIHelper {

	public ArrayList<String> prettifyArrayList(ArrayList<String> list) {
		ArrayList<String> prettyString = new ArrayList<String>();
		boolean setUpper = false;
		for (int i=0; i<list.size();i++){
			String temp = list.get(i);
			String prettyChars = "";
			for (int j=0; j<list.get(i).length(); j++){
				char tempChar = temp.charAt(j);
				if (j == 0 || setUpper) {
					setUpper = false;
					tempChar = Character.toUpperCase(tempChar);
				}
				if (!Character.isAlphabetic(tempChar) && !Character.isDigit(tempChar) || tempChar == ' ') {
					tempChar = ' ';
					if (j+1 <list.get(i).length()) {
						setUpper = true;
					}
				}
				prettyChars = prettyChars + tempChar;
			}
			prettyString.add(prettyChars);
		}
		return prettyString;
		
	}
	//DESCRIPTION: adds multiple choice questions to scene
	//INPUTS: a layout, a question to ask, number of options to choose 
	//		  from, a text for each button (has to match number of buttons)
	//RETURN: None
	public void multipleChoice(VBox layout, String question, int numButtons, String... args)
	{
		Label label = new Label(question);
		layout.getChildren().addAll(label);
		
		final ToggleGroup group = new ToggleGroup();
		
		RadioButton[] btn = new RadioButton[numButtons];
		
		for (int i = 0; i < numButtons; i++)
		{
			btn[i] = new RadioButton();
			btn[i].setToggleGroup(group);
			
			for (String arg : args)
			{
				btn[i].setText(args[i]);
			}
			layout.getChildren().add(btn[i]);
		}
	}
	
	//DESCRIPTION: adds written text questions to scene
	//INPUTS: a layout such as GridPane or VBox, a questions to ask
	//RETURN: None
	public void writtenText(VBox layout, String question)
	{
		Label label = new Label(question);
		TextField text = new TextField();
		layout.getChildren().addAll(label, text);
	}
	
	//DESCRIPTION: adds menu along top of window
	//INPUTS: a stage, a layout such as GridPane or VBox
	//RETURN: None
	public static void addMenu(Stage stage, VBox layout)
	{
		//FILE MENU
		Menu fileMenu = new Menu("File");
				
		//MENU ITEMS
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> exitAlert(stage));
		fileMenu.getItems().add(exit);
		
		//MAIN MENU BAR
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		
		layout.getChildren().addAll(menuBar);
	}
		
		public static void addComboBox(VBox layout, String question, String... args){
			ComboBox combobox = new ComboBox();
			Label label = new Label(question);
			for (String arg : args)
			{
				combobox.getItems().addAll(arg);
			}
			layout.getChildren().addAll(label, combobox);
		}
		
		//DESCRIPTION: used for any time a user wants to exit window it will prompt them with a window
		//INPUTS: a stage
		//RETURN: None
		public static void exitAlert(Stage stage)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure you want to exit?");
			
			ButtonType yesButton = new ButtonType("Yes");
			ButtonType noButton = new ButtonType("No");
			alert.getButtonTypes().setAll(yesButton, noButton);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == yesButton) 
			{
				stage.close();
			}
		}
}
