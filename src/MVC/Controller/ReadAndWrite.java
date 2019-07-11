package MVC.Controller;

import Commens.Constants;
import MVC.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ReadAndWrite {
    private static Gson gson=new Gson();
    // Save to file Utility
    static void WriteUsersToFile(ArrayList<User> users) {
        File courseFile = new File(Constants.dataFileLocation);
        if (!courseFile.exists()) {
            try {
                File directory = new File(courseFile.getParent());
                if (!directory.exists())
                    directory.mkdirs();
                courseFile.createNewFile();
            } catch (IOException e) {
                log("Exception Occurred: " + e.toString());
            }
        }

        try {
            // Convenience class for writing character files
            FileWriter courseWriter;
            courseWriter = new FileWriter(courseFile.getAbsoluteFile(), false);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(courseWriter);
            //bufferWriter.
            bufferWriter.write(gson.toJson(users));
            bufferWriter.close();

            //og("Course data saved at file location: " + Constants.dataFileLocation + " Data: " + myData + "\n");
        } catch (IOException e) {
            log("Got an error while saving Course data to file " + e.toString());
        }
    }

    static void WriteUsersToDB(ArrayList<User> users) {
        try {
            Connection connection=getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from Users where 1");
            for (User user:users) {
//                statement.executeUpdate("insert into Users values('john',1,0,0,0,0,0,0,false ,0,0)");
                statement.executeUpdate("insert into Users values( '"+user.getUsername()+"',"+user.getScore()+","+user.getWave()+","+user.getRocket()+","+user.getLife()+","+user.getBulletType()+","+user.getPowerUp()+","+user.getCoin()+","+user.isResume()+","+user.getWaveToResum()+","+user.getClock()+")");
//                statement.executeUpdate("insert into Users (username, score, wave,rocket,life,bulletType,powerUp,coin,resume,waveToResume,clock) values ( " +user.getUsername()+","+user.getScore()+","+user.getWave()+","+user.getRocket()+","+user.getLife()+","+user.getBulletType()+","+user.getPowerUp()+","+user.getCoin()+","+user.isResume()+","+user.getWaveToResum()+","+user.getClock()+")");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Data base failure");
//            e.printStackTrace();
            WriteUsersToFile(users);
        }

//        statement.executeUpdate("update courses set course_name = CONCAT(course_name,'-') where course_name like '%s'");
    }

    // Read From File Utility
    static ArrayList<User> ReadUsersFromFile() {
        File dataFile = new File(Constants.dataFileLocation);
        if (!dataFile.exists())
            log("File doesn't exist");

        //InputStreamReader isReader;
        try {
            Scanner scanner = new Scanner(dataFile);
            String data=scanner.nextLine();
            Type typeOfListOfUsers = new TypeToken<List<User>>(){}.getType();
//            return ReadUsersFromDB();
            return new ArrayList<>(gson.fromJson(data, typeOfListOfUsers));

        } catch (Exception e) {
            log("error load cache from file " + e.toString());
        }

//        log("\nCourse Data loaded successfully from file " + Constants.dataFileLocation);

        return new ArrayList<>();
    }

    static ArrayList<User> ReadUsersFromDB() {
        ArrayList<User> users= new ArrayList<>();
        try {
            Connection connection=getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()){
                users.add(new User(resultSet.getString(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getInt(7),resultSet.getInt(8),resultSet.getBoolean(9),resultSet.getInt(10),resultSet.getInt(11)));
            }
            connection.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Data base failure");
            users.addAll(ReadUsersFromFile());
        }
        return users;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/chickenInvaders2DB","root","");
    }

    private static void log(String s){
        System.out.println(s);
    }
}
