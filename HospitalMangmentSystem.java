package Hospital;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.crypto.spec.PBEKeySpec;

public class hospital{
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username= "root";
    private static final String password= "Lad@1312";

    public static void main(String[] args) {
        
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        Scanner scanner=new Scanner(System.in);


        try {
            Connection connection=DriverManager.getConnection(url, username, password);
            patient p=new patient(connection, scanner);
            doctors d=new doctors(connection ,scanner);

            while (true){
                System.out.println("Hospital Mangement System");
                 System.out.println("1. Add Patient");
                 System.out.println("2. view Patients");
                 System.out.println("3. view Doctors");
                 System.out.println("4. Book Apointments");
                 System.out.println("5. exit");
                 System.out.println("Enter your choice");

                 int choice =scanner.nextInt();
                 switch (choice) {
                    case 1:
                    // add patient
                    p.addpatient();
                    System.out.println();
                    break;
                    case 2:
                    p.viewpatient();
                    System.out.println();
                    break;

                    // view patient
                    case 3:
                    d.viewdoctor();
                    System.out.println();
                    break;

                    // view doctors
                    case 4:

                    bookAppointment(p, d, connection, scanner);
                    System.out.println();
                    break;


                    // book apointment
                    case 5:
                    System.out.println("THANK YOU FOR USING HOSPITAL MANGEMENT SYSTEM !!!");
                    return;
                        
                 
                    default:
                        System.out.println("enter valid choice");

                    
                 }


            }


        } catch (SQLException e) {
            
            e.printStackTrace();
            // TODO: handle exception
        }


    }

    public static void bookAppointment(patient patient, doctors doctor, Connection connection, Scanner scanner){
        System.out.print("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if(patient.getpatientbyid(patientId) && doctor.getdoctorbyid(doctorId)){
            if(checkDoctorAvailability(doctorId, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked!");
                    }else{
                        System.out.println("Failed to Book Appointment!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }




    public static boolean checkDoctorAvailability(int doctorid, String appointmentdate, Connection connection ){

        String query =" SELECT COUNT(*) FROM appointment WHERE doctor_id =? AND appointment_date =?";
        try {
            
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorid);
            preparedStatement.setString(2, appointmentdate);
            ResultSet resultSet=preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count=resultSet.getInt(1);

                if (count==0) {
                    return true;
                }else{ 
                    return false;
                }

                
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return false;

    }

    } 


