package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

public class Main extends Application
{
    Student studentObject;
    LoginSignup loginSignUpObject;
    Company companyObject;

    MongoClient mongo;
    MongoDatabase database;
    MongoCollection<Document> collection;
    
    public void signUpPageDisplay(final Stage stage)
    {   
        Hyperlink loginHyperlink = new Hyperlink("Already have an Account? Login");

        loginHyperlink.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event)
            {
                loginPageDisplay(stage);
            }
        });

        Label userSignUpLabel = new Label("User ID");       

        Label userAgeLabel = new Label("Age");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm Password");
        Label nameLabel = new Label("Name");
        Label CGPALabel = new Label("CGPA");
        Label rollNoLabel = new Label("Roll Number");
        Label collegeNameLabel = new Label("College Name");
       
        Label userNameExistsErrorLabel = new Label("Username already exists!");
        userNameExistsErrorLabel.setTextFill(Color.web("#ff0000"));
        userNameExistsErrorLabel.setVisible(false);
        Label passwordErrorLabel = new Label("Passwords don't match!");
        passwordErrorLabel.setTextFill(Color.web("#ff0000"));
        passwordErrorLabel.setVisible(false);
        
        Label allFieldsRequiredErrorLabel = new Label("All fields are required!");
        allFieldsRequiredErrorLabel.setTextFill(Color.web("#ff0000"));
        allFieldsRequiredErrorLabel.setVisible(false);
        Label ageErrorLabel = new Label("Enter correct age in digits");
        ageErrorLabel.setTextFill(Color.web("#ff0000"));
        ageErrorLabel.setVisible(false);
        Label ageNotWithinLimits = new Label("Age should be inbetween 18 and 30");
        ageNotWithinLimits.setTextFill(Color.web("#ff0000"));
        ageNotWithinLimits.setVisible(false);
        Label CGPAErrorLabel = new Label("Enter correct CGPA in digits");
        CGPAErrorLabel.setTextFill(Color.web("#ff0000"));
        CGPAErrorLabel.setVisible(false);
        Label CGPANotWithinLimitsErrorLabel = new Label("CGPA should be in between 0-10");
        CGPANotWithinLimitsErrorLabel.setTextFill(Color.web("#ff0000"));
        CGPANotWithinLimitsErrorLabel.setVisible(false);
        Label rollNoErrorLabel = new Label("Enter correct Roll number in digits");
        rollNoErrorLabel.setTextFill(Color.web("#ff0000"));
        rollNoErrorLabel.setVisible(false);

        final TextField nameField = new TextField("");
        final TextField ageField = new TextField("");
        final TextField CGPAField = new TextField("");
        final TextField rollNoField = new TextField("");
        final TextField collegeNameField = new TextField("");
        final TextField usernameTextField = new TextField("");
        final PasswordField userPasswordPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        Button SignUpButton = new Button("Sign Up");
           
        GridPane gridPaneSignUp = new GridPane();   
           
        gridPaneSignUp.setMinSize(400, 200);
        gridPaneSignUp.setPadding(new Insets(10, 10, 10, 10));
        gridPaneSignUp.setVgap(20);
        gridPaneSignUp.setHgap(20);      
        gridPaneSignUp.setAlignment(Pos.CENTER);
            

        SignUpButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                boolean error = true;
                
                studentObject.sid = usernameTextField.getText();
                studentObject.name = nameField.getText();
              
                //replace this by another function which does not add incorrect entries in database
                System.out.println("Password here");
                System.out.println(userPasswordPasswordField.getText());
                System.out.println(confirmPasswordField.getText());
                boolean result = loginSignUpObject.userNameExists(usernameTextField.getText(),
                        userPasswordPasswordField.getText());
                if(result)
                {
                    userNameExistsErrorLabel.setVisible(true);
                    error = true;     
                }
                else
                {
                    userNameExistsErrorLabel.setVisible(false);
                }
               
                if(!(userPasswordPasswordField.getText().equals(confirmPasswordField.getText())))
                {
                    passwordErrorLabel.setVisible(true);
                    error = true;
                }
                else
                {
                    passwordErrorLabel.setVisible(false);
                }
                
                //for age

                int validAge = validatingAge(ageField.getText());
                if(validAge==0)
                {
                	ageErrorLabel.setVisible(false);
                	ageNotWithinLimits.setVisible(false);
                }
                else if(validAge==1)
                {
                	ageErrorLabel.setVisible(true);
                	ageNotWithinLimits.setVisible(false);
                	error=true;
                }
                else
                {
                	ageNotWithinLimits.setVisible(true);
                	ageErrorLabel.setVisible(false);
                	error=true;
                }
                
                int validCGPA = validatingCGPA(CGPAField.getText());
                if(validCGPA==0)
                {
                	CGPAErrorLabel.setVisible(false);
                	CGPANotWithinLimitsErrorLabel.setVisible(false);
                }
                else if(validCGPA==1)
                {
                	CGPAErrorLabel.setVisible(true);
                	CGPANotWithinLimitsErrorLabel.setVisible(false);
                	error=true;
                }
                else
                {
                	CGPANotWithinLimitsErrorLabel.setVisible(true);
                	CGPAErrorLabel.setVisible(false);
                	error=true;
                }
                
                boolean validRollNo = validatingRollNo(rollNoField.getText());
                if(validRollNo)
                {
                	rollNoErrorLabel.setVisible(false);
                }
                else
                {
                	rollNoErrorLabel.setVisible(true);
                	error=true;
                }
                
                
                if(nameField.getText().equals("") || ageField.getText().equals("") || CGPAField.getText().equals("") || rollNoField.getText().equals("") || usernameTextField.getText().equals("") || userPasswordPasswordField.getText().equals("") || confirmPasswordField.getText().equals(""))
                {
                	allFieldsRequiredErrorLabel.setVisible(true);
                	error = true;
                }
                else
                {
                	allFieldsRequiredErrorLabel.setVisible(false);
                }
                	
                if(!passwordErrorLabel.isVisible() && !userNameExistsErrorLabel.isVisible() && !ageErrorLabel.isVisible() && !CGPAErrorLabel.isVisible() && !ageNotWithinLimits.isVisible() && !CGPANotWithinLimitsErrorLabel.isVisible() && !allFieldsRequiredErrorLabel.isVisible() && !rollNoErrorLabel.isVisible())
                {
                    error = false;
                }
                if(!error)
                {
                	studentObject.CGPA = Double.parseDouble(CGPAField.getText());
                	studentObject.age = Double.parseDouble(ageField.getText());
                	studentObject.rollNo = Double.parseDouble(rollNoField.getText());
                	studentObject.college= collegeNameField.getText();
                    studentObject.setStudentData();
                    loginSignUpObject.signUp(usernameTextField.getText(), userPasswordPasswordField.getText());
                    homePageDisplay(stage);
                }
            }
        });

        gridPaneSignUp.add(userSignUpLabel, 0, 0);
        gridPaneSignUp.add(usernameTextField, 1, 0);
        gridPaneSignUp.add(userNameExistsErrorLabel, 2, 0);
        gridPaneSignUp.add(passwordLabel, 0, 1);      
        gridPaneSignUp.add(userPasswordPasswordField, 1, 1);
        gridPaneSignUp.add(confirmPasswordField, 1, 2);
        gridPaneSignUp.add(confirmPasswordLabel, 0, 2);
        gridPaneSignUp.add(passwordErrorLabel , 2, 2);
        gridPaneSignUp.add(nameLabel, 0, 3);
        gridPaneSignUp.add(nameField, 1, 3);
        gridPaneSignUp.add(userAgeLabel, 0, 4);
        gridPaneSignUp.add(ageField, 1, 4);
        gridPaneSignUp.add(ageErrorLabel, 2, 4);
        gridPaneSignUp.add(ageNotWithinLimits, 2, 4);
        gridPaneSignUp.add(CGPALabel, 0, 5);
        gridPaneSignUp.add(CGPAField, 1, 5);
        gridPaneSignUp.add(CGPAErrorLabel, 2, 5);
        gridPaneSignUp.add(CGPANotWithinLimitsErrorLabel, 2, 5);
        gridPaneSignUp.add(rollNoLabel, 0, 6);
        gridPaneSignUp.add(rollNoField, 1, 6);
        gridPaneSignUp.add(rollNoErrorLabel, 2, 6);
        gridPaneSignUp.add(collegeNameLabel, 0, 7);
        gridPaneSignUp.add(collegeNameField, 1, 7);
        gridPaneSignUp.add(allFieldsRequiredErrorLabel, 1, 9);
        gridPaneSignUp.add(SignUpButton, 0,11);
        gridPaneSignUp.add(loginHyperlink, 1, 11);
       
        Scene scene = new Scene(gridPaneSignUp,800,600); 
       
        stage.setScene(scene); 
        //To set stage to full screen
        setFullScreen(stage);
        stage.show();
    }

    public int validatingAge(String ageField)
    {
    	try 
    	{
    		Double checkAge = Double.parseDouble(ageField);
          	if(checkAge<18 || checkAge>30)
          	{
          		return 2;
          	}
    	}
    	catch(Exception e)
    	{
            return 1;  
    	}
    	return 0;
    }
    
    public int validatingCGPA(String CGPAField)
    {
    	try 
  	  	{
    		Double checkCGPA = Double.parseDouble(CGPAField);
        	if(checkCGPA<0 || checkCGPA>10)
        	{
        		return 2;
        	} 
        }
        catch(Exception e)
        {
        	return 1;  
        }
    	return 0;
    }
    
    public boolean validatingRollNo(String rollNoField)
    {
    	try 
  	  	{
    		Double.parseDouble(rollNoField); 
        }
        catch(Exception e)
        {
        	return false;  
        }
  	  	return true;
    }

    public void companyListPageDisplay(Stage stage,ArrayList<String> subDomainList)
    {
    	//access the collection Company
    	collection = database.getCollection("Company");

    	//Get matching documents
    	Document query = new Document("subdomain",new Document("$in",subDomainList));
    	FindIterable<Document> iterDoc = collection.find(query);
    	MongoCursor<Document> it = iterDoc.iterator();
    	Document result;
    	
    	//display them
    	ArrayList<Hyperlink> companyNames = new ArrayList<>();
    	ArrayList<Label> description = new ArrayList<>();
    	ArrayList<Label> domains = new ArrayList<>();
    	ArrayList<Label> subdomains = new ArrayList<>();
    	while(it.hasNext())
    	{
    		result = it.next();
    		companyNames.add(new Hyperlink(result.get("name").toString()));
    		description.add(new Label(result.get("generalDescription").toString()));
    		domains.add(new Label(result.get("domain").toString()));
    		subdomains.add(new Label(result.get("subdomain").toString()));
    	}
    	
    	BorderPane borderPane = createMenuBar(stage);

        VBox vBox[] = new VBox[companyNames.size()+1];
        HBox hBox[] = new HBox[companyNames.size()];
        vBox[companyNames.size()] = new VBox();
        for(int i = 0; i < companyNames.size(); i++)
        {   
            /*
            to get the company Name selected in order to 
            show appropriate profile page
            */
        	String companyNameSelected = companyNames.get(i).getText();
        	
        	companyNames.get(i).setOnMouseClicked(new EventHandler<Event>()
            {
                @Override
                public void handle(Event event) 
                {
                    companyProfilePageDisplay(stage, companyNameSelected);
                }
            });

        	VBox.setMargin(companyNames.get(i), new Insets(5, 10, 5, 10));
            VBox.setMargin(description.get(i), new Insets(5, 10, 5, 10));
            VBox.setMargin(domains.get(i), new Insets(5, 10, 5, 10));
            VBox.setMargin(subdomains.get(i), new Insets(5, 10, 20, 10));
        	
            ImageView imageView = new ImageView(displayImage(companyNames.get(i).getText()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            
            vBox[i] = new VBox();
        	vBox[i].getChildren().add(companyNames.get(i));  
        	vBox[i].getChildren().add(description.get(i));
        	vBox[i].getChildren().add(domains.get(i));
        	vBox[i].getChildren().add(subdomains.get(i));
        	
        	hBox[i] = new HBox();
        	hBox[i].getChildren().addAll(imageView,vBox[i]);

        	vBox[companyNames.size()].getChildren().add(hBox[i]);
        }
     
        ScrollPane scrollPane = new ScrollPane();
    	scrollPane.setContent(vBox[companyNames.size()]);
            
    	// Pannable.
    	scrollPane.setPannable(true);
    	        
    	Scene scene = new Scene(new VBox(borderPane,scrollPane),800,600);  
        stage.setScene(scene);
        setFullScreen(stage);
    	stage.show();	
    }
    
    public void searchByDomainPageDisplay(Stage stage)
    {
    	BorderPane borderPane = createMenuBar(stage);
    	borderPane.setStyle("-fx-foreground-color: black;");
    		
    	CheckBox subdomains[] = new CheckBox[25];
    	subdomains[0] = new CheckBox("Natural Language Processing");
    	subdomains[1] = new CheckBox("Computer Vision");
    	subdomains[2] = new CheckBox("Symbolic Reasoning");
    	subdomains[3] = new CheckBox("Network Security");
    	subdomains[4] = new CheckBox("Information Security");
    	subdomains[5] = new CheckBox("Cryptography");
    	subdomains[6] = new CheckBox("Web Development");
    	subdomains[7] = new CheckBox("Android Development");
    	subdomains[8] = new CheckBox("Embedded Systems Development");
    	subdomains[9] = new CheckBox("Smart Healthcare");
    	subdomains[10] = new CheckBox("IoT In Agriculture");
    	subdomains[11] = new CheckBox("Smart Home");
    	subdomains[12] = new CheckBox("Data Mining");
    	subdomains[13] = new CheckBox("Big Data Analytics");
    	subdomains[14] = new CheckBox("Digital Art");
    	subdomains[15] = new CheckBox("Animation");
    	subdomains[16] = new CheckBox("Video Games");
    	subdomains[17] = new CheckBox("Visual Effects");
    	subdomains[18] = new CheckBox("Computer Architecture");
    	subdomains[19] = new CheckBox("Operating Systems");
    	subdomains[20] = new CheckBox("Parallel Computing");
    	subdomains[21] = new CheckBox("Distributed Computing");
    	subdomains[22] = new CheckBox("Virtualization and storage technologies");
    	subdomains[23] = new CheckBox("Software Defined Networking");
    	subdomains[24] = new CheckBox("Network Administration");
    	
    	Label Domain1 = new Label("Artificial Intelligence");
    	Label Domain2 = new Label("Cyber Security");
    	Label Domain3 = new Label("Software Development");
    	Label Domain4 = new Label("Internet Of Things(IOT)");
    	Label Domain5 = new Label("Data Science");
    	Label Domain6 = new Label("Computer Graphics");
    	Label Domain7 = new Label("Computer Architecture");
    	Label Domain8 = new Label("Concurrent, parallel, and distributed systems");
    	Label Domain9 = new Label("Computer Networking");
           
    	Label noDomainSelectedLabel = new Label("Please Select atleast one domain");
    	noDomainSelectedLabel.setTextFill(Color.web("#FF0000"));
    	noDomainSelectedLabel.setVisible(false);
    	Button searchCompanies = new Button("Search"); 
          
    	GridPane gridPane = new GridPane();    
          
    	gridPane.setMinSize(600, 400); 
    	gridPane.setPadding(new Insets(10, 10, 10, 10)); 
    	gridPane.setVgap(20); 
    	gridPane.setHgap(20);       
    	gridPane.setAlignment(Pos.CENTER); 
    	
    	gridPane.add(Domain1,0 ,1 );
    	gridPane.add(subdomains[0],1 ,2 );
    	gridPane.add(subdomains[1], 1, 3);
    	gridPane.add(subdomains[2], 1, 4);
    	
    	gridPane.add(Domain2,0 ,6 );
    	gridPane.add(subdomains[3],1 ,7 );
    	gridPane.add(subdomains[4], 1, 8);
    	gridPane.add(subdomains[5], 1, 9);
    	
    	gridPane.add(Domain3,0 ,11 );
    	gridPane.add(subdomains[6],1 ,12 );
    	gridPane.add(subdomains[7], 1, 13);
    	gridPane.add(subdomains[8], 1, 14);
    	
    	gridPane.add(Domain4,0 ,16 );
    	gridPane.add(subdomains[9],1 ,17 );
    	gridPane.add(subdomains[10], 1, 18);
    	gridPane.add(subdomains[11], 1, 19);
    	
    	gridPane.add(Domain5,0 ,21 );
    	gridPane.add(subdomains[12],1 ,22 );
    	gridPane.add(subdomains[13], 1, 23);
    	
    	gridPane.add(Domain6,0 ,25 );
    	gridPane.add(subdomains[14],1 ,26 );
    	gridPane.add(subdomains[15], 1, 27);
    	gridPane.add(subdomains[16], 1, 28);
    	gridPane.add(subdomains[17], 1, 29);
    	
    	gridPane.add(Domain7,0 ,31 );
    	gridPane.add(subdomains[18],1 ,32 );
    	gridPane.add(subdomains[19], 1, 33);
    	
    	gridPane.add(Domain8,0 ,35 );
    	gridPane.add(subdomains[20],1 ,37 );
    	gridPane.add(subdomains[21], 1, 38);
    	
    	gridPane.add(Domain9,0 ,40 );
    	gridPane.add(subdomains[22],1 ,41 );
    	gridPane.add(subdomains[23], 1, 42);
    	gridPane.add(subdomains[24], 1, 43);
    	
    	gridPane.add(noDomainSelectedLabel,0,45);
    	gridPane.add(searchCompanies,3,45);
     
     
    	ScrollPane scrollPane = new ScrollPane();
    	
    	//Fitting the scrollPane to the screen size
    	scrollPane.setFitToWidth(true);

    	scrollPane.setContent(gridPane);
            
    	// Make scrollPane Pannable.
    	scrollPane.setPannable(true);
                
    	Scene scene = new Scene(new VBox(borderPane,scrollPane),800,600);  
        stage.setScene(scene);
        //To set stage to full screen 
        setFullScreen(stage);
    	stage.show();
        
    	ArrayList<String> domainList = new ArrayList<String>();
    	
    	searchCompanies.setOnAction(new EventHandler<ActionEvent>()
    	{	
    		boolean error = true;
    		public void handle(ActionEvent event)
    		{
    			//get all selected domains from checkboxes
    	    	for(int i = 0; i < subdomains.length; i++)
    	    		if(subdomains[i].isSelected())
    	    		{
    	    			error = false;
    	    			domainList.add(subdomains[i].getText());
    	    		}
    	    	
    	    	
                if(error)
                	noDomainSelectedLabel.setVisible(true);
                else
                	companyListPageDisplay(stage,domainList);
    		}
    	});
    }
    
    public void searchByCompanyPageDisplay(final Stage stage)
    {    	
        BorderPane borderPane = createMenuBar(stage);
        
    	ObservableList<String> options =
            	FXCollections.observableArrayList();
        
    	ArrayList<String> companyList = new ArrayList<String>();
    	
    	companyList = companyObject.getCompanyList();
    	for(int i=0;i<companyList.size();i++)
    	{
    		options.add(companyList.get(i));
    	}
    	
        final ComboBox<String> companyListSearchBar = new ComboBox<>(options);
            
        companyListSearchBar.getSelectionModel().select("--Select--"); 
        
        GridPane gridPane = new GridPane();    
     
        gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
     
        gridPane.add(companyListSearchBar, 2, 4);
     
        Scene scene = new Scene(new VBox(borderPane,gridPane),800,600);
        stage.setScene(scene);
        //To set stage to full screen 
        setFullScreen(stage);
        stage.show();

        companyListSearchBar.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event)
            {
                String companyNameSelected=(String)companyListSearchBar.getValue();
                companyObject.name=companyNameSelected;
                companyObject.getProfile();
                companyProfilePageDisplay(stage,companyNameSelected);
            }
        });
    }
    
    //this function returns a borderpane corresponding to THE MENUBAR
    private BorderPane createMenuBar(Stage stage)
    {
        MenuBar leftBar = new MenuBar();
        Menu profile = new Menu("Profile");
        Menu home = new Menu("\b");
        Menu search = new Menu("Search");
        MenuItem searchByCompany = new MenuItem("Search by company name");
        MenuItem searchByDomain = new MenuItem("Search by domains");
        MenuItem signout = new MenuItem("Signout");
        MenuItem myProfile = new MenuItem("My Profile");
        profile.getItems().addAll(myProfile,signout);
        search.getItems().addAll(searchByDomain,searchByCompany);
        
        Label menuLabel = new Label("Home");
        menuLabel.setOnMouseClicked(new EventHandler<Event>()
        {
            @Override
            public void handle(Event event) 
            {
                homePageDisplay(stage);
            }
        });
        
        home.setGraphic(menuLabel);     
        
        leftBar.getMenus().addAll(home,search);
        MenuBar rightBar = new MenuBar();
        rightBar.getMenus().addAll(profile);
        Region spacer = new Region();
        spacer.getStyleClass().add("menu-bar");
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menubars = new HBox(leftBar, spacer, rightBar);
        
        myProfile.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("here1");
                myProfilePageDisplay(stage);
            }   
        });
        
        searchByDomain.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event)
            {
                searchByDomainPageDisplay(stage);
            }     
        });
        
        searchByCompany.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event)
            {
                searchByCompanyPageDisplay(stage);
            }
        });
        
        signout.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event)
            {
                loginPageDisplay(stage);
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menubars);

        return borderPane;
    }
    
    private void setFullScreen(Stage stage)
    {
    	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    public void homePageDisplay(final Stage stage)
    {
        BorderPane borderPane = createMenuBar(stage);

        StackPane root = new StackPane();
        root.setStyle(
            "-fx-background-image: url(" + System.getProperty("user.dir")+"/images/nice.jpg'" +
            "); " +
            "-fx-background-size: auto;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-background-position: center;"
        );
        root.getChildren().add(borderPane);
     Scene scene = new Scene(root,800,600);  
           
        stage.setScene(scene);
        //To set stage to full screen 
        setFullScreen(stage);
        stage.show();
    }
    
    //Function to display sample test for each company
    public void companyTestPageDisplay(Stage stage,String companyName)
    {
    	//access the collection Company    	
    	collection = database.getCollection("Company");

    	//Get matching document
    	FindIterable<Document> iterDoc = collection.find(new Document("name",companyName));
    	MongoCursor<Document> it = iterDoc.iterator();
    	Document company = it.next();
    
    	BorderPane borderPane = createMenuBar(stage);
    	
    	//Actual test will be stored in this label
    	Label exam = new Label(company.get("test").toString());
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(exam,0,0);
        
        ScrollPane scrollPane = new ScrollPane();
    	//Fitting the scrollPane to the screen size
    	scrollPane.setFitToWidth(true);
    	scrollPane.setPrefSize(600, 700);
    	
    	scrollPane.setContent(gridPane);
            
    	// Make scrollPane Pannable.
    	scrollPane.setPannable(true);
        
        Scene scene = new Scene(new VBox(borderPane,scrollPane),800,600);
    	stage.setScene(scene);
    	//To set stage to full screen 
        setFullScreen(stage);
    	stage.show();  	
    }
  
    //This function is to display the answer key of sample tests per company
    public void companyAnsKeyPageDisplay(Stage stage,String companyName)
    {
    	//access the collection Company    	
    	collection = database.getCollection("Company");

    	//Get matching document
    	FindIterable<Document> iterDoc = collection.find(new Document("name",companyName));
    	MongoCursor<Document> it = iterDoc.iterator();
    	Document company = it.next();
   
    	BorderPane borderPane = createMenuBar(stage);
    	
    	//actual answer key will be stored in this label 
    	Label ansKey = new Label(company.get("ansKey").toString());
    	
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(ansKey,0,0);
        
        ScrollPane scrollPane = new ScrollPane();
    	//Fitting the scrollPane to the screen size
    	scrollPane.setFitToWidth(true);
    	scrollPane.setPrefSize(600, 700);
    	
    	scrollPane.setContent(gridPane);
            
    	// Make scrollPane Pannable.
    	scrollPane.setPannable(true);
        
        Scene scene = new Scene(new VBox(borderPane,scrollPane),800,600);
    	stage.setScene(scene);
    	//To set stage to full screen 
        setFullScreen(stage);
    	stage.show();  	
    }
    
    /*
     * Function for displaying company profile to STUDENT
     */
    public void companyProfilePageDisplay(Stage stage,String companyName)
    {
    	//access the collection Company    	
    	collection = database.getCollection("Company");

    	//Get matching document
    	FindIterable<Document> iterDoc = collection.find(new Document("name",companyName));
    	MongoCursor<Document> it = iterDoc.iterator();
    	Document company = it.next();
    
        //display the profile
    	BorderPane borderPane = createMenuBar(stage);
	
        //only Id's
    	Label companyNameId = new Label("Name: ");
    	Label companyDomainId = new Label("Domain: ");
    	Label companySubdomainId = new Label("Subdomain: ");
    	Label generalDescriptionId = new Label("Description: ");
    	Label monthOfVisitId = new Label("Month Of Visit: ");
    	Label minCGPAId = new Label("Minimum CGPA: ");
    	Label addressId = new Label("Address: ");
    	Label contactId = new Label("Contact: ");
    	
        //actual values
    	Label companyNameLabel = new Label(company.get("name").toString());
    	Label companyDomainLabel = new Label(company.get("domain").toString());
    	Label companySubdomainLabel = new Label(company.get("subdomain").toString());
    	Label generalDescriptionLabel = new Label(company.get("generalDescription").toString());
    	Label monthOfVisitLabel = new Label(company.get("monthOfVisit").toString());
    	Label minCGPALabel = new Label(company.get("minCGPA").toString());
    	Label addressLabel = new Label(company.get("address").toString());
    	Label contactLabel = new Label(company.get("contact").toString());
        ImageView imageView = new ImageView(displayImage(companyName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
    	
    	Button exam = new Button("Sample Test");   //on clicking..test will be displayed on new page.
    	Button ansKey = new Button("Answer Key");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(companyNameId, 0, 1);
        gridPane.add(companyDomainId, 0, 2);
        gridPane.add(companySubdomainId, 0, 3);
        gridPane.add(generalDescriptionId, 0, 4);
        gridPane.add(monthOfVisitId, 0, 5);
        gridPane.add(minCGPAId, 0, 6);
        gridPane.add(addressId, 0, 7);
        gridPane.add(contactId, 0, 8);
        
        gridPane.add(imageView, 0, 0);
        gridPane.add(companyNameLabel, 1, 1);
        gridPane.add(companyDomainLabel, 1, 2);
        gridPane.add(companySubdomainLabel, 1, 3);
        gridPane.add(generalDescriptionLabel, 1, 4);
        gridPane.add(monthOfVisitLabel, 1, 5);
        gridPane.add(minCGPALabel, 1, 6);
        gridPane.add(addressLabel, 1, 7);
        gridPane.add(contactLabel, 1, 8);
        
        gridPane.add(exam, 1, 10);
        gridPane.add(ansKey, 1, 11);
       
        exam.setOnMouseClicked(new EventHandler<Event>()
        {
            @Override
            public void handle(Event event) 
            {
            	companyTestPageDisplay(stage,companyName);
            }
        });
        
        ansKey.setOnMouseClicked(new EventHandler<Event>()
        {
            @Override
            public void handle(Event event) 
            {
            	companyAnsKeyPageDisplay(stage,companyName);
            }
        });

        Scene scene = new Scene(new VBox(borderPane,gridPane),800,600);
    	stage.setScene(scene);
    	//To set stage to full screen 
        setFullScreen(stage);
    	stage.show();
    }
    
    /*
     * Function for STUDENT's Profile Page Display
     */
    public void myProfilePageDisplay(Stage stage)
    {
        BorderPane borderPane = createMenuBar(stage);

       	studentObject.getStudentData();
    	//Label studentId = new Label(studentObject.sid);
    	Label studentName = new Label(studentObject.name);
    	Label studentAge = new Label(""+((Double)studentObject.age).intValue());
    	Label studentCGPA = new Label(""+studentObject.CGPA);
    	Label studentRollNo = new Label(""+((Double)studentObject.rollNo).intValue());
    	Label studentCollegeName = new Label(""+studentObject.college);
    	
    	//Label displayId = new Label("ID");
    	Label displayName = new Label("Name");
    	Label displayAge = new Label("Age");
    	Label displayCGPA = new Label("CGPA");
    	Label displayRollNo = new Label("Roll Number");
    	Label displayCollegeName = new Label("College Name");
    	
    	Button editProfile = new Button("Edit");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
    	
    	gridPane.add(displayName, 0, 1);
    	gridPane.add(studentName, 1, 1);
    	gridPane.add(displayAge, 0, 2);
    	gridPane.add(studentAge, 1, 2);
    	gridPane.add(displayCGPA, 0, 3);
    	gridPane.add(studentCGPA, 1, 3);
    	gridPane.add(displayRollNo, 0, 4);
    	gridPane.add(studentRollNo, 1, 4);
    	gridPane.add(displayCollegeName, 0, 5);
    	gridPane.add(studentCollegeName, 1, 5);
    	gridPane.add(editProfile, 0, 7);
    	
    	editProfile.setOnAction(new EventHandler<ActionEvent>()
    	{
    		public void handle(ActionEvent event)
    		{
    			myProfileEditPageDisplay(stage);
    		}
    	});
    	
    	Scene scene = new Scene(new VBox(borderPane,gridPane),800,600);
    	stage.setScene(scene);
    	//To set stage to full screen 
        setFullScreen(stage);
    	stage.show();
    }
    
    public void myProfileEditPageDisplay(Stage stage)
    {
    	//for getting password get the collection StudentLogin
    	collection = database.getCollection("StudentLogin");
    	
    	BorderPane borderPane = createMenuBar(stage);
    	
    	TextField studentName = new TextField(studentObject.name);
    	TextField studentAge = new TextField(""+((Double)studentObject.age).intValue());
    	TextField studentCGPA = new TextField(""+studentObject.CGPA);
    	TextField studentRollNo = new TextField(""+((Double)studentObject.rollNo).intValue());
    	TextField studentCollegeName = new TextField(""+studentObject.college);
    	PasswordField studentCurrentPassword = new PasswordField();
    	PasswordField studentNewPassword = new PasswordField();
    	PasswordField studentConfirmPassword = new PasswordField();

    	Label displayName = new Label("Name");
    	Label displayAge = new Label("Age");
    	Label displayCGPA = new Label("CGPA");
    	Label displayRollNo = new Label("Roll Number");
    	Label displayCollegeName = new Label("College Name");
    	Label displayCurrentPassword = new Label("Current Password");
    	Label displayNewPassword = new Label("New Password");
    	Label displayConfirmPassword = new Label("Confirm Password");
    	
    	Label allFieldsRequiredErrorLabel = new Label("All fields are required!");
        allFieldsRequiredErrorLabel.setTextFill(Color.web("#ff0000"));
        allFieldsRequiredErrorLabel.setVisible(false);
        Label allPasswordFieldsRequiredErrorLabel = new Label("All fields are required!");
        allPasswordFieldsRequiredErrorLabel.setTextFill(Color.web("#ff0000"));
        allPasswordFieldsRequiredErrorLabel.setVisible(false);
        Label passwordSuccessfullyChangedLabel = new Label("Password Successfully Changed!");
        passwordSuccessfullyChangedLabel.setTextFill(Color.web("#00ff99"));
        passwordSuccessfullyChangedLabel.setVisible(false);
        Label currentPasswordErrorLabel = new Label("Incorrect Password!");
        currentPasswordErrorLabel.setTextFill(Color.web("#ff0000"));
        currentPasswordErrorLabel.setVisible(false);
        Label passwordErrorLabel = new Label("Passwords don't match!");
        passwordErrorLabel.setTextFill(Color.web("#ff0000"));
        passwordErrorLabel.setVisible(false);
        Label ageErrorLabel = new Label("Enter correct age in digits");
        ageErrorLabel.setTextFill(Color.web("#ff0000"));
        ageErrorLabel.setVisible(false);
        Label ageNotWithinLimits = new Label("Age should be inbetween 18 and 30");
        ageNotWithinLimits.setTextFill(Color.web("#ff0000"));
        ageNotWithinLimits.setVisible(false);
        Label CGPAErrorLabel = new Label("Enter correct CGPA in digits");
        CGPAErrorLabel.setTextFill(Color.web("#ff0000"));
        CGPAErrorLabel.setVisible(false);
        Label CGPANotWithinLimitsErrorLabel = new Label("CGPA should be in between 0-10");
        CGPANotWithinLimitsErrorLabel.setTextFill(Color.web("#ff0000"));
        CGPANotWithinLimitsErrorLabel.setVisible(false);
        Label rollNoErrorLabel = new Label("Enter correct Roll number in digits");
        rollNoErrorLabel.setTextFill(Color.web("#ff0000"));
        rollNoErrorLabel.setVisible(false);
    	
    	Button saveChanges = new Button("Save changes");
    	Button changePasswordButton = new Button("Change Password");
    	Button cancel = new Button("Cancel");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
    	
    	gridPane.add(displayName, 0, 0);
    	gridPane.add(studentName, 1, 0);
    	gridPane.add(displayAge, 0, 1);
    	gridPane.add(studentAge, 1, 1);
    	gridPane.add(ageErrorLabel, 2, 1);
    	gridPane.add(ageNotWithinLimits, 2, 1);
    	gridPane.add(displayCGPA, 0, 2);
    	gridPane.add(studentCGPA, 1, 2);
    	gridPane.add(CGPAErrorLabel, 2, 2);
    	gridPane.add(CGPANotWithinLimitsErrorLabel, 2, 2);
    	gridPane.add(displayRollNo, 0, 3);
    	gridPane.add(studentRollNo, 1, 3);
    	gridPane.add(rollNoErrorLabel, 2, 3);
    	gridPane.add(displayCollegeName, 0, 4);
    	gridPane.add(studentCollegeName, 1, 4);
    	gridPane.add(allFieldsRequiredErrorLabel, 0, 6);
    	gridPane.add(saveChanges, 0, 7);
    	gridPane.add(cancel, 1, 7);
    	
    	gridPane.add(displayCurrentPassword, 3, 0);
    	gridPane.add(displayNewPassword, 3, 1);
    	gridPane.add(displayConfirmPassword, 3, 2);
    	gridPane.add(studentCurrentPassword, 4, 0);
    	gridPane.add(studentNewPassword, 4, 1);
    	gridPane.add(studentConfirmPassword, 4, 2);
    	gridPane.add(changePasswordButton, 3, 4);
    	gridPane.add(currentPasswordErrorLabel, 5, 0);
    	gridPane.add(passwordErrorLabel, 5, 1);
    	gridPane.add(allPasswordFieldsRequiredErrorLabel, 4, 3);
    	gridPane.add(passwordSuccessfullyChangedLabel, 4, 3);
    	
    	saveChanges.setOnAction(new EventHandler<ActionEvent>() 
    	{
    		public void handle(ActionEvent event)
    		{
    			boolean error = true;
    			int validAge = validatingAge(studentAge.getText());
                if(validAge==0)
                {
                	ageErrorLabel.setVisible(false);
                	ageNotWithinLimits.setVisible(false);
                }
                else if(validAge==1)
                {
                	ageErrorLabel.setVisible(true);
                	ageNotWithinLimits.setVisible(false);
                	error=true;
                }
                else
                {
                	ageNotWithinLimits.setVisible(true);
                	ageErrorLabel.setVisible(false);
                	error=true;
                }
                
                int validCGPA = validatingCGPA(studentCGPA.getText());
                if(validCGPA==0)
                {
                	CGPAErrorLabel.setVisible(false);
                	CGPANotWithinLimitsErrorLabel.setVisible(false);
                }
                else if(validCGPA==1)
                {
                	CGPAErrorLabel.setVisible(true);
                	CGPANotWithinLimitsErrorLabel.setVisible(false);
                	error=true;
                }
                else
                {
                	CGPANotWithinLimitsErrorLabel.setVisible(true);
                	CGPAErrorLabel.setVisible(false);
                	error=true;
                }
                
                boolean validRollNo = validatingRollNo(studentRollNo.getText());
                if(validRollNo)
                {
                	rollNoErrorLabel.setVisible(false);
                }
                else
                {
                	rollNoErrorLabel.setVisible(true);
                	error=true;
                }
                
                if(studentName.getText().equals("") || studentAge.getText().equals("") || studentCGPA.getText().equals("") || studentRollNo.getText().equals("") || studentCollegeName.getText().equals(""))
                {
                	allFieldsRequiredErrorLabel.setVisible(true);
                	error = true;
                }
                else
                {
                	allFieldsRequiredErrorLabel.setVisible(false);
                }
                	
                if(!ageErrorLabel.isVisible() && !CGPAErrorLabel.isVisible() && !ageNotWithinLimits.isVisible() && !CGPANotWithinLimitsErrorLabel.isVisible() && !allFieldsRequiredErrorLabel.isVisible() && !rollNoErrorLabel.isVisible())
                {
                    error = false;
                }
                if(!error)
                {
                	studentObject.name = studentName.getText().toString();
                	studentObject.age = Double.parseDouble(studentAge.getText().toString());
                	studentObject.CGPA = Double.parseDouble(studentCGPA.getText().toString());
                	studentObject.rollNo = Double.parseDouble(studentRollNo.getText().toString());
                	studentObject.college = studentCollegeName.getText().toString();
                	studentObject.updateProfile();
                	myProfilePageDisplay(stage);
                }
    		}
		});
    	
    	changePasswordButton.setOnAction(new EventHandler<ActionEvent>()
    	{
    		public void handle(ActionEvent event)
    		{
    			boolean error = false;
    			
    			BasicDBObject query = new BasicDBObject("username", studentObject.sid);
    			FindIterable<Document> iterDoc = collection.find(query);
    			MongoCursor<Document> it = iterDoc.iterator();

    			//check if password matches
    			Document result = it.next();
    			if(!result.get("password").equals(studentCurrentPassword.getText()))
    			{
    				//wrong password
    				error = true;
    				currentPasswordErrorLabel.setVisible(true);
    			}
    			else
    			{
    				currentPasswordErrorLabel.setVisible(false);
    			}
    				
    			if(!(studentNewPassword.getText().equals(studentConfirmPassword.getText())))
                {
                    passwordErrorLabel.setVisible(true);
                    error = true;
                }
                else
                {
                    passwordErrorLabel.setVisible(false);
                }
    			
    			if(studentCurrentPassword.getText().equals("") || studentNewPassword.getText().equals("") || studentConfirmPassword.getText().equals(""))
                {
                	allPasswordFieldsRequiredErrorLabel.setVisible(true);
                	error = true;
                }
                else
                {
                	allPasswordFieldsRequiredErrorLabel.setVisible(false);
                }
                	
                if(!currentPasswordErrorLabel.isVisible() && !passwordErrorLabel.isVisible() && !allPasswordFieldsRequiredErrorLabel.isVisible())
                {
                    error = false;
                }
                if(!error)
                {
                	//then change the password
                	Document searchQuery = new Document("username", studentObject.sid);

                    Document newDocument = new Document("username", studentObject.sid) 
                            .append("password",studentConfirmPassword.getText());
                    
                    //replace that document in the Student Collection
                    collection.replaceOne(searchQuery,newDocument);    
                    
                    passwordSuccessfullyChangedLabel.setVisible(true);
                }
    		}
    	});
    	
    	cancel.setOnAction(new EventHandler<ActionEvent>() 
    	{
    		public void handle(ActionEvent event)
    		{   			
    			myProfilePageDisplay(stage);
    		}
		});
    	
    	Scene scene = new Scene(new VBox(borderPane,gridPane),400,400);
    	stage.setScene(scene);
    	stage.show();
    }
    
    public void loginPageDisplay(final Stage stage)
    {
    	/*
         * Function for displaying the Login Page
         */
    	ObservableList<String> options = 
        	FXCollections.observableArrayList(
    		"Student",
        	"Company",
        	"Admin"
    	);
        
        final ComboBox<String> loginType = new ComboBox<String>(options);
        Hyperlink signUpHyperlink = new Hyperlink("SignUp");

        signUpHyperlink.setOnAction(new EventHandler<ActionEvent>() {
   
            public void handle(ActionEvent event)
            {
                signUpPageDisplay(stage);
            }
        });   
        
     	loginType.getSelectionModel().selectFirst();
     	
        Label typeLabel = new Label("Type of login");
        Label signupLabel = new Label("New user?");
        Label userLoginLabel = new Label("User ID");       
        Label passwordLabel = new Label("Password");
        Label errorLabel = new Label("Username or password incorrect!");
        errorLabel.setTextFill(Color.web("#ff0000"));
        errorLabel.setVisible(false);

        final TextField userLoginTextField = new TextField();       
        final PasswordField userPasswordPasswordField = new PasswordField();  
        Button loginButton = new Button("Login"); 
      
        GridPane gridPane = new GridPane();    
      
        gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER); 
       
        gridPane.add(userLoginLabel, 0, 1); 
        gridPane.add(userLoginTextField, 1, 1); 
        gridPane.add(passwordLabel, 0, 2);       
        gridPane.add(userPasswordPasswordField, 1, 2);
        gridPane.add(errorLabel, 0, 3);
        gridPane.add(loginButton, 0, 4); 
        gridPane.add(signUpHyperlink, 1, 5);
        gridPane.add(signupLabel, 0, 5);
        gridPane.add(loginType, 1, 0);
        gridPane.add(typeLabel, 0, 0);
        
//        FileInputStream input = null;
//		try 
//		{
//			String path= ""+(System.getProperty("user.dir"))+"/images/"+"Youtube"+".png";
//			input = new FileInputStream(path);
//		}
//		catch (FileNotFoundException e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//           Image image = new Image(input);
                  
//           StackPane root = new StackPane();
//           root.setStyle(
//               "-fx-background-image: url(" +
//                   "'file:/Users/rohan/Training-and-Placement/images/final.jpg'" +
//               "); " +
//               "-fx-background-size: auto;" +
//               "-fx-background-repeat: no-repeat;" +
//               "-fx-background-position: center;"
//           );
//           root.getChildren().add(gridPane);
        Scene scene = new Scene(gridPane,800,600);  
//        gridPane.setStyle("-fx-background-color: #0000FF;");
//        scene.getStylesheets().add(Main.class.getResource("trial.css").toExternalForm());
        scene.getStylesheets().add("trial.css");
        stage.setTitle("Training-and-Placement"); 
        stage.setScene(scene);     
        
      //To set stage to full screen 
        setFullScreen(stage);
        
        stage.show();
        
        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
        //Code to check all login credentials are filled
        	public void handle(ActionEvent event)
        	{
        		boolean result=loginSignUpObject.login(userLoginTextField.getText(),
        											userPasswordPasswordField.getText() ,
        											loginType.getValue());
        		if(result)
        		{
        			if(loginType.getValue() == "Student")
        			{
        				studentObject.sid = userLoginTextField.getText();
        				homePageDisplay(stage);
        			}
        			else if(loginType.getValue() == "Company")
        			{
        				companyObject.companyId = userLoginTextField.getText();
        				companyHomePageDisplay(stage);
        			}
        			else
        				//display page for admin
        				;
        		}
        		else
        		{
        			//display error
        			errorLabel.setVisible(true);
        		}
        	}
        });
    }

    public void companyEditProfilePageDisplay(Stage stage)
    {
    	/*
         * Edit Profile function for Company Person
         */
        //only Id's
    	Label companyNameId = new Label("Name: ");
    	Label companyDomainId = new Label("Domain: ");
    	Label companySubdomainId = new Label("Subdomain: ");
    	Label generalDescriptionId = new Label("Description: ");
    	Label monthOfVisitId = new Label("Month Of Visit: ");
    	Label minCGPAId = new Label("Minimum CGPA: ");
    	Label addressId = new Label("Address: ");
    	Label contactId = new Label("Contact: ");
    	
        //values in textBoxes
    	TextField companyNameTextField = new TextField(companyObject.name);
    	TextField companyDomainTextField = new TextField(companyObject.domain);
    	TextField companySubdomainTextField = new TextField(companyObject.subdomain);
    	TextField generalDescriptionTextField = new TextField(companyObject.generalDescription);
    	TextField monthOfVisitTextField = new TextField(companyObject.monthOfVisit);
    	TextField minCGPATextField = new TextField("" + companyObject.minCGPA);
    	TextField addressTextField = new TextField(companyObject.address);
    	TextField contactTextField = new TextField("" + ((Double)companyObject.contact).intValue());  
        ImageView imageView = new ImageView(displayImage(companyObject.name));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);  	
    	
    	Button saveProfileButton = new Button("Save Profile");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(imageView, 0, 0);
        gridPane.add(companyNameId, 0, 1);
        gridPane.add(companyDomainId, 0, 2);
        gridPane.add(companySubdomainId, 0, 3);
        gridPane.add(generalDescriptionId, 0, 4);
        gridPane.add(monthOfVisitId, 0, 5);
        gridPane.add(minCGPAId, 0, 6);
        gridPane.add(addressId, 0, 7);
        gridPane.add(contactId, 0, 8);
        
        gridPane.add(companyNameTextField, 1, 1);
        gridPane.add(companyDomainTextField, 1, 2);
        gridPane.add(companySubdomainTextField, 1, 3);
        gridPane.add(generalDescriptionTextField, 1, 4);
        gridPane.add(monthOfVisitTextField, 1, 5);
        gridPane.add(minCGPATextField, 1, 6);
        gridPane.add(addressTextField, 1, 7);
        gridPane.add(contactTextField, 1, 8);
        
        gridPane.add(saveProfileButton, 0, 10);
        
        Scene scene = new Scene(gridPane,800,600);
    	stage.setScene(scene);
    	//To set stage to full screen 
        setFullScreen(stage);
    	stage.show();
    	
    	//on save profile button clicked, add values to database, and show profile page
    	saveProfileButton.setOnAction(new EventHandler<ActionEvent>()
    	{
    		public void handle(ActionEvent event)
    		{
    			//get values from text boxes
    			companyObject.name = companyNameTextField.getText();
    			companyObject.domain = companyDomainTextField.getText();
    			companyObject.subdomain = companySubdomainTextField.getText();
    			companyObject.generalDescription = generalDescriptionTextField.getText();
    			companyObject.monthOfVisit = monthOfVisitTextField.getText();
    			companyObject.minCGPA = Double.parseDouble(minCGPATextField.getText());
    			companyObject.address = addressTextField.getText();
    			companyObject.contact = Double.parseDouble(contactTextField.getText());
    			
    			companyObject.updateProfile();
    			companyHomePageDisplay(stage);
    		}
    	});
    }
    
    public void companyHomePageDisplay(Stage stage)
    {
    	/*
         * This function is for Company Person
         */
    	//get the profile from object
    	companyObject.getProfile();
    	
        //display the profile
        //only Id's
    	Label companyNameId = new Label("Name: ");
    	Label companyDomainId = new Label("Domain: ");
    	Label companySubdomainId = new Label("Subdomain: ");
    	Label generalDescriptionId = new Label("Description: ");
    	Label monthOfVisitId = new Label("Month Of Visit: ");
    	Label minCGPAId = new Label("Minimum CGPA: ");
    	Label addressId = new Label("Address: ");
    	Label contactId = new Label("Contact: ");
    	
        //actual values
    	Label companyNameTextField = new Label(companyObject.name);
    	Label companyDomainTextField = new Label(companyObject.domain);
    	Label companySubdomainTextField = new Label(companyObject.subdomain);
    	Label generalDescriptionTextField = new Label(companyObject.generalDescription);
    	Label monthOfVisitTextField = new Label(companyObject.monthOfVisit);
    	Label minCGPATextField = new Label("" + companyObject.minCGPA);
    	Label addressTextField = new Label(companyObject.address);
    	Label contactTextField = new Label("" + ((Double)companyObject.contact).intValue()); 	
    	ImageView imageView = new ImageView(displayImage(companyObject.name));
    	imageView.setFitHeight(100);
    	imageView.setFitWidth(100);
    	
    	Button editProfileButton = new Button("Edit Profile");
    	Button logoutButton = new Button("Logout");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        
        gridPane.add(companyNameId, 0, 1);
        gridPane.add(companyDomainId, 0, 2);
        gridPane.add(companySubdomainId, 0, 3);
        gridPane.add(generalDescriptionId, 0, 4);
        gridPane.add(monthOfVisitId, 0, 5);
        gridPane.add(minCGPAId, 0, 6);
        gridPane.add(addressId, 0, 7);
        gridPane.add(contactId, 0, 8);
        
        gridPane.add(imageView, 0, 0);
        gridPane.add(companyNameTextField, 1, 1);
        gridPane.add(companyDomainTextField, 1, 2);
        gridPane.add(companySubdomainTextField, 1, 3);
        gridPane.add(generalDescriptionTextField, 1, 4);
        gridPane.add(monthOfVisitTextField, 1, 5);
        gridPane.add(minCGPATextField, 1, 6);
        gridPane.add(addressTextField, 1, 7);
        gridPane.add(contactTextField, 1, 8);
        
        gridPane.add(editProfileButton, 0, 11);
        gridPane.add(logoutButton, 1, 11);
        
        Scene scene = new Scene(gridPane,800,600);
    	stage.setScene(scene);

    	//To set stage to full screen 
        setFullScreen(stage);
    	stage.show();
    	
    	editProfileButton.setOnAction(new EventHandler<ActionEvent>()
    	{
    		public void handle(ActionEvent event)
    		{
    			companyEditProfilePageDisplay(stage);
    		}
    	});
    	logoutButton.setOnAction(new EventHandler<ActionEvent>()
    	{
    		public void handle(ActionEvent event)
    		{
    			loginPageDisplay(stage);
    		}
    	});
    	
    }

    public Image displayImage(String companyName)
    {
    	System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
    	System.out.println(companyName);
    	FileInputStream input = null;
		try 
		{
			String path= ""+(System.getProperty("user.dir"))+"/images/"+companyName+".jpg";
			input = new FileInputStream(path);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           Image image = new Image(input);
           return image;
    } 
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {

    	loginSignUpObject = new LoginSignup();
    	companyObject = new Company();
    	studentObject = new Student();

        //Create a mongo client
        mongo = new MongoClient("localhost",27017);

        //access the database
        database = mongo.getDatabase("tnpdb");

        loginPageDisplay(primaryStage);
    }

    public static void main(String []args)
    {
        launch(args);
    }
}
