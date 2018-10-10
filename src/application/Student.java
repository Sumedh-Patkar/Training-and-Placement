package application;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

//import java.util.Scanner;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Student
{
    String sid;
    String name;
    double age;
    double CGPA;         // double taken on purpose
    double rollNo;
    String college;
    
    MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;
	
    public Student()
    {
    	/*
    	 * Constructor. Initializes variables to default values.
    	 * Establishes connection with the database.
    	 * Accesses "Student" collection from the Database. 
    	 */
        sid = "";
        name = "";
        age=0;
        CGPA=0;
        rollNo = 0;
        college = "";
        
		mongo = new MongoClient("localhost",27017);
		database = mongo.getDatabase("tnpdb");
		collection = database.getCollection("Student");
    }

    public Student(String sid,String name,double age,double cgpa, double rollNo, String college)
    {
        this.sid = sid;
        this.name = name;
        this.age=age;
        this.CGPA=cgpa;
        this.rollNo=rollNo;
        this.college=college;
        
        mongo = new MongoClient("localhost",27017);
		database = mongo.getDatabase("tnpdb");
		collection = database.getCollection("Student");
    }
    
    public void updateProfile()
    {
    	/*
		 * used to update Profile document in Student collection
		 */
    	
        Document searchQuery = new Document("sid", this.sid);

        Document newDocument = new Document("sid", this.sid) 
                .append("name", this.name)
                .append("age", this.age) 
                .append("CGPA", this.CGPA)
                .append("rollNo", this.rollNo)
                .append("college", this.college);  
        
        //replace that document in the Student Collection
        collection.replaceOne(searchQuery,newDocument);    
    }

    //for actually storing the student data in the database
    //this function will be called by student object in the UI class
    public void setStudentData()
    {	
        Document document = new Document("sid", sid) 
    			.append("name", name)
    			.append("age", age) 
    			.append("CGPA", CGPA)
    			.append("rollNo", rollNo)
    			.append("college", college);  
    	
        collection.insertOne(document); 
    }
    
    //will be used for displaying the student's profile
    public  void getStudentData()
    {   
        BasicDBObject query = new BasicDBObject("sid" , sid);
        FindIterable<Document> iterDoc = collection.find(query); 
        MongoCursor<Document> it = iterDoc.iterator();
     
        Document result = it.next();
        this.name = result.get("name").toString();
        this.age  =  (Double)result.get("age");
        this.CGPA = (Double)result.get("CGPA");
        this.rollNo = (Double)result.get("rollNo");
        this.college = result.get("college").toString();

    }            
    
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
