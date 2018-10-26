package application;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;

//import java.util.Scanner;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.BasicDBObject;

/*
 * Kept optional
class Branch
{
    String location;
    int contact;
    public Branch()
    {
    	location = "";
    	contact = 0;
    }
    public Branch(String l,int c)
    {
        location = l;
        contact = c;
    }
}
*/

public class Company {
	String companyId;
	String name;
	String domain;
	String subdomain;
	String generalDescription;
	String monthOfVisit;
	double minCGPA;
//	ArrayList<Branch> branchList;
	String address;
	double contact;
	
//	static Scanner sc;
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;
	
	Company()
	{
		/*
		 * Constructor. Initializes all variables to default value.
		 * Establishes connection with Company Database.
		 * Accesses the Collection "Company" from the Database
		 */
		companyId = "";
		name = "";
		domain = "";
		subdomain = "";
		generalDescription = "";
		monthOfVisit = "";
		minCGPA = 0;
		address = "";
		contact = 0;
		
		mongo = new MongoClient("localhost",27017);
		database = mongo.getDatabase("tnpdb");
		collection = database.getCollection("Company");
	}
	
	void getProfileById()
	{
		/*
		 * Used to get Company profile document from Company collection
		 */
		
		//Find the Company's Document from the Collection
		BasicDBObject query = new BasicDBObject("companyId", this.companyId);
		
		FindIterable <Document> iterDoc = collection.find(query);
		MongoCursor <Document> it = iterDoc.iterator();
		
		//Store in a Document object
		Document result = it.next();
		
		//get the company document entries into company object's attributes
		this.name = (String)result.get("name");
		this.domain = (String)result.get("domain");
		this.subdomain = (String)result.get("subdomain");
		this.monthOfVisit = (String)result.get("monthOfVisit");
		this.generalDescription = (String)result.get("generalDescription");
		this.minCGPA = (Double)result.get("minCGPA");
		this.address = (String)result.get("address");
		this.contact = (Double)result.get("contact");
	}
	
	void getProfileByName()
	{
		/*
		 * Used to get Company profile document from Company collection
		 */
		
		//Find the Company's Document from the Collection
		BasicDBObject query = new BasicDBObject("name", this.name);
		
		FindIterable <Document> iterDoc = collection.find(query);
		MongoCursor <Document> it = iterDoc.iterator();
		
		//Store in a Document object
		Document result = it.next();
		
		//get the company document entries into company object's attributes
		this.name = (String)result.get("name");
		this.domain = (String)result.get("domain");
		this.subdomain = (String)result.get("subdomain");
		this.monthOfVisit = (String)result.get("monthOfVisit");
		this.generalDescription = (String)result.get("generalDescription");
		this.minCGPA = (Double)result.get("minCGPA");
		this.address = (String)result.get("address");
		this.contact = (Double)result.get("contact");
	}
	
	void updateProfile()
	{
		/*
		 * used to update Profile document in Company collection
		 */

		//Find the Company's Document from the Collection
		Document searchQuery = new Document("companyId",this.companyId);
		
		//insert the NEW VALUES 
		Document newDocument = new Document("companyId",this.companyId)
				.append("name", this.name)
				.append("domain", this.domain)
				.append("subdomain", this.subdomain)
				.append("monthOfVisit", this.monthOfVisit)
				.append("generalDescription", this.generalDescription)
				.append("minCGPA", this.minCGPA)
				.append("address", this.address)
				.append("contact", this.contact);
		
		//replace that document in the collection. Update is complex
		collection.replaceOne(searchQuery, newDocument);
	}
	
	ArrayList<String> getCompanyList()
	{
		/*
		 * Used to get the Company List in order to Display in "Search by Company" page
		 */	
		ArrayList<String> returningCompanyList = new ArrayList<String>();
		
		//Find the Company's Document from the Collection
		BasicDBObject query = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject("name",1);
		FindIterable <Document> iterDoc = collection.find().projection(new Document("name",1));
		MongoCursor <Document> it = iterDoc.iterator();
		
		//Store in a Document object
		while(it.hasNext())
		{
			Document result = it.next();
			returningCompanyList.add(result.get("name").toString());
		}
		return returningCompanyList;
	}
	
	/*
	 * only used for testing purpose
	 *uncomment for testing
	public static void main(String args[])
	{
		sc = new Scanner(System.in);
		int choice;
		Company c = new Company();
		
		while(true)
		{
			System.out.println("\nEnter Choice:\n1: Get Company Profile\n2: Set/Update Company Profile\n3: Display Company List");
			choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				c.getProfile();
				break;
			case 2:
				c.updateProfile();
				break;
			case 3:
				c.getCompanyList();
				break;
			}
		}
	}
	*/
}
