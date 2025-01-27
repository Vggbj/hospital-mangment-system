package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class doctors {
    private Connection connection;
    //private Scanner scanner;
    public doctors(Connection connection,Scanner scanner){
        this.connection=connection;
      //  this.scanner=scanner;
    

    }
    //view patient
    public void viewdoctor(){
               String query = "select * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+---------------------+---------------------+");
            System.out.println("| Doctor Id  | Name                | Specialization      |");
            System.out.println("+------------+---------------------+---------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
             //
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-19s | %-19s |\n", id, name, specialization);
                System.out.println("+------------+---------------------+---------------------+");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        
        }
        ////
    }

    //get doctor by id
    public boolean getdoctorbyid(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
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
