import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

public class CarTimeCalculator extends Application {

	/* Variables used throughout class declared here
	*/
	private Stage mainStage;
	private Scene scene;
	private Label zeroSixty = new Label("0 s");
	private Label quarterMile = new Label("0 s");
	private Button backButton = new Button("Back");
	private JavaUIHelper helper = new JavaUIHelper();
	private String title = "Car Calculations";
	private double HEIGHT = 600;
	private double WIDTH = 800;
	private ArrayList<Integer> years = new ArrayList<Integer>(getYears());
	private String[] Years = copy(years);
	//odometer radio btn choices
	private String[] odometerChoice = {"< 49,999", "50,000 - 99,999", "100,000 - 149,999", "> 150,000"};
	// oil change frequency radio btn choices
	private String[] maintain = {"Never miss oil changes",
			"Rarely miss oil changes",
			"Occasionally miss oil changes",
			"Rarely do oil changes",
			"What is an oil change"};
    private String stock = "Car is bone stock";
	private static MainWindow main = new MainWindow();
	// everytime user changes the oil change frequency radiobutton, it changes oilChangeFreq value
	private int oilChangeFreq = 1;
	// everytime user changes the odometer radiobutton, it changes odometerNum value
	private int odometerNum = 1;
	private CarCalcBackend backend = new CarCalcBackend();
	
	// initial method called for class
	@Override
	public void start(Stage primaryStage) throws Exception {
		// line that divides user input and app output
		this.mainStage = primaryStage;
		Line divider = new Line(0,450,WIDTH,450);
		BorderPane border = new BorderPane();
		HBox hbox = mkTitle();
		border.setTop(hbox);
		primaryStage.setTitle(title);
		GridPane center = mkBody();
		border.setCenter(center);
		divider.setFill(Color.BLUE);
		border.getChildren().addAll(divider);
		//main window class object
		
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
		scene = new Scene(border, WIDTH, HEIGHT);
		primaryStage.setOnCloseRequest(confirmCloseEventHandler);
	    primaryStage.setScene(scene);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}
	
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
	
