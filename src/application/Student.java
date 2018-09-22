package application;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

import java.util.Scanner;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Student
{
    String sid,name;
    double age,CGPA;         // double taken on purpose
    
    //constructor
    public Student()
    {
        sid = "";
        name = "";
        age=0;
        CGPA=0;
    }
    public Student(String sid,String name,double age,double cgpa)
    {
        this.sid = sid;
        this.name = name;
        this.age=age;
        this.CGPA=cgpa;
    }
    
    //for actually storing the student data in the database
    //this function will be called by student object in the UI class
    public void setStudentData()
    {
    	MongoClient mongo = new MongoClient( "localhost" , 27017 );
    	MongoDatabase db = mongo.getDatabase("tnpdb");
    
    	MongoCollection<Document> collection = db.getCollection("Student");
    	Document document = new Document("sid", sid) 
    			.append("name", name)
    			.append("age", age) 
    			.append("CGPA", CGPA);  
    	collection.insertOne(document); 
    	System.out.println("Document inserted successfully");
    }
    
    //will be used for displaying the student's profile
    public  void getStudentData(String sid)
    {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongo.getDatabase("tnpdb");
        
        MongoCollection<Document> collection = db.getCollection("Student");
        
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("sid" , sid);
        FindIterable<Document> iterDoc = collection.find(whereQuery); 
        MongoCursor<Document> it = iterDoc.iterator();
      
        /*
         * THE COMMENTED OUT PART IS UNNECESSARY as Student gets a login only when His profile exists
         */
//        if(it.hasNext() == false)
//        {
//        	System.out.println("Sorry, Cursor cannot find doc for this student!");
//        }
//        else
//        {
//        while (it.hasNext()) 
//        {
        Document result = it.next();
        this.sid  = result.get("sid").toString();
        this.name = result.get("name").toString();
        this.age  =  (Double)result.get("age");
        this.CGPA = (Double)result.get("CGPA");
//        }
    }            
//    }
    
    /*
     * only used for testing purpose
     *uncomment for testing
     *
    public static void main(String[] args) {
    
        Scanner sc= new Scanner(System.in);
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongo.getDatabase("tnpdb");
        
        MongoCollection<Document> collection = db.getCollection("student");
        
        
        String sid,name;
        int age,CGPA;
        int choice;
        Student s = new Student();
        
        do {
        System.out.println("Enter choice:\n1.set data\n2.get data of particular student \n10.Exit");
        choice=sc.nextInt();
        switch(choice)
        {
        case 1:
            System.out.println("Enter sid,name,age,cgpa");
            sid = sc.next();
            name = sc.next();
            age = sc.nextInt();
            CGPA = sc.nextInt();
            Student s1=new Student(sid,name,age,CGPA);
            s1.setStudentData();
            break;
        case 2:
        	System.out.println("Enter sid of student to be searched");
            sid=sc.next();
            s.getStudentData(sid);
            break;
        }
        }while(choice!=10);
    }
    */
}
