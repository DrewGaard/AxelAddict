import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import org.json.JSONException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PersonalityResultsWindow {

	private int topRes = 5;
	private Stage mainStage;
	int tempLastYear;
	
	public void start(Stage primaryStage, LinkedList<KeywordPair> keywords) throws Exception {
		this.mainStage = primaryStage;
		GridPane grid = new GridPane();
		Scene scene;
		BorderPane border = new BorderPane();
		grid.setHgap(15);
		grid.setVgap(10);
	    grid.setPadding(new Insets(20, 20, 50, 10));
	    grid.setStyle("-fx-background-color: #FDFEFE ;");
	    
	    HBox hbox = mkTitle();
	    primaryStage.setOnCloseRequest(confirmCloseEventHandler);
	     
	    border.setTop(hbox);
	    //make the center content with the cars that are going to be displayed
	    border.setCenter(new Label("Please wait.... Content Loading"));
	    scene = new Scene(border, 750, 600);
	    primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	    
	    ScrollPane sp = new ScrollPane(); 
	  	sp.setContent(showCarResults(grid, keywords, primaryStage));	
	  	sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	  	sp.setHbarPolicy(ScrollBarPolicy.NEVER);
	  	sp.setFitToWidth(true);
	  	sp.setFitToHeight(true);
	  	border.setCenter(sp);

		
	}
	private ArrayList<CarMatches> getCarMatches(LinkedList<KeywordPair> keywords, Stage primaryStage) {
		GoogleAPI googleSearch = new GoogleAPI("");
		String totalGoogleText = "";
			for(int i = 0;i<keywords.size();i++){
			if (i+4 < keywords.size()){
				
				googleSearch.userWordSearch("best "+keywords.get(i).getName() +", "+ keywords.get(i+1).getName()+", "+keywords.get(i+2).getName()+", "+ keywords.get(i+3).getName()+", and "+keywords.get(i+4).getName()+ " " + keywords.get(i).getCategory() + ", "+ keywords.get(i+1).getCategory() + ", "+keywords.get(i+2).getCategory()+", "+keywords.get(i+3).getCategory()+", and "+keywords.get(i+4).getCategory());
				totalGoogleText = totalGoogleText + googleSearch.getDescriptions();
				i += 4;
			} else if (i+3 < keywords.size()){
				googleSearch.userWordSearch("best "+keywords.get(i).getName() +", "+ keywords.get(i+1).getName()+", "+keywords.get(i+2).getName()+", and "+ keywords.get(i+3).getName()+ " " + keywords.get(i).getCategory() + ", "+ keywords.get(i+1).getCategory() + ", "+keywords.get(i+2).getCategory()+", or "+keywords.get(i+3).getCategory());
				totalGoogleText = totalGoogleText + googleSearch.getDescriptions();
				i += 3;
			} else if (i+2 < keywords.size()) {

				googleSearch.userWordSearch("best "+keywords.get(i).getName() +", "+ keywords.get(i+1).getName()+", and "+keywords.get(i+2).getName()+" " + keywords.get(i).getCategory() + ", "+ keywords.get(i+1).getCategory() + ", or "+keywords.get(i+2).getCategory());
				totalGoogleText = totalGoogleText + googleSearch.getDescriptions();
				i += 2;
			}else if (i+1 < keywords.size()) {

				googleSearch.userWordSearch("best "+keywords.get(i).getName() +" and "+ keywords.get(i+1).getName()+" " + keywords.get(i).getCategory() + " or "+ keywords.get(i+1).getCategory());
				totalGoogleText = totalGoogleText + googleSearch.getDescriptions();
				i += 1;
			}
			else {
				googleSearch.userWordSearch("best "+keywords.get(i).getName() + ", " + keywords.get(i).getCategory());
				totalGoogleText = totalGoogleText + googleSearch.getDescriptions();
			}
		}
		Algorithms algo = new Algorithms("");
		ArrayList<CarMatches> carMatches = algo.matchCarsWithGoogle(totalGoogleText);
		CarAPI getModels = new CarAPI();
		ArrayList<CarMatches> carMatch = new ArrayList<CarMatches>();
		for (int i = 0;i<carMatches.size(); i++) {
			if (carMatches.get(i).getOccurence() != 0) {
				ArrayList<String> modelsForMake = new ArrayList<String>();
				try {
					modelsForMake = getModels.getCarModelsFromMake(carMatches.get(i).getMake());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				for (int b =0;b<modelsForMake.size();b++){
					String temp = modelsForMake.get(b);
					algo.setPattern(temp);
					int modelOcc = algo.NumOccurences(totalGoogleText, temp);
					if (modelOcc > 0) {
						CarMatches temp1 = new CarMatches();
						temp1.setMake(carMatches.get(i).getMake());
						temp1.setOccurence(Math.min(carMatches.get(i).getOccurence(), modelOcc));
						temp1.setModel(temp);
						carMatch.add(temp1);
					}
				}
			}
		}
		return carMatch;
	}
	
	private ArrayList<Text> mkLabels(ArrayList<CarMatches> matches) {
		ArrayList<Text> text = new ArrayList<Text>();
		Algorithms algorithm = new Algorithms("");
		ArrayList<CarMatches> topMatches = algorithm.topCars(matches, topRes);
		for (int i = 0;i<topMatches.size();i++){
			
			CarAPI results = new CarAPI();
			
			try {
				tempLastYear = results.getLastYear(topMatches.get(i).getMake(), topMatches.get(i).getModel());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			results.setCarToSearch(topMatches.get(i).getMake(), topMatches.get(i).getModel(), tempLastYear);
			
			
			String baseVal = "$ "+results.tester.get(0).getBaseValue();
			String resaleVal = "$ "+results.tester.get(0).getResaleValue();
			String displaceMent = results.tester.get(0).getDisplacement() +" cubic centimeters";
			String driveT = results.tester.get(0).getDriveTrain();
			String engineType = results.tester.get(0).getEngineType();
			String cylinderCount = results.tester.get(0).getCylinder()+"";
			String gasType = results.tester.get(0).getGasType();
			String hp = results.tester.get(0).getHorsepower()+" HP";
			String mpgC = results.tester.get(0).getMPGCity()+" MPG";
			String mpgH = results.tester.get(0).getMPGHighway()+" MPG";
			String pkHP = results.tester.get(0).getPeakHP()+" RPM";
			String pkTQ = results.tester.get(0).getPeakTQ()+" RPM";
			String TQ = results.tester.get(0).getTorque()+" ft lb";
			String NA = "N/A";
			if(results.tester.get(0).getBaseValue() == 0) baseVal = "Price Not Listed";
			if(results.tester.get(0).getResaleValue() == 0) resaleVal = "Price Not Listed";
			if(results.tester.get(0).getDisplacement() == 0) displaceMent = NA;
			if(results.tester.get(0).getDriveTrain() == "") driveT = NA;
			if(results.tester.get(0).getEngineType() == "") engineType = NA;
			if(results.tester.get(0).getCylinder() == 0) cylinderCount = NA;
			if(results.tester.get(0).getGasType() == "") gasType = NA;
			if(results.tester.get(0).getHorsepower() == 0) hp = NA;
			if(results.tester.get(0).getMPGCity()==0) mpgC = NA;
			if(results.tester.get(0).getMPGHighway()==0) mpgH = NA;
			if(results.tester.get(0).getPeakHP()==0) pkHP = NA;
			if(results.tester.get(0).getPeakTQ()==0) pkTQ = NA;
			if(results.tester.get(0).getTorque() == 0) TQ = NA;
			
			Text temp = new Text((i+1)+"). "+topMatches.get(i).getMake().toUpperCase() + " " + topMatches.get(i).getModel().toUpperCase() + 
			"\n"  + "___________________________________________________________________" + "\n" + "Base Value: " + baseVal + "\n" + 
			"Displacement: " + displaceMent + "\n" + "Drive Train: " + driveT + "\n" + 
			"Engine Type: " + engineType + cylinderCount + "\n" +"Gas Type: " + gasType + "\n" + 
			"Horespower: " + hp + "\n" + "City MPG: " + mpgC + "\n" + "Highway MPG: " + mpgH + "\n" + 
			"Peak HP: " + pkHP + "\n" + "Peak TQ: " + pkTQ + "\n" + "Resale Value: " + resaleVal + "\n" + 
			"Torque: " + TQ + "\n" + "___________________________________________________________________");
			text.add(temp);
		}
		return text;
	}

	private Node showCarResults(GridPane grid, LinkedList<KeywordPair> keywords, Stage primaryStage) {
		ArrayList<Text> matches = mkLabels(getCarMatches(keywords,primaryStage));
		grid.add(new Label("Showing Top "+topRes+ " results from personality match"), 1,0);
		for (int i = 0;i<matches.size();i++){
			Button expandButton = new Button("Expand");
			Label temp = new Label((i+1)+".");
			Label carSpecs = new Label("This is a temporary string.");
			carSpecs.setVisible(false);
			
			expandButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					carSpecs.setVisible(true);
				}
			});
			
			
			grid.add(matches.get(i), 1, i+1);
		}
		return grid;
	}

	private HBox mkTitle() {
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(15);
	    hbox.setStyle("-fx-background-color: #306EFF ;");//background color
	    
	    //title and title font settings
	    Label label = new Label("Personality Results");
	    label.setFont(Font.font("Arial",FontWeight.BOLD, 50));
	    label.setTextFill(Color.WHITE);
	    
	    //add title
	    hbox.getChildren().addAll(label);
	    hbox.setAlignment(Pos.CENTER);
	
	    return hbox;
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

}
