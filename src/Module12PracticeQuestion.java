
import java.util.Optional;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class Module12PracticeQuestion extends Application{

	public Font DEFAULT_FONT = Font.font("Times New Roman" , 15);
	private RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
	private Button submitButton;
	private CheckBox studentCheckBox;
	private TextField nameField, ageField;
	private String name, gender;
	private int age;
	private boolean isStudent;
	
	
	public void start(Stage primaryStage) {
		FlowPane pane = new FlowPane();
		pane.setVgap(15);
		pane.setHgap(45);
		
		Scene scene = new Scene(pane, 300, 350);
		
		
		Text welcomeText = new Text("Enter your information");
		welcomeText.setFont(Font.font("Times New Roman", 25));
		pane.getChildren().add(welcomeText);
		
		
		/* Asking for user to enter their name */
		Text getNameText = new Text("Enter your name.");
		getNameText.setFont(DEFAULT_FONT);
		pane.getChildren().add(getNameText);
		
		nameField = new TextField();
		nameField.setOnAction(this::handleTextField);
		pane.getChildren().add(nameField);
		
		
		/* Asking user to enter their age */
		Text getAgeText = new Text("Enter your age.");
		getAgeText.setFont(DEFAULT_FONT);	
		pane.getChildren().add(getAgeText);
		
		ageField = new TextField();
		ageField.setOnAction(this::handleTextField);
		pane.getChildren().add(ageField);
		
		
		/* Asking user to enter their gender */
		HBox genderHBox= new HBox();
		genderHBox.setSpacing(20);

		
		Text getGenderText = new Text("Enter your gender.");
		getGenderText.setFont(DEFAULT_FONT);
		pane.getChildren().add(getGenderText);
		pane.getChildren().get(5).setTranslateX(80);
		
		maleRadioButton = new RadioButton("Male");
		maleRadioButton.setOnAction(this::handleRadioButtons);
		genderHBox.getChildren().add(maleRadioButton);
		
		femaleRadioButton = new RadioButton("Female");
		femaleRadioButton.setOnAction(this::handleRadioButtons);
		genderHBox.getChildren().add(femaleRadioButton);
		
		otherRadioButton = new RadioButton("Other");
		otherRadioButton.setOnAction(this::handleRadioButtons);
		genderHBox.getChildren().add(otherRadioButton);
		
		ToggleGroup radioButtonGroup = new ToggleGroup();
		maleRadioButton.setToggleGroup(radioButtonGroup);
		femaleRadioButton.setToggleGroup(radioButtonGroup);
		otherRadioButton.setToggleGroup(radioButtonGroup);
		
		pane.getChildren().add(genderHBox);
		pane.getChildren().get(6).setTranslateX(40);
		
		
		/* Asking user if they're a student */
		VBox studentVBox = new VBox();
		studentVBox.setSpacing(15);
		
		Text isStudent = new Text("Are you a student?");
		isStudent.setFont(DEFAULT_FONT);
		studentVBox.getChildren().add(isStudent);
	
		studentCheckBox = new CheckBox("Student");
		studentVBox.getChildren().add(studentCheckBox);
		studentCheckBox.setOnAction(this::handleCheckBox);

		pane.getChildren().add(studentVBox);
		pane.getChildren().get(7).setTranslateX(85);
		
		
		
		/* Adding submit button */
		submitButton = new Button("Submit");
		studentVBox.getChildren().add(submitButton);
		studentVBox.getChildren().get(2).setTranslateX(25);
		studentVBox.getChildren().get(2).setTranslateY(25);
		submitButton.setOnAction(this::processSubmitButton);
		
		
		
		primaryStage.setTitle("Information");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	private void handleTextField(ActionEvent event) {
		
		if(event.getSource() == nameField) {
			name = nameField.getText();
			
			//In case nothing is entered
			if(name.isEmpty() || name == null) {
				
				popUpErrorMessage("name");
				
			}
			
		} else if(event.getSource() == ageField) {
			
			//Something other than a number may be entered, this is to fix that
			try {
				
				age = Integer.parseInt(ageField.getText());
				
			}catch(NumberFormatException ex) {
				
				popUpErrorMessage("number");
				
				//Clear TextField after displaying error
				ageField.clear();
			}
		}
	
	}
	
	
	public void handleRadioButtons(ActionEvent event) {
		
		if(event.getSource() == maleRadioButton) {
			gender = "male";
		} else if(event.getSource() == femaleRadioButton) {
			gender = "female";
		} else if(event.getSource() == otherRadioButton) {
			
			//Use pop up to ask user to specify
			TextInputDialog inputDialog = new TextInputDialog();
			inputDialog.setHeaderText(null);
			inputDialog.setTitle("Enter Gender");
			inputDialog.setContentText("Please specify");
			Optional<String> input = inputDialog.showAndWait();
			
			if(input.isPresent() ) {
				
				//Check to see if user entered an empty String
				if(input.get().equals("")) {
					//If nothing is entered show an alert and get user to specify 
					Alert error = new Alert(AlertType.ERROR);
					error.setContentText("Must Specify");
					error.setHeaderText(null);
					error.setTitle("ERROR");
					error.showAndWait();
				}else{
					//If user didn't enter an empty String then add it into String gender 
					gender = input.get();
				}
			
			} 
		}
	}
	
	
	public void handleCheckBox(ActionEvent event) {
		if(studentCheckBox.isSelected()) {
			isStudent = true;
		} else if(!studentCheckBox.isSelected()) {
			isStudent = false;
		}
	}
	
	
	public void processSubmitButton(ActionEvent event) {
		
		//Get the text from all the TextFields just in case user did not press enter 
		try {
			name = nameField.getText();
			if(name == null || name.isEmpty()) {
				throw new NullPointerException();
			}
			age = Integer.parseInt(ageField.getText());
			
			
			//Check to see if user has provided a gender, if not display error message
			/* This is done only to keep multiple pop ups from displaying */
			if(gender == null || gender.isEmpty()) {
				throw new Exception();
			}
			
		}catch(NullPointerException ex) {
				popUpErrorMessage("Name");
			
		}catch(NumberFormatException ex ) {
			popUpErrorMessage("Age");
		}catch(Exception ex) {
			popUpErrorMessage("Gender");
		}
		
		
		
		//If method has gotten this far then all information should be provided; will display summary of information on a pop up
		Alert informationAlert = new Alert(AlertType.INFORMATION);
		informationAlert.setTitle("Information Summary");
		informationAlert.setHeaderText("Information Summary");
		String information = "Name: " + name + "\n" + 
							 "Age: " + age + "\n" +
							 "Gender: " + gender + "\n" + 
							 "Student: " + isStudent;
		
		informationAlert.setContentText(information);
		
		
		informationAlert.showAndWait();
	}
	
	
	//This method handles all the error pop ups when user doesn't give information/the right information
	private void popUpErrorMessage(String errorType) {
		
		Alert errorMessage =  new Alert(AlertType.ERROR);
		errorMessage.setContentText("Must Enter " + errorType);
		errorMessage.setHeaderText(null);
		errorMessage.setTitle("Error");
		errorMessage.showAndWait();
	}
	
	
	public static void main(String[] args) {
		launch(args);

	}

}
