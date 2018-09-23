package application;

import java.util.ArrayList;

import org.bson.Document;

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
import javafx.scene.layout.VBox;
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
        Hyperlink loginHyperlink = new Hyperlink("Login");

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

        final TextField nameField = new TextField();
        final TextField ageField = new TextField();
        final TextField CGPAField = new TextField();
        final TextField userSignUpTextField = new TextField();       
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
        		studentObject = new Student(userSignUpTextField.getText().toString(),
        				nameField.getText().toString(),
        				Double.parseDouble(ageField.getText().toString()),
        				Double.parseDouble(CGPAField.getText().toString()));

        		boolean result=loginSignUpObject.signUp(userSignUpTextField.getText().toString(),
        				userPasswordPasswordField.getText().toString());
        		if(result)
        		{
                    studentObject.setStudentData();
        			homePageDisplay(stage);
        		}
        		else
        		{
        			signUpPageDisplay(stage);
        		}	
        	}
        });

        gridPaneSignUp.add(userSignUpLabel, 0, 0); 
        gridPaneSignUp.add(userSignUpTextField, 1, 0); 
        gridPaneSignUp.add(nameLabel, 0, 1);
        gridPaneSignUp.add(nameField, 1, 1);
        gridPaneSignUp.add(ageField, 1, 2);
        gridPaneSignUp.add(userAgeLabel, 0, 2);
        gridPaneSignUp.add(CGPALabel, 0, 3);
        gridPaneSignUp.add(CGPAField, 1, 3);
        gridPaneSignUp.add(passwordLabel, 0, 4);       
        gridPaneSignUp.add(userPasswordPasswordField, 1, 4);
        gridPaneSignUp.add(confirmPasswordField, 1, 5);
        gridPaneSignUp.add(confirmPasswordLabel, 0, 5); 
        gridPaneSignUp.add(SignUpButton, 0,6); 
        gridPaneSignUp.add(loginHyperlink, 1, 6);
        
        Scene scene = new Scene(gridPaneSignUp,800,600);  
        
        stage.setScene(scene);  
        //To set stage to full screen 
        setFullScreen(stage);
        stage.show();
    }

    public void companyListPageDisplay(Stage stage,ArrayList<String> domainList)
    {
    	//access the collection Company
    	collection = database.getCollection("Company");

    	//Get matching documents
    	Document query = new Document("subdomain",new Document("$in",domainList));
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

        VBox vBox = new VBox();
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
                	System.out.println(companyNameSelected + " is selected");
                    companyProfilePageDisplay(stage,companyNameSelected);
                }
            });

        	VBox.setMargin(companyNames.get(i), new Insets(5, 10, 5, 10));
            VBox.setMargin(description.get(i), new Insets(5, 10, 5, 10));
            VBox.setMargin(domains.get(i), new Insets(5, 10, 5, 10));
            VBox.setMargin(subdomains.get(i), new Insets(5, 10, 20, 10));
        	
        	vBox.getChildren().add(companyNames.get(i));  
        	vBox.getChildren().add(description.get(i));
        	vBox.getChildren().add(domains.get(i));
        	vBox.getChildren().add(subdomains.get(i));

        }
        
        ScrollPane scrollPane = new ScrollPane();
    	scrollPane.setContent(vBox);
            
    	// Pannable.
    	scrollPane.setPannable(true);
    	        
    	Scene scene = new Scene(new VBox(borderPane,scrollPane),800,600);  
        stage.setScene(scene);
        //To set stage to full screen 
        setFullScreen(stage);
    	stage.show();	
    }
    
    public void searchByDomainPageDisplay(Stage stage)
    {
    	BorderPane borderPane = createMenuBar(stage);
    	borderPane.setStyle("-fx-foreground-color: black;");
    		
    	//main code for this function
    	CheckBox subdomains[] = new CheckBox[9];
    	subdomains[0] = new CheckBox("Artificial Intelligence");
    	subdomains[1] = new CheckBox("Machine Learning");
    	subdomains[2] = new CheckBox("Natural Language Processing");
    	//update required names here afterwards
    	subdomains[3] = new CheckBox("subdomain4");
    	subdomains[4] = new CheckBox("subdomain5");
    	subdomains[5] = new CheckBox("subdomain6");
    	subdomains[6] = new CheckBox("subdomain7");
    	subdomains[7] = new CheckBox("subdomain8");
    	subdomains[8] = new CheckBox("subdomain9");
    	
    	Label Domain1 = new Label("Domain1");
    	Label Domain2 = new Label("Domain2");
    	Label Domain3 = new Label("Domain3");
           
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
    	
    	gridPane.add(searchCompanies,3,30);//purposefully kept much below for testing scrollpane
     
     
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
        
    	ArrayList<String> domainList = new ArrayList<String>();
    	
    	searchCompanies.setOnAction(new EventHandler<ActionEvent>()
    	{	
    		public void handle(ActionEvent event)
    		{
    			//get all selected domains from checkboxes
    	    	for(int i = 0; i < subdomains.length; i++)
    	    		if(subdomains[i].isSelected())
    	    			domainList.add(subdomains[i].getText());
    	    	
    	    	/*
                if domain list is empty
                (i.e. No checkboxes are selected) show error
                */
    	    	
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

        Scene scene = new Scene(borderPane, 800, 600);
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
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(companyNameId, 0, 0);
        gridPane.add(companyDomainId, 0, 1);
        gridPane.add(companySubdomainId, 0, 2);
        gridPane.add(generalDescriptionId, 0, 3);
        gridPane.add(monthOfVisitId, 0, 4);
        gridPane.add(minCGPAId, 0, 5);
        gridPane.add(addressId, 0, 6);
        gridPane.add(contactId, 0, 7);
        
        gridPane.add(companyNameLabel, 1, 0);
        gridPane.add(companyDomainLabel, 1, 1);
        gridPane.add(companySubdomainLabel, 1, 2);
        gridPane.add(generalDescriptionLabel, 1, 3);
        gridPane.add(monthOfVisitLabel, 1, 4);
        gridPane.add(minCGPALabel, 1, 5);
        gridPane.add(addressLabel, 1, 6);
        gridPane.add(contactLabel, 1, 7);
        
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

       	studentObject.getStudentData(studentObject.sid);
    	Label studentId = new Label(studentObject.sid);
    	Label studentName = new Label(studentObject.name);
    	Label studentAge = new Label(""+studentObject.age);
    	Label studentCGPA = new Label(""+studentObject.CGPA);
    	
    	Label displayId = new Label("ID");
    	Label displayName = new Label("Name");
    	Label displayAge = new Label("Age");
    	Label displayCGPA = new Label("CGPA");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
    	
    	gridPane.add(displayId, 0, 0);
    	gridPane.add(studentId, 1, 0);
    	gridPane.add(displayName, 0, 1);
    	gridPane.add(studentName, 1, 1);
    	gridPane.add(displayAge, 0, 2);
    	gridPane.add(studentAge, 1, 2);
    	gridPane.add(displayCGPA, 0, 3);
    	gridPane.add(studentCGPA, 1, 3);
    	
    	Scene scene = new Scene(new VBox(borderPane,gridPane),800,600);
    	stage.setScene(scene);
    	//To set stage to full screen 
        setFullScreen(stage);
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
        gridPane.add(loginButton, 0, 4); 
        gridPane.add(signUpHyperlink, 1, 5);
        gridPane.add(signupLabel, 0, 5);
        gridPane.add(loginType, 1, 0);
        gridPane.add(typeLabel, 0, 0);

        Scene scene = new Scene(gridPane,800,600);  
      
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
        			//set an error Label here and clear text fields
        			loginPageDisplay(stage);
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
    	
    	Button saveProfileButton = new Button("Save Profile");
    	
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
    	TextField companyNameTextField = new TextField(companyObject.name);
    	TextField companyDomainTextField = new TextField(companyObject.domain);
    	TextField companySubdomainTextField = new TextField(companyObject.subdomain);
    	TextField generalDescriptionTextField = new TextField(companyObject.generalDescription);
    	TextField monthOfVisitTextField = new TextField(companyObject.monthOfVisit);
    	TextField minCGPATextField = new TextField("" + companyObject.minCGPA);
    	TextField addressTextField = new TextField(companyObject.address);
    	TextField contactTextField = new TextField("" + ((Double)companyObject.contact).intValue()); 	
    	
    	Button editProfileButton = new Button("Edit Profile");
    	Button logoutButton = new Button("Logout");
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(20); 
        gridPane.setHgap(20);       
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(companyNameId, 0, 0);
        gridPane.add(companyDomainId, 0, 1);
        gridPane.add(companySubdomainId, 0, 2);
        gridPane.add(generalDescriptionId, 0, 3);
        gridPane.add(monthOfVisitId, 0, 4);
        gridPane.add(minCGPAId, 0, 5);
        gridPane.add(addressId, 0, 6);
        gridPane.add(contactId, 0, 7);
        
        gridPane.add(companyNameTextField, 1, 0);
        gridPane.add(companyDomainTextField, 1, 1);
        gridPane.add(companySubdomainTextField, 1, 2);
        gridPane.add(generalDescriptionTextField, 1, 3);
        gridPane.add(monthOfVisitTextField, 1, 4);
        gridPane.add(minCGPATextField, 1, 5);
        gridPane.add(addressTextField, 1, 6);
        gridPane.add(contactTextField, 1, 7);
        
        gridPane.add(editProfileButton, 0, 10);
        gridPane.add(logoutButton, 1, 10);
        
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
