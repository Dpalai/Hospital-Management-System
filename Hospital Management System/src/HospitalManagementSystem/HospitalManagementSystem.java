package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://----------";
    private static final String userName="--";
    private static final String password="*-------";

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            Connection connection= DriverManager.getConnection(url,userName,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);



            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Pateint");
                System.out.println("2. View Pateints");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                int choice=scanner.nextInt();

                switch (choice){
                    case 1:
                        // Add Pateint
                        patient.addPatient();
                        break;
                    case 2:
                        //View Pateints
                        patient.ViewPatient();
                        break;
                    case 3:
                        //view Doctors
                        doctor.ViewDoctor();
                        break;
                    case 4:
                        //Book Appointment
                        bookAppointment(patient,doctor,scanner,connection);
                        break;
                    case 5:
                        //Exit
                        break;
                    default:
                        System.out.println("invalid input !!");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctor doctor,Scanner scanner,Connection connection){
        System.out.println("Enter Pateint Id:");
        int Pateint_id = scanner.nextInt();
        System.out.println("Enter Doctor Id:");
        int Doctor_id = scanner.nextInt();
        System.out.println("Enter Appointment Date: (yyyy-mm-dd)");
        String Appointment_date = scanner.next();

        if(doctor.getDoctorsById(Doctor_id) && patient.getPatientsById(Pateint_id)){
            if(checkDoctorAvailability(Doctor_id,Appointment_date,connection)) {
                String AppointmentQuery = " INSERT INTO appointment (Pid,Did,Appdate) VALUES (?,?,?) ";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(AppointmentQuery);
                    preparedStatement.setInt(1, Pateint_id);
                    preparedStatement.setInt(2, Doctor_id);
                    preparedStatement.setString(3, Appointment_date);
                    int rowsaffected = preparedStatement.executeUpdate();

                    if (rowsaffected > 0) {
                        System.out.println("Appointment Booked ");
                        System.out.println(" ");
                    } else {
                        System.out.println("failed to Book Appointment ");
                        System.out.println(" ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor Already Booked");
                System.out.println(" ");
            }
        }else{
            System.out.println("either Doctor or Pateint not exist !!");
        }

    }

    public static boolean checkDoctorAvailability(int Doctor_id,String Appointment_date,Connection connection){
        String query = "SELECT COUNT(*) FROM appointment WHERE Did= ? && Appdate= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,Doctor_id);
            preparedStatement.setString(2,Appointment_date);
            ResultSet resultSet=preparedStatement.executeQuery();


            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        return false;
    }

}
