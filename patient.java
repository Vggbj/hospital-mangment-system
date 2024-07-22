package Hospital;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class patient {
    private Connection connection;
    private Scanner scanner;
    public patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addpatient(){
        System.out.println("Enter Patient Name: ");
        String name=scanner.next();
        System.out.println("Enter Patient Age : ");
        int age =scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender =scanner.next();

        try {
            String query="insert into patient(name, age, gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows=preparedStatement.executeUpdate();

            if (affectedRows>0) {
                System.out.println("Patient added succesfully!! ");

            }
            else{
                System.out.println("Failed to add Patient ");
            }



        } catch (SQLException e) {
            e.printStackTrace();
            
        }


    }
    //view patient
    public void viewpatient(){
               String query = "select * from patient";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("patient: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        
        }
        ////
    }

    //get patient by id
    public boolean getpatientbyid(int id){
        String query = "SELECT * FROM patient WHERE id = ?";
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