	private GridPane mkBody() {
		final ToggleGroup odo = new ToggleGroup();
		final ToggleGroup oilChange = new ToggleGroup();
		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(10);
	    grid.setPadding(new Insets(20, 20, 50, 10));
	    
	    // set the size of each row on the GridPane
	    RowConstraints row1= new RowConstraints(), 
	    		row2= new RowConstraints(), 
	    		row3= new RowConstraints();
	    row1.setPrefHeight(60);
	    row2.setPrefHeight(30);
	    row3.setPrefHeight(20);
	    grid.getRowConstraints().addAll(row1,row1,row2,row3,row3,row3,row3,row3);
	    
	    
	    //back button
	    backButton.setTranslateX(-45);
		backButton.setTranslateY(-54);
		backButton.setPrefHeight(30);
		backButton.setPrefWidth(50);
		backButton.setFont(Font.font("Calibri",FontWeight.BOLD, 15));
		grid.getChildren().addAll(backButton);
	
	    
	    mkLabel(grid, "Horsepower(hp):",0,0);
	    
	    TextField textboxHP = mkWrittenText();
	    textboxHP.setTooltip(new Tooltip("Numeric values only"));
	    grid.add(textboxHP, 1, 0);
	    
	    mkLabel(grid,"Torque(ft*lb):",2,0);
	    
	    TextField torqueTB = mkWrittenText();
	    torqueTB.setTooltip(new Tooltip("Value must be numeric and in terms of ft*lbs"));
	    grid.add(torqueTB, 3, 0);
	    
	    mkLabel(grid, "Weight (lbs):",2,1);
	    
	    TextField textboxWT = mkWrittenText();
	    textboxWT.setTooltip(new Tooltip("Value must be numeric and in terms of lbs"));
	    grid.add(textboxWT, 3, 1);

	    
	    mkLabel(grid, "Year:", 0, 1);
	    
	    ComboBox<String> years = mkComboBox(Years);
	    years.setMinSize(220, 40);
	    years.setValue(Years[0]);
	    grid.add(years, 1, 1);
	    
	    Text odometerQuestion = new Text("Odometer:");
	    odometerQuestion.setFont(Font.font("Calibri", 20));
	    grid.add(odometerQuestion, 0, 2);
	    
	    // makes the radiobtns for the odometer
	    RadioButton[] odoBtns = mkMultipleChoice(4, odometerChoice);
	    // selects first radiobtn
	    odoBtns[0].setSelected(true);
	    // loops through and sets each radiobtns txt to black, insert user data according to the position of the radiobtn
	    // and sets the toggle group to odo so we know they are all related
	    for (int i=0; i<4; i++){
	    	odoBtns[i].setTextFill(Color.BLACK);
	    	odoBtns[i].setUserData(i+1);
	    	odoBtns[i].setToggleGroup(odo);
	    	grid.add(odoBtns[i], 0, (3+i));
	    }
	    // whenever any radio button in the group odo is clicked it calls this listener and this listener updates the value
	    // of odoometerNum to the position of the currently selected radiobtn
	    odo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	    	public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

	            if (odo.getSelectedToggle() != null) {
	            	odometerNum = (int) odo.getSelectedToggle().getUserData();

	            }

	        }
	    });
	    
	    CheckBox isStock = new CheckBox(stock);
	    grid.add(isStock, 1, 3);
	    
	    Text maintainQ = mkLabel(grid, "Oil change frequency", 3, 2);
	    		new Text("Oil change frequency:");
	    maintainQ.setFont(Font.font("Calibri", 20));
	    //grid.add(maintainQ, 3, 2);
	    
	    // makes the oil change frequency radiobtns
	    RadioButton[] maintainBtns = mkMultipleChoice(5, maintain);
	    // selects the first radiobtn in maintainBtns
	    maintainBtns[0].setSelected(true);
	    // loops through maintainBtns radio buttons and sets the text color to black, set there user data to there
	    // position, and adds it to the oilChange group
	    for (int i=0; i<5; i++){
	    	maintainBtns[i].setTextFill(Color.BLACK);
	    	maintainBtns[i].setUserData(i+1);
	    	maintainBtns[i].setToggleGroup(oilChange);
	    	grid.add(maintainBtns[i], 3, (3+i));
	    }
	    // if any radiobtn in the group oilChange is selected it calls this function which updates the value if
	    // oilChangeFreq to the position of the currently selected radiobtn
	    oilChange.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	    	public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

	            if (oilChange.getSelectedToggle() != null) {
	            	oilChangeFreq = (int) oilChange.getSelectedToggle().getUserData();

	            }

	        }
	    });
	    
	    Button btn = new Button("Calculate");
	    // for now when calculate button is clicked it prints the data out to the console
	    btn.setOnAction(new EventHandler<ActionEvent>(){

	    	@Override
			public void handle(ActionEvent event) {
				try {
					int hp = 0, wt = 0, state = 0;
					boolean execute = true;
					textboxWT.setStyle("");
					torqueTB.setStyle("");
					textboxHP.setStyle("");
					if(textboxHP.getText().isEmpty() || !isNumber(textboxHP.getText())){
						textboxHP.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
						execute = false;
					}
					else{
						hp = Integer.parseInt(textboxHP.getText());
					}
					if (isStock.selectedProperty().getValue()){
						state = 1;
					}
					if(torqueTB.getText().isEmpty() || !isNumber(torqueTB.getText())){
						torqueTB.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
						execute = false;
					}
					else{
						Integer.parseInt(torqueTB.getText());
					}
					if(textboxWT.getText().isEmpty() || !isNumber(textboxWT.getText())){
						textboxWT.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
						execute = false;
					}
					else{
						wt = Integer.parseInt(textboxWT.getText());
					}
					if(execute){
						int yr = Integer.parseInt(years.getValue());
						double quartMl = Math.round (backend.quarterMile(hp, wt, (oilChangeFreq - 1), (odometerNum - 1), state, yr) * 10000.0) / 10000.0;
						double zToSixty = Math.round(backend.zeroToSixty(hp, wt, (oilChangeFreq - 1), (odometerNum - 1), state, yr) * 10000.0) / 10000.0;
						quarterMile.setText(quartMl +" s");
						zeroSixty.setText(zToSixty + " s");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	    grid.add(btn, 0, 10);
	    
	    Label zToS = new Label("Zero - Sixty Time:");
	    Label QM = new Label("Quarter Mile Time:");
	    zToS.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	    QM.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	    
	    grid.add(zToS, 1, 16);
	    grid.add(QM, 1, 17);
	    
	    
	    zeroSixty.setTextFill(Color.web("#E46A6B"));
	    zeroSixty.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
	    quarterMile.setTextFill(Color.web("#E46A6B"));
	    quarterMile.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
	    grid.add(zeroSixty, 2, 16);
	    grid.add(quarterMile, 2, 17);
	    
	    grid.setAlignment(Pos.BASELINE_CENTER);
	    grid.setStyle("-fx-background-color: #FDFEFE ;");
	    
	    return grid;
	}
	
	public Text mkLabel(GridPane grid, String label, int col, int row){
		 Text txt = new Text(label);
		 txt.setFont(Font.font("Calibri", 20));
		 grid.add(txt, col, row);
		 return txt;
	}
	
	public static boolean isNumber(String string)
	{
		int dotCounter = 0;
	    for (char c : string.toCharArray())
	    {
	    	if (c == '.'){
	    		dotCounter++;
	    	}
	        if ((!Character.isDigit(c) && c != '.') || dotCounter > 1){
	        	return false;
	        }
	    }
	    return true;
	}

	
	private HBox mkTitle() {
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #306EFF ;");
	
	    Label label = new Label(title);
	    label.setFont(Font.font("Calibri",FontWeight.BOLD, 50));
	    label.setTextFill(Color.WHITE);
	    
	    
	    hbox.getChildren().addAll(label);
	    hbox.setAlignment(Pos.CENTER);
	
	    return hbox;
	}
	
	private String[] copy(ArrayList<Integer> years) {
		String[] Years = new String[years.size()];
		for(int i=0; i<years.size(); i++){
			Years[i] = years.get(i).toString();
		}
		return Years;
	}
	
	private ArrayList<Integer> getYears() {
		ArrayList<Integer> years = new ArrayList<Integer>();
		for (int i = Calendar.getInstance().getWeekYear(); i >= 1950; i--) {
			years.add(i);
		}
		return years;
	}
	
		//DESCRIPTION: adds multiple choice questions to scene
		//INPUTS: a layout, a question to ask, number of options to choose 
		//		  from, a text for each button (has to match number of buttons)
		//RETURN: None
		public RadioButton[] mkMultipleChoice(int numButtons, String... args)
		{
			
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
			}
			return btn;
		}
		
		public TextField mkWrittenText()
		{
			TextField text = new TextField();
			return text;
		}
		
		public static ComboBox<String> mkComboBox(String... args){
			ComboBox<String> combobox = new ComboBox<String>();
			for (String arg : args)
			{
				combobox.getItems().addAll(arg);
			}
			return combobox;
		}
		
		public static void main(String[] args){
			new CarTimeCalculator();
		javafx.application.Application.launch(CarTimeCalculator.class);
	}

}
