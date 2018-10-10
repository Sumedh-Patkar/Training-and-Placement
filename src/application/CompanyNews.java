package application;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;

import java.util.Scanner;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class CompanyNews {
String companyId;
String companyName;
String news;

//static Scanner sc;
MongoClient mongo;
MongoDatabase database;
MongoCollection<Document> collection;

	CompanyNews() 
	{
		// TODO Auto-generated constructor stub
	
		companyId = "";
		companyName = "";
		news = "";
		
		mongo = new MongoClient("localhost",27017);
		database = mongo.getDatabase("tnpdb");
		collection = database.getCollection("CompanyNews");

	}
 
 
	ArrayList<String> getCompanyNewsList()
	{
		/*
		 * Used to get the News List in order to Display in News Section in home page
		 */	
		ArrayList<String> newsList = new ArrayList<String>();
		
		//Find the Company's News Document from the Collection
//		BasicDBObject query = new BasicDBObject();
//		BasicDBObject fields = new BasicDBObject("name",1);
		FindIterable <Document> iterDoc = collection.find().projection(new Document("news",1));
		MongoCursor <Document> it = iterDoc.iterator();
		
		//Store in a Document object
		while(it.hasNext())
		{
			Document result = it.next();
			newsList.add(result.get("news").toString());
		}
		return newsList;
	}
	
	public void setData(String companyId,String companyName,String news)
	{
		Document document = new Document("companyId", companyId)
								.append("companyName", companyName)
								.append("news", news);
		
		collection.insertOne(document);
	}


}
