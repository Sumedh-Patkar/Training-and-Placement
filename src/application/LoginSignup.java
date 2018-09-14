package application;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;  
import com.mongodb.client.FindIterable;

public class LoginSignup {
	String username;
	String password;
	String type;
	static Scanner sc;
	
	void login()
	{	
		// Creating a Mongo client 
	    MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
	    
	    // Accessing the database 
	    MongoDatabase database = mongo.getDatabase("tnpdb"); 
  
	    
	    //check type
	    System.out.println("Enter Type");
	    type = sc.next();
	    
	    MongoCollection<Document> collection;
	    if(type.equals("Student"))
	    //access the Collection
	    	collection = database.getCollection("StudentLogin");
	    else
	    	collection = database.getCollection("CompanyLogin");
		
	    //take username password
	    System.out.println("Enter Username");
	    username = sc.next();
	    
	    System.out.println("Enter Password");
	    password = sc.next();
	    
	    //check if username already exists
	    
	    BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("username", username);
	    FindIterable<Document> iterDoc = collection.find(whereQuery);
	    MongoCursor<Document> it = iterDoc.iterator();
	    
	    if(it.hasNext() == true)
	    {
	    //check for validity
	    	Document result = it.next();
	    	if(result.get("password").equals(password))
	    		//give login if password matches
	    		System.out.println("Welcome to tnp!\n");
	    	else
	    		System.out.println("Hack detected!\n");
	    }
	    else
	    	//username doesn't exist
	    	System.out.println("Username doesn't exist.Please Sign Up");
	}

	void signUp()
	{	
		// Creating a Mongo client 
	    MongoClient mongo = new MongoClient( "localhost" , 27017 );  
	    
	    // Accessing the database 
	    MongoDatabase database = mongo.getDatabase("tnpdb");
	    	    
	    //check type
	    System.out.println("Enter Type");
	    type = sc.next();
	    
	    MongoCollection<Document> collection;
	    //access the collection
	    if(type.equals("Student"))
	    	collection = database.getCollection("StudentLogin");
	    else
	    	collection = database.getCollection("CompanyLogin");
		
	    //take username password
	    System.out.println("Enter Username");
	    username = sc.next();
	    
	    System.out.println("Enter Password");
	    password = sc.next();
	    
	    //check if username already exists
	    
	    BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("username", username);
	    FindIterable<Document> iterDoc = collection.find(whereQuery);
	    MongoCursor<Document> it = iterDoc.iterator();
    
	    if(it.hasNext() == false)
	    {
	    //insert user into student table
	    Document person = new Document("username",username)
	    		.append("password", password);
	    
	    collection.insertOne(person);
	    }
	    else
	    {
	    	System.out.println("Username Already exists");
	    }
	    
	}
	
	void logout()
	{
		//show login page again
		
	}
	
	//only for testing
	public static void main(String []args)
	{
		LoginSignup l = new LoginSignup();
		sc = new Scanner(System.in);
		
		int choice;
    	while(true)
    	{
    		System.out.println("\n\nEnter Choice:\n1: Login\n2: SignUp\n");
    		choice = sc.nextInt();
    		
       		switch(choice)
       		{
       		case 1:
       			l.login();
       			break;
       		case 2:
       			l.signUp();
       			break;
    		}
    	}
//    	sc.close();
	}
}