package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient(){
        System.out.println("enter Patient Name : ");
        String Name=scanner.next();
        System.out.println("enter Patient Age : ");
        int Age=scanner.nextInt();
        System.out.println("enter Patient Gender : ");
        String Gender =scanner.next();

        try {
            String query = " INSERT INTO pateints(Name,Age,Gender) VALUES (? ,? ,? )";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,Name);
            preparedStatement.setInt(2,Age);
            preparedStatement.setString(3,Gender);
            int affectedrows = preparedStatement.executeUpdate();

            if(affectedrows>0){
                System.out.println("Patient added succesfully ");
                System.out.println(" ");
            }else{
                System.out.println("Patient not added ");
                System.out.println(" ");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void ViewPatient(){
        try{
            String query = "SELECT * FROM pateints";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Pateints");
            System.out.println("+-----+-----------------+-------+----------+");
            System.out.println("| id  + Name            + Age   + Gender   |");
            System.out.println("+-----+-----------------+-------+----------+");
            while ((resultSet.next())){
                int id=resultSet.getInt("id");
                String Name=resultSet.getString("Name");
                int Age=resultSet.getInt("Age");
                String Gender=resultSet.getString("Gender");
                System.out.printf("| %-3s | %-15s | %-5s | %-8s |\n",id,Name,Age,Gender);
                System.out.println("+-----+-----------------+-------+----------+");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean getPatientsById(int id){

        try{
            String query="SELECT * FROM pateints WHERE id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
