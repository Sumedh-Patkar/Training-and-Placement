package application;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;


import java.util.Scanner;
import org.bson.Document;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class Student {


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
        
        MongoCollection<Document> collection = db.getCollection("student");
        
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("sid" , sid);
        FindIterable<Document> iterDoc = collection.find(whereQuery); 
        
        //FindIterable<Document> iterDoc = collection.find(); 
        
             if(iterDoc == null)
             {
                 System.out.println("Sorry, Document for this student not present!");
             }
             else 
             {
                     // Getting the iterator 
                    MongoCursor<Document> it = iterDoc.iterator();
                    if(it.hasNext() == false)
                    {
                         System.out.println("Sorry, Cursor cannot find doc for this student!");
                    }
                    else
                    {
                   
                        while (it.hasNext()) 
                        {   
                            /*System.out.println(it.next()); 
                            System.out.println("");*/

                            
                            Document result = it.next();
                            this.sid  = result.get("sid").toString();
                            this.name = result.get("name").toString();
                            this.age  =  (Integer)result.get("age");
                            this.CGPA = (Integer)result.get("CGPA");
                            
                            
                            //the println statements further are only for testing purpose
                            System.out.println("Profile of Student:");
                            System.out.println();
                            System.out.println("SID : "+ sid);
                            System.out.println("NAME : "+ name);
                            System.out.println("AGE : "+ age);
                            System.out.println("CGPA : "+ CGPA);
                            System.out.println("\n\n\n");
                        }
                        
                    }
                        
             }
    }
  
    
    
    //getallstudentdata() is a temporary function.
    //to be removed afterwards
    public  void getAllStudentData()
    {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongo.getDatabase("tnpdb");
        
        MongoCollection<Document> collection = db.getCollection("student");
        
        FindIterable<Document> iterDoc = collection.find(); 
        
             if(iterDoc == null)
             {
                 System.out.println("Sorry, Document for this student not present!");
             }
             else 
             {
                     // Getting the iterator 
                    MongoCursor<Document> it = iterDoc.iterator();
                    if(it.hasNext() == false)
                    {
                         System.out.println("Sorry, Cursor cannot find doc for this student!");
                    }
                    else
                    {
                   
                        while (it.hasNext()) 
                        {   
                            /*System.out.println(it.next()); 
                            System.out.println("");*/

                            
                            Document result = it.next();
                            //this.sid = result.get("sid").toString();  //alternate way
                            this.sid = result.getString("sid");
                            this.name = result.get("name").toString();
                            this.age =  (Integer) result.get("age");
                            this.CGPA = (Integer) result.get("CGPA");

                            System.out.println("Profile of Student:");
                            System.out.println();
                            System.out.println("SID : "+ sid);
                            System.out.println("NAME : "+ name);
                            System.out.println("AGE : "+ age);
                            System.out.println("CGPA : "+ CGPA);
                            System.out.println("\n");
                        }
                        System.out.println("\n\n");
                        
                    }
                        
             }
    }
    
    
    
    // Main funtion to be removed afterwards
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
        System.out.println("Enter choice:\n1.set data\n2.get data of all students \n3.get data of particular student \n10.Exit");
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
            System.out.println("Student Data :");
            s.getAllStudentData();
            break;
        case 3: 
            System.out.println("Enter sid of student to be searched");
            sid=sc.next();
            s.getStudentData(sid);
            break;
        }
        }while(choice!=10);
}
}