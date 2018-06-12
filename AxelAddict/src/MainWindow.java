import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

public class MainWindow extends Application {

	//Variables
	private Scene scene;
	int WIDTH = 500, HEIGHT = 500;
	Button personalityButton = new Button("Personality Test");
	Button carSearchButton = new Button("Car Search");
	Button calculationsButton = new Button("Car Calculations");
	Text welcome = new Text("Welcome to \nAxle Addict"); //welcome message
	private Stage mainStage;
	/*Start function is what runs the window. From here, everything
	 * gets added on the window. Event handlers for buttons are handled
	 * here.*/
	@Override
	public void start(Stage window) throws Exception {
		this.mainStage = window;
		//Class objects
		Personality perso = new Personality(); 
		CarTimeCalculator calculator = new CarTimeCalculator();
		CarSearchWindow carSearch = new CarSearchWindow();
		
		//Setting up the window
		VBox layout = new VBox(20);
		scene = new Scene(layout, WIDTH, HEIGHT);
		window.setResizable(false);
		layout.setPadding(new Insets(10));
	    layout.setSpacing(20);
	    layout.setAlignment(Pos.TOP_CENTER);
		
		//Button settings
		personalityButton.setMinHeight(60);
		personalityButton.setPrefWidth(250);
		personalityButton.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
		carSearchButton.setMinHeight(60);
		carSearchButton.setPrefWidth(250);
		carSearchButton.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
		calculationsButton.setMinHeight(60);
		calculationsButton.setPrefWidth(250);
		calculationsButton.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
		welcome.setFont(Font.font("Calibri", FontWeight.BOLD, 85));
		welcome.setFill(Color.WHITE);
		
	
		//Personality Button action to go to personality class
		personalityButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try {
					perso.start(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		//Calculation button action to go to carTimeCalculator class
		calculationsButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try{
					calculator.start(window);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
		});
		
		//Car Search button action to go to CarSearchWindow class
		carSearchButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try{
					carSearch.start(window);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
		});
		
		window.setOnCloseRequest(confirmCloseEventHandler); //Exit alert confirmation 

		//Add everything to window
		layout.getChildren().addAll(welcome, personalityButton, carSearchButton, calculationsButton); 
		window.setTitle("Axle Addict");
		layout.setStyle("-fx-background-color: #306EFF ;");
		window.setScene(scene);

		window.show();
		
	}
	
	//Exit confirmation method when X pressed
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
