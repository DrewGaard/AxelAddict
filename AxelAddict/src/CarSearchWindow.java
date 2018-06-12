import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.json.JSONException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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


public class CarSearchWindow extends Application implements ChangeListener{

	private Scene scene;
	private Stage mainStage;
	JavaUIHelper helper = new JavaUIHelper();
	private int HEIGHT = 600;
	private int WIDTH = 800;
	private ArrayList<Integer> years = new ArrayList<Integer>(getYears());
	private String[] Years = copy(years);
	BorderPane border = new BorderPane(); 
	Button backButton = new Button("Back");
	String makeVal = "Aston Martin";
	Text makeModResults = new Text("");
	int iter;
	Text trimNameText = new Text();
	String carImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Picture_icon_BLACK.svg/2000px-Picture_icon_BLACK.svg.png";
	Image imageCar = new Image(carImage);
	ImageView image = new ImageView();
	ProgressBar pb = new ProgressBar(0);
	ProgressIndicator pi = new ProgressIndicator();
	double progress = 0;
	String picString;
	Text largerPic = new Text();
	
	String imageMake;
	String imageModel;
	int imageYear;
	
	
	
	
	GridPane grid = new GridPane();

	final ProgressBar bar = new ProgressBar();
	
	private ArrayList<String> trims = new ArrayList<>();
	private ArrayList<String> info = new ArrayList<>();
	
	private ArrayList<Label> trimLabel = new ArrayList<Label>();
	private ArrayList<Label> infoLabel = new ArrayList<Label>();
	
	int curYear = 0;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.mainStage = primaryStage;
		scene = new Scene(border, WIDTH, HEIGHT);
		HBox hbox = mkTitle();
		border.setTop(hbox);
		primaryStage.setResizable(false);
		
		
		Line divider = new Line(0,450,WIDTH,450);
		divider.setFill(Color.BLUE);
		border.getChildren().addAll(divider);
		
		Separator separator1 = new Separator();
        separator1.setPrefSize(WIDTH, 0);
        border.getChildren().addAll(separator1);
		
		primaryStage.centerOnScreen();
	  //Add window scroll functionality
		primaryStage.setOnCloseRequest(confirmCloseEventHandler);
		primaryStage.setTitle("Car Search");
		primaryStage.setResizable(false);
	  	ScrollPane sp = new ScrollPane(); 
	  	sp.setContent(mkBody());	
	  	sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	  	sp.setHbarPolicy(ScrollBarPolicy.NEVER);
	  	sp.setFitToWidth(true);
	  	sp.setFitToHeight(true);
	  		border.setCenter(sp);
	  		//Scroll scene
	//  	Scene scene1 = new Scene(sp, 770, 600);	//SCROLLBAR scene with layout parameters
	  	
