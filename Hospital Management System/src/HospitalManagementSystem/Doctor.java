package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {

    private Connection connection;

    public Doctor(Connection connection){
        this.connection=connection;
    }

    public void ViewDoctor(){
        String query= "SELECT * FROM Doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctors");
            System.out.println("+-----+-----------------+------------------+");
            System.out.println("| id  + Name            + Specialization   |");
            System.out.println("+-----+-----------------+------------------+");
            while ((resultSet.next())){
                int id=resultSet.getInt("id");
                String Name=resultSet.getString("Name");
                String Specialization=resultSet.getString("Specialization");
                System.out.printf("|%-5s|%-17s|%-18s |\n",id,Name,Specialization);
                System.out.println("+-----+-----------------+------------------+");
            }
        }catch ( SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorsById(int id){
        String query= "SELECT * FROM Doctors WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
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

