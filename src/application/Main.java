package application;

import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.geometry.*;

 class text1
{
    String name;
    public void setText1(String x)
    {
        this.name = x;
    }

    public String getText1()
    {
        return this.name;
    }
}


public class Main extends Application
{
    Label check ;
    Label check1;
    TextField c1;
    TextField c2;
    Scene s1;
    Scene s2;
    AnchorPane x;
    AnchorPane y;
    Button qw;
    Button wq;
    text1 t;

    public void signUp(Stage stage)
    {
        Hyperlink loginHyperlink = new Hyperlink();
        
        loginHyperlink.setText("Login");
        
        loginHyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                loginPageDisplay(stage);
            }
        }); 
            Label userSignUpLabel = new Label("User ID");       
            
            Label passwordLabel = new Label("Password"); 
            
            Label confirmPasswordLabel = new Label("Confirm Password");

            Label nameLabel = new Label("Name");

            Label CGPALabel = new Label("CGPA");

            TextField nameField = new TextField();

            TextField CGPAField = new TextField();
    
            TextField userSignUpTextField = new TextField();       
              
            PasswordField userPasswordPasswordField = new PasswordField();  
             
            PasswordField confirmPasswordField = new PasswordField();

            Button SignUpButton = new Button("Sign Up"); 
            
            GridPane gridPaneSignUp = new GridPane();    
            
            gridPaneSignUp.setMinSize(400, 200); 
               
            gridPaneSignUp.setPadding(new Insets(10, 10, 10, 10)); 
              
            gridPaneSignUp.setVgap(5); 
            gridPaneSignUp.setHgap(5);       
            
            gridPaneSignUp.setAlignment(Pos.CENTER); 
             
            SignUpButton.setOnAction(new EventHandler<ActionEvent>() 
            {

                public void handle(ActionEvent event)
                {
                    dummyPageDisplay(stage);
                }

            });

            gridPaneSignUp.add(userSignUpLabel, 0, 0); 
            gridPaneSignUp.add(userSignUpTextField, 1, 0); 
            gridPaneSignUp.add(passwordLabel, 0, 1);       
            gridPaneSignUp.add(userPasswordPasswordField, 1, 1);
            gridPaneSignUp.add(confirmPasswordField, 1, 2);
            gridPaneSignUp.add(confirmPasswordLabel, 0, 2); 
            gridPaneSignUp.add(nameLabel, 0, 3);
            gridPaneSignUp.add(nameField, 1, 3);
            gridPaneSignUp.add(CGPALabel, 0, 4);
            gridPaneSignUp.add(CGPAField, 1, 4);
            gridPaneSignUp.add(SignUpButton, 0,5); 
            gridPaneSignUp.add(loginHyperlink, 1, 5);

            Scene scene = new Scene(gridPaneSignUp);  
      
            stage.setTitle("Grid Pane Example"); 
               
            stage.setScene(scene); 
               
            stage.show();

    }


    public void dummyPageDisplay(Stage stage)
    {
        BorderPane borderPane = new BorderPane();

        MenuBar menu = new MenuBar();

        Menu profile = new Menu("Profile");
        Menu home = new Menu("Home");

        MenuItem signout = new MenuItem("Signout");

        profile.getItems().addAll(signout);

        menu.getMenus().addAll(profile,home);

        borderPane.setTop(menu);

        Scene scene = new Scene(borderPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void loginPageDisplay(Stage stage)
    {
     Hyperlink h = new Hyperlink();
        h.setText("SignUp");
        h.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                signUp(stage);
            }
        });   

        Label signupLabel = new Label("New user");

      Label userLoginLabel = new Label("User ID");       
      
      Label passwordLabel = new Label("Password"); 
	  
      TextField userLoginTextField = new TextField();       
             
      PasswordField userPasswordPasswordField = new PasswordField();  
       
      Button loginButton = new Button("Login"); 
      
      GridPane gridPane = new GridPane();    
      
      gridPane.setMinSize(400, 200); 
        
      gridPane.setPadding(new Insets(10, 10, 10, 10)); 
      
      gridPane.setVgap(5); 
      gridPane.setHgap(5);       
       
      gridPane.setAlignment(Pos.CENTER); 
       
      gridPane.add(userLoginLabel, 0, 0); 
      gridPane.add(userLoginTextField, 1, 0); 
      gridPane.add(passwordLabel, 0, 1);       
      gridPane.add(userPasswordPasswordField, 1, 1); 
      gridPane.add(loginButton, 0, 2); 
      gridPane.add(h, 1, 3);
      gridPane.add(signupLabel, 0, 3);
 
      Scene scene = new Scene(gridPane);  
      
      stage.setTitle("Grid Pane Example"); 
          
      stage.setScene(scene);    
     
      stage.show();

      loginButton.setOnAction(new EventHandler<ActionEvent>() 
      {
        //Code to check all login credentials are filled
        public void handle(ActionEvent event)
        {
            dummyPageDisplay(stage);
        }
        
      });

    }


    


    

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        loginPageDisplay(primaryStage);
    }

    public static void main(String []args)
    {
        launch(args);
    }
}