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
            return new ArrayList<>(gson.fromJson(data, typeOfListOfUsers));

        } catch (Exception e) {
            log("error load cache from file " + e.toString());
        }

        log("\nCourse Data loaded successfully from file " + Constants.dataFileLocation);

        return null;
    }

    static void test(Object model) {
        System.out.println(gson.toJson(model).getBytes().length);
    }

    private static void log(String s){
        System.out.println(s);
    }
}
