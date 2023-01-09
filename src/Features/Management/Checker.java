package Features.Management;

import com.google.gson.*;
import java.io.*;
import io.github.cdimascio.dotenv.*;

class Checker {
    // Initialize GSon with pretty printing
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Create a JsonArray
    private JsonArray userList = new JsonArray();

    // JSON database location
    static Dotenv env = Dotenv.load();
    final static String filepath = env.get("FILEPATH");


    /**
     * Method simplifier to add new data/object to existing JSON database
     * @param object    JsonObject to be added to the list.
     * @return          JsonArray value.
     */
    protected JsonArray addObjectToList(JsonObject object) {
        try {
            var reader = new FileReader(filepath);

            // Check if the file have a existing data inside
            if (reader.ready()) {
                // If found, then read the file and put to the userList var
                userList = gson.fromJson(reader, JsonArray.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add back
        userList.add(object);

        return userList;
    }

    /**
     * Method simplifier to add JsonArray to the file
     * @param list  JsonArray to be added to the file.
     * @noreturn
     */
    protected void writeListToFile(JsonArray list) {
        try {
            // Initialize new FileWriter object
            var file = new FileWriter(filepath);

            // Add list to the file
            gson.toJson(list, file);

            // Close the writer
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to checking if value of JSON Element is exist inside JSON file
     * @param search    String want to search
     * @param list      JsonArray to be searched
     * @param data      Json Element we want to get
     * @return          true if data found
     */
    protected boolean dataIsValid(String search, JsonArray list, String data) {
        for (int i = 0; i < list.size(); i++) {
            var element = list.get(i);
            String nim = element.getAsJsonObject().get(data).getAsString();

            if (search.equalsIgnoreCase(nim)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method to checking if input/scanner has the correct data.
     * @param str   string that will be checked by the method
     * @return      true if data input is not digit, false otherwise
     */
    protected boolean isString(String str) {
        for (char c: str.toCharArray()) {
            // If found character is an integer,
            if (Character.isDigit(c)) {
                return false;
            }
        }

        // Otherwise
        return true;
    }

    /**
     * Method to checking if input/scanner has the number (integer/float) data.
     * @param str   string that will be checked by the method
     * @return      true if data input is a int/float without whitespace, false otherwise
     */
    protected boolean isNumber(String str) {
        for (char c : str.toCharArray()) {
            // Stop if not digit
            if (!Character.isDigit(c)) {
                return false;
            }

            // Stop if we found whitespace on the string
            if (Character.isWhitespace(c)) {
                return false;
            }
        }

        // Otherwise
        return true;
    }

    protected JsonArray getUserList() {
        try {
            var reader = new FileReader(filepath);

            // Check if the file have a existing data inside
            if (!reader.ready()) {
                // If not found, tell user File is Empty
                System.out.println("File is Empty");
                return null;
            }

            return userList = gson.fromJson(reader, JsonArray.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
