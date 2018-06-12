import javafx.scene.control.Alert;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class Personality extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	public static int row = 0;
	public static TextField textFields[];
	public static final ToggleGroup group = new ToggleGroup();
	public static final ToggleGroup group1 = new ToggleGroup();
	public static final ToggleGroup group2 = new ToggleGroup();
	public static final ToggleGroup group3 = new ToggleGroup();
	public static final ToggleGroup group4 = new ToggleGroup();
	private Stage mainStage;
	ToggleGroup allGroups[] = new ToggleGroup[4];
	String[] allQuestions = new String[8];
	LinkedList<KeywordPair> keywords = new LinkedList<KeywordPair>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.mainStage = primaryStage;
		//Grid where everything is displayed and the grid settings
		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(10);
	    grid.setPadding(new Insets(20, 20, 50, 10));
	    grid.setStyle("-fx-background-color: #FDFEFE ;");
	  //DONE BUTTON, SAYS THANK YOU ONCE CLICKED
	  	Button Submitbutton = new Button("SUBMIT");
	  	Submitbutton.setFont(Font.font("Calibri",FontWeight.BOLD, 15));
		MainWindow main = new MainWindow();//Main Window instance
		PersonalityResultsWindow p = new PersonalityResultsWindow();
	    BorderPane border = new BorderPane();//border of the window that holds the scenes
		Scene scene = new Scene(border, 800, 600);//Window size
		Button backButton = new Button("Back");
		HBox hbox = mkTitle();//make title of window
		border.setTop(hbox);//set the the title to the top
		border.setCenter(addQuestions(grid));//center the grid onto the window
		allGroups[0] = group1;
		allGroups[1] = group2;
		allGroups[2] = group3;
		allGroups[3] = group4;
		grid.add(Submitbutton, 0, row+1);
		
		//set the window title and the exit alert when clicking X
		primaryStage.setTitle("Personality Test");
		primaryStage.setOnCloseRequest(confirmCloseEventHandler);
		primaryStage.setResizable(false);
		
		//Add window scroll functionality
		ScrollPane sp = new ScrollPane(); 
		sp.setContent(border);	
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		//Scroll scene
		Scene scene1 = new Scene(sp, 820, 600);	//SCROLLBAR scene with layout parameters
		
		backButton.setTranslateX(-561);
	    backButton.setTranslateY(28.8);
	    backButton.setPrefHeight(30);
		backButton.setPrefWidth(50);
		backButton.setFont(Font.font("Calibri",FontWeight.BOLD,15));
		hbox.getChildren().addAll(backButton);
		//back button action 
		backButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try {
					main.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}				
			});
		
		//on submit, save the answers to the text based questions
		Submitbutton.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				boolean Empty = false;
				
				for(TextField t : Personality.textFields){
					if (t.getText().trim().isEmpty()) {Empty = true;}
				}
				
				if (Empty)
				{
					Alert alert = new Alert(AlertType.ERROR, "Please Fill Out All Questions!", ButtonType.OK);
					alert.showAndWait();
				}
				else
				{
					try
					{
						PersonalityResults();
						p.start(primaryStage, keywords);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}

		});
		
		//show the window settings to the screen
		primaryStage.setScene(scene);
		primaryStage.setScene(scene1); 
		primaryStage.setResizable(false);
		primaryStage.show();		
		
	}
	
	// anonymous class that's only to be used as an argument to PersonalityResultsWindow
	public void PersonalityResults() {
		for(int i=0;i<Personality.textFields.length; i++){
			allQuestions[i] = Personality.textFields[i].getText();
		}
		for(int i=0;i<allGroups.length;i++){
			RadioButton answerSelected = (RadioButton) allGroups[i].getSelectedToggle();
			allQuestions[i+4] = answerSelected.getText();
		}
		getPersonalityResults();
	}
	
	public void getPersonalityResults(){
		String savedQuestions = "";
		for(int i = 0;i < allQuestions.length; i++) {
			savedQuestions += allQuestions[i] + " ";
		}
		SqlKeywords.checkDatabase();
		keywords = KeywordMatching.getKeywordsFromString(savedQuestions);
	}
	
	public LinkedList<KeywordPair> getKeywords(){
		return keywords;
	}
	
	
	//DESCRIPTION: Makes the title of the page, i.e. the "Personality" title and the background color
	//RETURNS: Hbox which store the title info to be displayed back in main function
	private HBox mkTitle() {
		//Hbox that creates the title and holds all the settings
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(15);
	    hbox.setStyle("-fx-background-color: #306EFF ;");//background color
	    //title and title font settings
	    Label label = new Label("Personality Test");
	    label.setFont(Font.font("Calibri",FontWeight.BOLD, 50));
	    label.setTextFill(Color.WHITE);
	    
	    //add title
	    hbox.getChildren().addAll(label);
	    hbox.setAlignment(Pos.CENTER);
	
	    return hbox;
	}
	
	//DESCRIPTION: adds random multiple choice and written text questions to scene
	//INPUTS: a layout such as GridPane or VBox
	//RETURN: None
	public GridPane addQuestions(GridPane grid)
	{
		ColumnConstraints col = new ColumnConstraints();
		col.setFillWidth(true);
		col.setMinWidth(785);
		grid.getColumnConstraints().add(col);
		//GridPane grid = new GridPane();
		Random rand = new Random();
		int counter = 0;//iterator for text questions loop
		int row = 0;//keeps track of what row it's in currently on the grid to display questions	
		ToggleGroup group = new ToggleGroup();
		
		//string stores all text based questions
		String questions[] = {"What do you care about most when buying a house? (i.e. Price, Style, Space)",
				"Write down words someone would describe you with:", "Name some countries you would like to live in:",
				"What do you care about most when buying clothes? (i.e. Price, Style, Trend)", "How do you achieve goals in life?", "Which sport(s) do you like? (Write NONE if you don't like sports)",
				"List your top 5 values?", "List some of your hobbies (i.e. Being active, swimming, travelling):",
				"How do you like to drive?", "What kind of music do you listen to? (i.e. Hip Hop, Country, Oldies)",
				"Name some countries you would like to visit:", "Which languages can you speak?"};
		
		//loops until it Displays 4 random text based questions
		textFields = new TextField[4];
		int a = 0;
		while(counter<=3){
			for(int i=0;i<questions.length;i++){
				int num = rand.nextInt(10) + 0;
				if(num>2 && num<9){
					if(counter>3){
						break;
					}else{
						Label label = new Label(questions[i]);
						label.setFont(Font.font("Calibri",FontWeight.BOLD, 25));
						TextField text = new TextField();
						grid.add(label, 0, row);
						grid.add(text, 0, row+1);
						counter+=1;
						textFields[a] = text;
						a+=1;
					}
					row+=2;
				}
			}
			
		}
		group = group1;
		//variables for displaying the multiple choice questions
		boolean check = false;//checks if the random number is correct
		int array[] = new int[12];
		int groupNum = 1, multipleCount = 0, multipleAmount = 4;		
		
		
		//loops until 4 questions have been displayed
		while(multipleCount<multipleAmount){
			int randQuestion = rand.nextInt(11) + 0;
			
			//checks if the question chosen has already been displayed
			check = alreadyDisplayed(check, array, randQuestion);
			
			//if the question hasn't been displayed, display it
			//the appending to the array saves the question that has already been displayed
			if(check == false){
				if(randQuestion==0){
					row = multipleChoice(group, row, grid, "Do you care about the environment?", 2, "No", "Yes, I'm eco-friendly");
					multipleCount+=1;
					array[0]=randQuestion;
				}
				if(randQuestion==1){
					row = multipleChoice(group, row, grid, "Do you get emotional watching sad movies?", 2, "No, I'm a tough/courageous person", "Yes, I get emotional");
					multipleCount+=1;
					array[1]=randQuestion;
				}
				if(randQuestion==2){
					row = multipleChoice(group, row, grid, "Do you like playing sports?", 2, "No, I'm lazy", "Yes, I am an active person");
					multipleCount+=1;
					array[2]=randQuestion;
				}
				if(randQuestion==3){
					row = multipleChoice(group, row, grid, "Do you like going out?", 2, "No I'm somewhat of an introvert", "Yes, I am very adventurous");
					multipleCount+=1;
					array[3]=randQuestion;
				}
				if(randQuestion==4){
					row = multipleChoice(group, row, grid, "Do you like the Fast and Furious movie series?", 2, "No, too boring for me", "Yes, I love the Fast and Furious series");
					multipleCount+=1;
					array[4]=randQuestion;
				}
				if(randQuestion==5){
					row = 	multipleChoice(group, row, grid, "How many art museums have you visited?", 2, "None", "Many, I enjoy seeing creative works by artistic people");
					multipleCount+=1;
					array[5]=randQuestion;
				}
				if(randQuestion==6){
					row = multipleChoice(group, row, grid, "What type of workout from below is your favorite?", 4, "None, i dont like to workout", "Yoga, it is calm and relaxing", "Crossfit, I enjoy upbeat workouts", "Heavy lifting, I want to be as strong and tough as possible");
					multipleCount+=1;
					array[6]=randQuestion;
				}
				if(randQuestion==7){
					row = multipleChoice(group, row, grid, "Do you like adrenaline pumping activities such as bungee jumping or roller coasters?", 2 , "No, i get very nervous doing these", "Yes, I'm a very determined and fearless person");
					multipleCount+=1;
					array[7]=randQuestion;
				}
				if(randQuestion==8){
					row = multipleChoice(group, row, grid, "Which best describes you from below?", 4, "Intelligent, I was a very good student", "Ambitious, nothing can stop me from reaching my goals", "Creative, I dont think like others", "Persistent, I never give up");
					multipleCount+=1;
					array[8]=randQuestion;
				}
				if(randQuestion==9){
					row = multipleChoice(group, row, grid, "Do you like travelling with family?", 2, "No", "Yes, I love time with family");
					multipleCount+=1;
					array[9]=randQuestion;
				}
				if(randQuestion==10){
					row = multipleChoice(group, row, grid, "Choose a road:", 3, "A straight and calm or relxing road that goes on forever", "Windy road with an adventurous view", "Road with reasonable safe speed limits");
					multipleCount+=1;
					array[10]=randQuestion;
				}
				if(randQuestion==11){
					row = multipleChoice(group, row, grid, "How often do you upgrade your phone?", 3, "Never, I like my phone and/or im too cheap for a new one", "Once a year", "2+ a year");
					multipleCount+=1;
					array[11]=randQuestion;
				}
				groupNum++;
			}
			
			if(groupNum == 2) group = group2;
			else if(groupNum == 3) group = group3;
			else if(groupNum == 4) group = group4;
			check=false;
		}
		Personality.row = row;
		return grid;
	}

	private boolean alreadyDisplayed(boolean check, int[] array, int randQuestion) {
		for(int i=0; i<array.length;i++){
			if(randQuestion==array[i]){
				check = true;
				break;
			}
		}
		return check;
	}
	
	//DESCRIPTION: adds multiple choice questions to scene
	//INPUTS: a layout, a question to ask, number of options to choose 
	//		  from, a text for each button (has to match number of buttons)
	//RETURN: None
	@SuppressWarnings("unused")
	public int multipleChoice(ToggleGroup g, int r, GridPane grid, String question, int numButtons, String... args)
	{
		int row = r;
		int iterator = r+1;
		Label label = new Label(question);
		label.setFont(Font.font("Calibri",FontWeight.BOLD, 25));
		//layout.getChildren().addAll(label);
		grid.add(label, 0, row);
		
		RadioButton[] btn = new RadioButton[numButtons];
		
		for (int i = 0; i < numButtons; i++)
		{
			btn[i] = new RadioButton();
			btn[i].setToggleGroup(g);
			
			for (String arg : args)
			{
				btn[i].setText(args[i]);
			}
			btn[i].setFont(Font.font("Calibri",FontWeight.BOLD, 15));
			grid.add(btn[i], 0, iterator);
			iterator++;
			btn[i].setSelected(true);
		}
		
		return row+numButtons+1;
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

	
	//DESCRIPTION: used for any time a user wants to exit window it will prompt them with a window
	//INPUTS: a stage
	//RETURN: None 
	private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(mainStage);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
	};
}