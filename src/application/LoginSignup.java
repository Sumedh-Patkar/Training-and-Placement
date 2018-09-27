package application;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient; 
import com.mongodb.client.FindIterable;

public class LoginSignup
{
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;

	LoginSignup()
	{
		mongo = new MongoClient( "localhost" , 27017 );
		database = mongo.getDatabase("tnpdb");
	}

	public boolean login(String username,String password,String loginType)
	{
		//access the collection according to Login-Type
		collection = database.getCollection(loginType + "Login");		//example "StudentLogin"
		
		//check if username already exists
		BasicDBObject query = new BasicDBObject("username", username);
		FindIterable<Document> iterDoc = collection.find(query);
		MongoCursor<Document> it = iterDoc.iterator();

		if(it.hasNext() == true)	//if username exists
		{
			//check if password matches
			Document result = it.next();
			if(result.get("password").equals(password))
			{
				//give login if password matches
				return true;
			}
			else
				return false;	//wrong password
		}
		else
			return false;		//username doesn't exist
	}
	
	/*
	 * THIS function RETURNS "TRUE" if username EXISTS
	 */
	public boolean userNameExists(String username,String password)
	{       
		collection = database.getCollection("StudentLogin");
		
		BasicDBObject query = new BasicDBObject("username", username);
		FindIterable<Document> iterDoc = collection.find(query);
		MongoCursor<Document> it = iterDoc.iterator();
		
		return it.hasNext();
	}
	
	public void signUp(String username,String password)
	{       
		collection = database.getCollection("StudentLogin");
		
		//insert user into StudentLogin table
		Document person = new Document("username",username)
				.append("password", password);

		collection.insertOne(person);
	}

    /*
     * only used for testing purpose
     *uncomment for testing
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
     sc.close();
     }
     */
}
