package my;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class db_conn {
    Connection c=null;
    boolean r=false;
    String er,query;
    ArrayList a=new ArrayList();
    
    
    String url="jdbc:mysql://localhost:3306";
    String db_Id="root";
    String db_Pass="";
    String db_name="ssss";
    
    //check connectivity
    public boolean check_conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection(url,db_Id,db_Pass);
            r=true;
        }
        catch(CommunicationsException e){
            er="Database Comunication error";
        }
        catch(SQLException|ClassNotFoundException e){
            er="Error is:   "+e;
        }
        return r;
    }
    
//checking database and auto creation
    public boolean check_db(){
       try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection(url,db_Id,db_Pass);
            Statement ps=c.createStatement(); 
            ResultSet rs=ps.executeQuery("show databases"); 
            while(rs.next()){
                if(rs.getString("Database").equals(db_name)){
                      r=true;
                      break;
                }
            }
            if(r){
                Statement pst0=c.createStatement(); 
                pst0.executeUpdate("create database "+db_name); 
            
                    Statement pst1=c.createStatement(); 
                    pst1.executeUpdate("use "+db_name);
            
                    Statement pst2=c.createStatement(); 
                    pst2.executeUpdate("create table books(name varchar(25),author varchar(25))");
                    pst2.executeUpdate("insert into books values('Select Book','Select Author')");
                    
                    Statement pst3=c.createStatement(); 
                    pst3.executeUpdate("create table admin(id int PRIMARY KEY AUTO_INCREMENT,Name varchar(50),contact int(10),username varchar(30),password varchar(50))");
                    pst3.executeUpdate("insert into admin values(1,'Payal',1234567890,'admin','admin')");
            
                    Statement pst4=c.createStatement(); 
                    pst4.executeUpdate("create table db(Name varchar(50),ID int PRIMARY KEY AUTO_INCREMENT,Contact int,Address varchar(50),Book_Name varchar(50),author varchar(50))");
             
                    Statement pst5=c.createStatement(); 
                    pst5.executeUpdate("create table take(Name varchar(50),ID int PRIMARY KEY AUTO_INCREMENT,Contact int,Address varchar(50),Book_Name varchar(50),author varchar(50))");
                    
                    c=DriverManager.getConnection(url+"/"+db_name,db_Id,db_Pass);
                    r=true;     
                } 
       }
       catch(SQLException |ClassNotFoundException e){
           //r=false;
           //er="Error is:   "+e;
           JOptionPane.showMessageDialog(null,"Error is:   "+e);
       }
         return r; 
    }

    //for giving conection once you enterd in softwarte
    Connection get_conn(){
        Connection co=null;
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                co=DriverManager.getConnection(url+"/"+db_name,db_Id,db_Pass);
            }
            catch(CommunicationsException e){
                er="Database Comunication error";
            }
            catch(SQLException |ClassNotFoundException e){
            }
            return co;
    }
    String error(){
        return er;
    }
   Boolean update(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection(url+"/"+db_name,db_Id,db_Pass);
            PreparedStatement ps=c.prepareStatement(query);
            for(int i=0;i<a.size();i++)
                ps.setString(i+1,""+a.get(i));
            ps.executeUpdate();
            
            r=true;
        }
        catch(SQLException |ClassNotFoundException e){
            r=false;
            er=""+e;
        }
        return r;
    }
    ResultSet select(){
        ResultSet rs=null;
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection(url+"/"+db_name,db_Id,db_Pass);
            PreparedStatement ps=c.prepareStatement(query);
            if(!a.isEmpty()){
            for(int i=0;i<a.size();i++)
                ps.setString(i+1,""+a.get(i));
            }
            rs=ps.executeQuery();
            r=true;
        }
        catch(SQLException |ClassNotFoundException e){
            r=false;
            er=""+e;
        }
        return rs;
    }  
    
    
    boolean close()
    {   
        boolean c_r=false;
        try {
            c.close();
            c_r=true;
        } 
        catch (SQLException e) {
            er=""+e;
        }
        return c_r;
    }
}
