package application;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

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
//	ArrayList<Branch> branchList;
	String address;
	double contact;
	String monthOfVisit;
	String generalDescription;
	double minCGPA;
	
	static Scanner sc;
	
	/*
	 * Used to get Company profile document from Company collection
	 */
	void getProfile()
	{
		companyId = "apple";
		//Create a mongo client
		MongoClient mongo = new MongoClient("localhost",27017);
		
		//access the database
		MongoDatabase database = mongo.getDatabase("tnpdb");
		
		//access the collection Company
		MongoCollection<Document> collection = database.getCollection("Company");
		
		//Find the Company's Document from the Collection
		BasicDBObject query = new BasicDBObject();
		query.put("companyId", this.companyId);
		FindIterable <Document> iterDoc = collection.find(query);
		MongoCursor <Document> it = iterDoc.iterator();
		
		//Store in a Document object
		Document result = it.next();
		
		//get the company document entries into company attributes
		this.name = (String)result.get("name");
		this.domain = (String)result.get("domain");
		this.subdomain = (String)result.get("subdomain");
		this.monthOfVisit = (String)result.get("monthOfVisit");
		this.generalDescription = (String)result.get("generalDescription");
		this.minCGPA = (Double)result.get("minCGPA");
		this.address = (String)result.get("address");
		this.contact = (Double)result.get("contact");
		
		//display the result on terminal for now
		System.out.println("Name = " + this.name);
		System.out.println("Domain = " + this.domain);
		System.out.println("SubDomain = " + this.subdomain);
		System.out.println("Month Of Visit = " + this.monthOfVisit);
		System.out.println("General Description = " + this.generalDescription);
		System.out.println("minCGPA = " + this.minCGPA);
		System.out.println("Address = " + this.address);
		System.out.println("Contact = " + this.contact);
		
	}
	
	/*
	 * used to update Profile document in Company collection
	 */
	void updateProfile()
	{
		companyId = "apple";
		//Create a mongo client
		MongoClient mongo = new MongoClient("localhost",27017);
		
		//access the database
		MongoDatabase database = mongo.getDatabase("tnpdb");
		
		//access the collection Company
		MongoCollection<Document> collection = database.getCollection("Company");
		
		//Find the Company's Document from the Collection
		Document searchQuery = new Document("companyId",this.companyId);
		
		//get NEW VALUES from the textboxes
		
		Document newDocument = new Document("companyId",this.companyId)
				.append("name", this.name)
				.append("domain", "Computers")
				.append("subdomain", this.subdomain)
				.append("monthOfVisit", this.monthOfVisit)
				.append("generalDescription", this.generalDescription)
				.append("minCGPA", this.minCGPA)
				.append("address", this.address)
				.append("contact", this.contact);
		
		//replace that document in the collection. Update is complex
		collection.replaceOne(searchQuery, newDocument);
	}
	
	void getCompanyList()
	{
		MongoClient mongo = new MongoClient("localhost",27017);
		
		//access the database
		MongoDatabase database = mongo.getDatabase("tnpdb");
		
		//access the collection Company
		MongoCollection<Document> collection = database.getCollection("Company");
		
		//Find the Company's Document from the Collection
		BasicDBObject query = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject("name",1);
		FindIterable <Document> iterDoc = collection.find().projection(new Document("name",1));
		MongoCursor <Document> it = iterDoc.iterator();
		
		//Store in a Document object
//		Document result = it.next();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	}
	
	/*
	 * only used for testing purpose
	 */
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
}
