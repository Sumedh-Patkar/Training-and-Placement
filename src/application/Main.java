package application;



import javafx.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
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
import javafx.geometry.*;

public class Main extends Application
{
    Student studentObject;
    LoginSignup loginSignUpObject;
    Company companyObject;

    public void signUpPageDisplay( final Stage stage)
    {
        Hyperlink loginHyperlink = new Hyperlink();

        loginHyperlink.setText("Login");

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
                    	homePageDisplay(stage);
                    	studentObject.setStudentData();
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
            

            Scene scene = new Scene(gridPaneSignUp);  
      
            stage.setTitle("Grid Pane Example"); 
               
            stage.setScene(scene); 
               

            stage.show();

    }

    public void searchByDomainPageDisplay(Stage stage)
    {
        	
        	//code for menu bar
        	MenuBar leftBar = new MenuBar();
        	Menu profile = new Menu("Profile");
            Menu home = new Menu("Home");
            Menu search = new Menu("Search");
            MenuItem searchByCompany = new MenuItem("Search by company name");
            MenuItem searchByDomain = new MenuItem("Search by domains");
            MenuItem signout = new MenuItem("Signout");
            MenuItem myProfile = new MenuItem("My Profile");
            profile.getItems().addAll(myProfile,signout);
            search.getItems().addAll(searchByDomain,searchByCompany);
            leftBar.getMenus().addAll(home,search);
            MenuBar rightBar = new MenuBar();
            rightBar.getMenus().addAll(profile);
            Region spacer = new Region();
            spacer.getStyleClass().add("menu-bar");
            HBox.setHgrow(spacer, Priority.SOMETIMES);
            HBox menubars = new HBox(leftBar, spacer, rightBar);

            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menubars);

        	
        	
        	//main code for this function
        	CheckBox subdomains[] = new CheckBox[3];
        	subdomains[0] = new CheckBox("Artificial Intelligence");
        	subdomains[1] = new CheckBox("Deep Learning");
        	subdomains[2] = new CheckBox("Computer Vision");
        	

        	Label Domain1 = new Label("Domain1");
         
           
            Button searchCompanies = new Button("Search"); 
          
            GridPane gridPane = new GridPane();    
          
            gridPane.setMinSize(400, 200); 
            gridPane.setPadding(new Insets(10, 10, 10, 10)); 
            gridPane.setVgap(20); 
            gridPane.setHgap(20);       
            gridPane.setAlignment(Pos.CENTER); 
           
            gridPane.add(Domain1,0 ,1 );
            gridPane.add(subdomains[0],1 ,2 );
            gridPane.add(subdomains[1], 1, 3);
            //gridPane.add(subdomains[2], 1, 4);
            
            //gridPane.add(Domain1,0 ,6 );
            //gridPane.add(subdomains[0],1 ,7 );   //needs diff array elements
            //gridPane.add(subdomains[1], 1, 8);
            gridPane.add(subdomains[2], 1, 9);
            
            
            gridPane.add(searchCompanies,2,50);
            
           /* VBox a = new VBox(borderPane,gridPane);
            ScrollPane scrollPane = new ScrollPane(a);
            scrollPane.add(a);
           */ 
            
           /* Pane mainPane = new Pane();
            mainPane.getChildren().addAll(borderPane,mainPane);*/
     
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(gridPane);
            
            // Pannable.
            scrollPane.setPannable(true);
          
            
            
            Scene scene = new Scene(new VBox(borderPane,scrollPane));  
          
            stage.setTitle("Training-and-Placement"); 
              
            stage.setScene(scene);
         
            stage.show();

            searchCompanies.setOnAction(new EventHandler<ActionEvent>()
            {
            
            	public void handle(ActionEvent event)
            	{
            		//companylistPageDisplay(stage);
            	}
            });
        	
       
    	
    }
    
    public void searchByCompanyPageDisplay(final Stage stage)
    {
    	
    	
    }
    
    public void homePageDisplay(final Stage stage)
    {
    	MenuBar leftBar = new MenuBar();
    	Menu profile = new Menu("Profile");
        Menu home = new Menu("Home");
        Menu search = new Menu("Search");
        MenuItem searchByCompany = new MenuItem("Search by company name");
        MenuItem searchByDomain = new MenuItem("Search by domains");
        MenuItem signout = new MenuItem("Signout");
        MenuItem myProfile = new MenuItem("My Profile");
        profile.getItems().addAll(myProfile,signout);
        search.getItems().addAll(searchByDomain,searchByCompany);
        leftBar.getMenus().addAll(home,search);
        MenuBar rightBar = new MenuBar();
        rightBar.getMenus().addAll(profile);
        Region spacer = new Region();
        spacer.getStyleClass().add("menu-bar");
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menubars = new HBox(leftBar, spacer, rightBar);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menubars);
        
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

        Scene scene = new Scene(borderPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void companyProfilePageDisplay(Stage stage)
    {
    	Label companyVision = new Label("Vision");
    	
    	Label companyDomains = new Label("Domains");
    	
    	Label companySubdomains = new Label("Sub-Domains");
    	
    	Label address = new Label("Address");
    }
    
    
    public void loginPageDisplay(final Stage stage)
    {

        ObservableList<String> options = 
        	FXCollections.observableArrayList(
    		"Student",
        	"Company",
        	"Admin"
    	);
        
        final ComboBox<String> loginType = new ComboBox<String>(options);

        Hyperlink signUpHyperlink = new Hyperlink("SignUp");
//        signUpHyperlink.setText("SignUp");
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

 
        Scene scene = new Scene(gridPane);  
      
        stage.setTitle("Training-and-Placement"); 
          
        stage.setScene(scene);
     
        stage.show();

        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
        //Code to check all login credentials are filled
        	public void handle(ActionEvent event)
        	{
        		boolean result=loginSignUpObject.login(userLoginTextField.getText().toString() ,
        											userPasswordPasswordField.getText().toString() ,
        											(String)loginType.getValue());
        		if(result)
        		{
        			homePageDisplay(stage);
        		}
        		else
        		{
        			//set an error Label here and clear text fields
        			loginPageDisplay(stage);
        		}
        	}
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

    	loginSignUpObject = new LoginSignup();
    	companyObject = new Company();

        loginPageDisplay(primaryStage);
    }

    public static void main(String []args)
    {
        launch(args);
    }
}