	  //main window class object
	    MainWindow main = new MainWindow();
		
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
		primaryStage.setScene(scene);
	 // 	primaryStage.setScene(scene1); 
	  	primaryStage.show();
	}
	
	private GridPane mkBody() throws IOException {
		CarAPI tester = new CarAPI();
		//GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(10);
	    grid.setPadding(new Insets(20, 20, 50, 10));
	    
	    
	    Text year = new Text("Year:");
	    year.setFont(Font.font("Calibri", 20));
	    year.applyCss();
	    grid.add(year, 3, 0);
	    year.setFill(Color.BLACK);

	    
	    new Text("                                                ");
	    year.setFont(Font.font("Calibri", 20));
	   // grid.add(space, 0, 0);
	    
	    ComboBox<String> years = mkComboBox(Years);
	    years.setMinSize(100, 30);
	    years.setMaxSize(100, 30);
	    years.setValue(Years[0]);
	    grid.add(years, 4, 0);
	   // years.setStyle("-fx-background-color: #0099cc");
	    
	    
	    Text make = new Text("Make:");
	    make.setFont(Font.font("Calibri", 20));
	    make.setFill(Color.BLACK);
	    grid.add(make, 1, 0);
	    
	    
	    String allMakes = "am-general,acura,alfa-romeo,aston-martin,audi,bmw,bentley,bugatti,buick,cadillac,chevrolet,chrysler,daewoo,dodge,eagle,fiat,ferrari,fisker,ford,gmc,genesis,geo,hummer,honda,hyundai,infiniti,isuzu,jaguar,jeep,kia,lamborghini,land-rover,lexus,lincoln,lotus,mini,maserati,maybach,mazda,mclaren,mercedes-benz,mercury,mitsubishi,nissan,oldsmobile,panoz,plymouth,pontiac,porsche,ram,rolls-royce,saab,saturn,scion,spyker,subaru,suzuki,tesla,toyota,volkswagen,volvo,smart";
	    
	    ArrayList<String> makesArray =  new  ArrayList<String>(Arrays.asList(allMakes.split(",")));
	    
	    final ComboBox<String> makeComboBox = new ComboBox<String>();
	    ArrayList<String> prettyString = helper.prettifyArrayList(makesArray);
        makeComboBox.getItems().addAll(prettyString);   
        
        //grid.add(bar, 7, 4);
        
        Line divider = new Line(0,450,WIDTH - 25,450);
		divider.setFill(Color.BLUE);
		grid.add(divider, 0, 4);
        
        

        makeComboBox.setValue("");
        makeComboBox.setMinSize(150, 30);
        makeComboBox.setMaxSize(150, 30);
        makeComboBox.setValue("Am-General");
       // makeComboBox.setStyle("-fx-background-color: #0099cc");
        grid.add(makeComboBox, 2, 0);
        
        final ComboBox<String> modelComboBox = new ComboBox<String>();
	    grid.add(modelComboBox, 6, 0);
	    modelComboBox.setMinSize(150, 30);
        modelComboBox.setMaxSize(150, 30);
       // modelComboBox.setStyle("-fx-background-color: #0099cc");
        
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        now.get(Calendar.YEAR);  
	    
        
        makeComboBox.setOnAction((event) -> {
        	makeVal = makeComboBox.getSelectionModel().getSelectedItem();
            makeVal = makeVal.replaceAll("\\s","%20"); //We need to remove all the spaces in the make string or else we get an error
            String passableMake = makeVal.toLowerCase();
            curYear = Integer.parseInt(years.getSelectionModel().getSelectedItem());
            
            modelComboBox.getItems().clear();
            
            	
				
				try {
					tester.getModelFromYearMake(passableMake, curYear);
					modelComboBox.getItems().clear();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					modelComboBox.getItems().clear();
					e.printStackTrace();
				}
				
				ArrayList<String> prettyStringModels = helper.prettifyArrayList(tester.getCarModels()); 
                modelComboBox.getItems().addAll(prettyStringModels);
			
                
        	
        
        	
        });
        
        
        years.setOnAction((event) -> {
        	
        	makeVal = makeComboBox.getSelectionModel().getSelectedItem();
            makeVal = makeVal.replaceAll("\\s",""); //We need to remove all the spaces in the make string or else we get an error
            String passableMake = makeVal.toLowerCase();
            curYear = Integer.parseInt(years.getSelectionModel().getSelectedItem());
            modelComboBox.getItems().clear();
        	
            
            try {
				tester.getModelFromYearMake(passableMake, curYear);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<String> prettyStringModels = helper.prettifyArrayList(tester.getCarModels()); 
            modelComboBox.getItems().addAll(prettyStringModels);
        	/*
            if(makeVal != null && curYear != 0) //Make sure year is greater than 0 and there is a date in the text box meaning the length is greater than 3
            {
            	makeVal = makeComboBox.getSelectionModel().getSelectedItem();
                makeVal = makeVal.replaceAll("\\s",""); //We need to remove all the spaces in the make string or else we get an error
                String passableMake = makeVal.toLowerCase();
                curYear = Integer.parseInt(years.getSelectionModel().getSelectedItem());
                modelComboBox.getItems().clear();
            	//tester.setCarModelsFromMakeYear(passableMake, curYear);
                
                try {
					tester.getModelFromYearMake(passableMake, curYear);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            	ArrayList<String> prettyStringModels = helper.prettifyArrayList(tester.getCarModels()); 
                modelComboBox.getItems().addAll(prettyStringModels);
            }
            */
        });
        
	    Text model = new Text("Model:");
	    model.setFont(Font.font("Calibri", 20));
	    model.setFill(Color.BLACK);
	    grid.add(model, 5, 0);
	    
	 
	    //-------Search Button----------------------
		Button searchBtn = new Button("Search");
		searchBtn.setPrefHeight(30);
		searchBtn.setPrefWidth(70);
		searchBtn.setMaxHeight(30);
		searchBtn.setMinHeight(30);
		searchBtn.setMaxWidth(70);
		searchBtn.setMinWidth(70);
		searchBtn.setFont(Font.font("Calibri", 15));
		//searchBtn.setStyle("-fx-background-color: #0099cc");
		
		
		makeModResults.setFont(Font.font("Calibri", 20));
		grid.add(makeModResults, 0, 5);
		
		
		int column2 = 0;
	    int row2 = 9;
		grid.add(trimNameText, column2, row2 - 1);
		
		 // for now when calculate button is clicked it prints the data out to the console
	    searchBtn.setOnAction(new EventHandler<ActionEvent>(){

	    	@Override
			public void handle(ActionEvent event) {
	    		
	    		
	    	if(makeComboBox.getValue().isEmpty())
	    	{
	    		makeComboBox.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
	    	}
	    	
	    		
	    	if(makeComboBox.getValue() != null && modelComboBox.getValue() != null)
	    	{
				try {
					year.setStyle("");
					//textboxYear.setStyle("");
					//modelComboBox.setStyle("");
					//makeComboBox.setStyle("");
					//minBox.setStyle("");
					//maxBox.setStyle("");
					
					
					
					
					grid.getChildren().remove(image);
					grid.getChildren().remove(largerPic);
					

					makeModResults.setText("                                                                                 " + makeComboBox.getValue()+ " " + modelComboBox.getValue());
					makeModResults.setFill(Color.BLACK);
				
					
					
					
					
					String passableMake = makeComboBox.getSelectionModel().getSelectedItem();
		            passableMake = passableMake.replaceAll("\\s","%20"); //We need to remove all the spaces in the make string or else we get an error
		            passableMake = passableMake.toLowerCase();
		            
		            imageMake = passableMake;
		            
		            
		            String passableModel = modelComboBox.getSelectionModel().getSelectedItem();
		            passableModel = passableModel.replaceAll("\\s","%20"); //We need to remove all the spaces in the make string or else we get an error
		            passableModel = passableModel.toLowerCase();
		            
		            imageModel = passableModel;
				   
		        	CarAPI results = new CarAPI();
					results.setCarToSearch(passableMake, passableModel, Integer.parseInt(years.getValue()));
					
					imageYear = Integer.parseInt(years.getValue());
					
					ArrayList<Text> trimNames = new ArrayList<Text>();
					
					picString = "                                                                                 No Image";
					
					
					try{
						carImage = results.getCarImages(makeComboBox.getValue(), passableModel, Integer.parseInt(years.getValue()));
						grid.getChildren().remove(image);
						
						Image searchImg = new Image(carImage);
						
						image.setImage(searchImg);
						
						picString  = "                                                                           Click image to enlarge";
						
						image.setFitHeight(150);
						image.setFitWidth(125);
						image.setPreserveRatio(true);
						image.setOnMouseClicked(e -> {
  							imagePopupWindowShow();
  				        });
						
						grid.add(image, 4, 6);
					
					}catch(Exception ex){}
  						

 
					
					
			
					
					
					largerPic.setText(picString);
					largerPic.setFont(Font.font("Calibri", 20));
					grid.add(largerPic, 0, 7);
					
					
					
					
					
					String curTextStr = " ";
					
					for(int i = 0; i < results.tester.size(); i++)
					{
						//Text curText = new Text(results.tester.get(i).getTrim());
						String trimNameDisplay  = results.tester.get(i).getTrim();
						trimNameDisplay.toUpperCase();
						trimNameText.setText(trimNameDisplay);
						//trimNameText.setFont(trimNameFont);
						
						
						Text curText = new Text("Trim: " + results.tester.get(i).getTrim() + "\n" + "Base Value: " + results.tester.get(i).getBaseValue() + "\n" + "Displacement: " + results.tester.get(i).getDisplacement() + "\n" + "Drive Train: " + results.tester.get(i).getDriveTrain() + "\n" + "Engine Type: " + results.tester.get(i).getEngineType() + "\n" +"Gas Type: " + results.tester.get(i).getGasType() + "\n" + "Horespower: " + results.tester.get(i).getHorsepower() + "\n" + "City MPG: " + results.tester.get(i).getMPGCity() + "\n" + "Highway MPG: " + results.tester.get(i).getMPGHighway() + "\n" + "Peak HP: " + results.tester.get(i).getPeakHP() + "\n" + "Peak TQ: " + results.tester.get(i).getPeakTQ() + "\n" + "Resale Value: " + results.tester.get(i).getResaleValue() + "\n" + "Torque: " + results.tester.get(i).getTorque());
						String baseVal = "$ "+results.tester.get(i).getBaseValue();
						String resaleVal = "$ "+results.tester.get(i).getResaleValue();
						String displaceMent = results.tester.get(i).getDisplacement() +" cubic centimeters";
						String driveT = results.tester.get(i).getDriveTrain();
						String engineType = results.tester.get(i).getEngineType();
						String cylinderCount = results.tester.get(i).getCylinder()+"";
						String gasType = results.tester.get(i).getGasType();
						String hp = results.tester.get(i).getHorsepower()+" HP";
						String mpgC = results.tester.get(i).getMPGCity()+" MPG";
						String mpgH = results.tester.get(i).getMPGHighway()+" MPG";
						String pkHP = results.tester.get(i).getPeakHP()+" RPM";
						String pkTQ = results.tester.get(i).getPeakTQ()+" RPM";
						String TQ = results.tester.get(i).getTorque()+" ft lb";
						String NA = "N/A";
						if(results.tester.get(i).getBaseValue() == 0) baseVal = "Price Not Listed";
						if(results.tester.get(i).getResaleValue() == 0) resaleVal = "Price Not Listed";
						if(results.tester.get(i).getDisplacement() == 0) displaceMent = NA;
						if(results.tester.get(i).getDriveTrain() == "") driveT = NA;
						if(results.tester.get(i).getEngineType() == "") engineType = NA;
						if(results.tester.get(i).getCylinder() == 0) cylinderCount = NA;
						if(results.tester.get(i).getGasType() == "") gasType = NA;
						if(results.tester.get(i).getHorsepower() == 0) hp = NA;
						if(results.tester.get(i).getMPGCity()==0) mpgC = NA;
						if(results.tester.get(i).getMPGHighway()==0) mpgH = NA;
						if(results.tester.get(i).getPeakHP()==0) pkHP = NA;
						if(results.tester.get(i).getPeakTQ()==0) pkTQ = NA;
						if(results.tester.get(i).getTorque() == 0) TQ = NA;
						curTextStr = curTextStr + "\n" + "\n"  + ("Trim: " + results.tester.get(i).getTrim() + "\n" + 
						"______________________________________________________" + "\n" + "Base Value: " + baseVal + "\n" + 
						"Displacement: " + displaceMent + "\n" + "Drive Train: " + driveT + "\n" + 
						"Engine Type: " + engineType + cylinderCount + "\n" +"Gas Type: " + gasType + "\n" + 
						"Horespower: " + hp + "\n" + "City MPG: " + mpgC + "\n" + "Highway MPG: " + mpgH + "\n" + 
						"Peak HP: " + pkHP + "\n" + "Peak TQ: " + pkTQ + "\n" + "Resale Value: " + resaleVal + "\n" + 
						"Torque: " + TQ + "\n" + "______________________________________________________");

		
						trims.add(results.tester.get(i).getTrim());
						info.add("\n" + "______________________________________________________" + "\n" + "Base Value: " + results.tester.get(i).getBaseValue() + "\n" + "Displacement: " + results.tester.get(i).getDisplacement() + "\n" + "Drive Train: " + results.tester.get(i).getDriveTrain() + "\n" + "Engine Type: " + results.tester.get(i).getEngineType() + results.tester.get(i).getCylinder() + "\n" +"Gas Type: " + results.tester.get(i).getGasType() + "\n" + "Horespower: " + results.tester.get(i).getHorsepower() + "\n" + "City MPG: " + results.tester.get(i).getMPGCity() + "\n" + "Highway MPG: " + results.tester.get(i).getMPGHighway() + "\n" + "Peak HP: " + results.tester.get(i).getPeakHP() + "\n" + "Peak TQ: " + results.tester.get(i).getPeakTQ() + "\n" + "Resale Value: " + results.tester.get(i).getResaleValue() + "\n" + "Torque: " + results.tester.get(i).getTorque() + "\n" + "___________________________________________");
						
						
						Label temp = new Label(trims.get(i));
						Label temp2 = new Label(info.get(i));
						trimLabel.add(temp);
						infoLabel.add(temp2);
						
						//trimLabel.get(i).setText(trims.get(i));
						//infoLabel.get(i).setText(info.get(i));
						
						
						trimNames.add(curText);
						new Text("Base Value: " + results.tester.get(i).getBaseValue());
					}

					
					
				    
				    
					    String trimNameDisplay = "";
				    for(int i= 0; i < trimNames.size(); i++)
					{
						trimNameDisplay  = curTextStr;
					}
				    
				    
				  	trimNameDisplay.toUpperCase();
					trimNameText.setText(trimNameDisplay);
					trimNameText.setFont(Font.font ("Calibri", 20));
					trimNameText.setFill(Color.BLACK);
					

					
					if(trimNames.size() == 0)
					{
						makeModResults.setText("No Results");
						makeModResults.setFill(Color.BLACK);
					}
					
					
					for(iter = 0; iter < trimNames.size(); iter++)
					{
						trimNames.get(iter).setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setTitle(results.tester.get(iter).getTrim());
									alert.setHeaderText(null);
									alert.setContentText("Trim: " + results.tester.get(iter).getTrim());
									alert.showAndWait();
							}
						});
					

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
	    	}
			
		});
		
		grid.add(searchBtn, 4, 3);
		//------------------------------------------
		
		grid.setStyle("-fx-background-color: #FDFEFE ;");
	    grid.setAlignment(Pos.BASELINE_CENTER);
	    
	    grid.getColumnConstraints().add(new ColumnConstraints(60));
	    grid.getRowConstraints().add(new RowConstraints(60));
	    grid.setAlignment(Pos.TOP_LEFT);
	    
	    
		return grid;
	}
	
	private HBox mkTitle() {
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #306EFF ;");
	    backButton.setTranslateX(-252);
	    backButton.setTranslateY(28.8);
		backButton.setPrefHeight(30);
		backButton.setPrefWidth(50);
		backButton.setFont(Font.font("Calibri",FontWeight.BOLD,15));
		hbox.getChildren().addAll(backButton);
	 	
		Label label = new Label("Car Search");
	    label.setTextFill(Color.WHITE);
	    label.setFont(Font.font("Calibri",FontWeight.BOLD, 50));
	   //label.setTextFill(Color.GREY);
	    
	    
	    hbox.getChildren().addAll(label);
	    hbox.setAlignment(Pos.CENTER);
	
	    return hbox;
	}
	
	public static void main(String[] args){
		new CarSearchWindow();
		javafx.application.Application.launch(CarSearchWindow.class);
	}
	
	public TextField mkWrittenText()
	{
		TextField text = new TextField();
		return text;
	}
	
	//----Validation for number from CarTimeCalculator Class-----------
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


	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
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
		for (int i = Calendar.getInstance().getWeekYear(); i >= 1980; i--) {
			years.add(i);
		}
		return years;
	}
	
	public static ComboBox<String> mkComboBox(String... args){
		ComboBox<String> combobox = new ComboBox<String>();
		for (String arg : args)
		{
			combobox.getItems().addAll(arg);
		}
		return combobox;
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
	
	   public void imagePopupWindowShow() {
	        Image image;
	        ImageView imageView;
	        BorderPane pane;
	        Scene scene;
	        Stage stage;
	        CarAPI results = new CarAPI();
	        
	        String carImage = "http://fresherandprosper.com/usercontent/itempageimages/default/noresults.jpg";
	        
	        try{
				carImage = results.getCarImages(imageMake, imageModel, imageYear);
			}catch(Exception ex){}
	        
	        
	        image = new Image(carImage);      
	        imageView = new ImageView(image);
	        imageView.maxHeight(600);
	        imageView.prefHeight(800);
	        imageView.prefWidth(800);
	        imageView.minHeight(800);
	        imageView.minWidth(600);
	        imageView.maxWidth(800);


	        // Our image will sit in the middle of our popup.
	        pane = new BorderPane();
	        
	        imageView.fitWidthProperty().bind(pane.widthProperty());
	        imageView.fitHeightProperty().bind(pane.heightProperty());
	        pane.setCenter(imageView);
	        scene = new Scene(pane, 800, 600);
	        
	        stage = new Stage();
	        stage.setTitle("Car Search Image");
			stage.setResizable(false);
	        stage.setScene(scene);
	        stage.setOnCloseRequest(
	            e -> {
	                e.consume();
	                stage.close();
	            }
	        );
	        stage.showAndWait();
	    }
}	