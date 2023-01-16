//utility yang
package Features.Management;

import com.google.gson.*;
import java.io.*;

public class Checker {
    // Initialize GSon with pretty printing
    //bikin object dari gson untuk memanipulasi file json
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Create a JsonArray
    private JsonArray userList = new JsonArray();

    /**
     * Method simplifier to add new data/object to existing JSON file
     * @param object    JsonObject to be added to the list.
     * @param file      Json File to be used store data.
     * @return          JsonArray value.
     */
    public JsonArray addObjectToList(JsonObject object, String file) {
        //
        try (var reader = new FileReader(file)){
            // Check if the file have a existing data inside
            if (reader.ready()) {
                // If found, then read the file and put to the userList var
                //ngerubah file dari reader ke kelas yang dituju
                userList = gson.fromJson(reader, JsonArray.class);
            } else {
                // If not found, then re-instantiate the object
                //bikin arrays baru
                userList = new JsonArray();
            }
        } catch (IOException e) {
            //buat ngethrow error klok ada masalah di file reader (yang ada diatas)
            e.printStackTrace();
        }

        // Add back
        // sudah berupa interface (sudah berupa object) yang akan diisi method yang dituju
        userList.add(object);

        return userList;
    }

    /**
     * Method simplifier to add JsonArray to the file
     * @param list  JsonArray to be added to the file.
     * @param file      Json File to be used store data.
     * @noreturn
     */
    public void writeListToFile(JsonArray list, String file) {
        // Initialize new FileWriter object
        try (var write = new FileWriter(file)) {
            // Add list to the file
            gson.toJson(list, write);

            // Close the writer
            write.flush();

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
    //buat ngecek value dari sebuah objek sama dengan yang ada di database
    public boolean elementIsValid(String search, JsonArray list, String data) {
        for (int i = 0; i < list.size(); i++) {
            var element = list.get(i);
            String value = element.getAsJsonObject().get(data).getAsString();

            // Checking if the value exist
            if (search.equalsIgnoreCase(value)) {
                return true;
            }
        }

        // Otherwise
        return false;
    }

    /**
     * Method to checking if input/scanner has the correct data.
     * @param str   string that will be checked by the method
     * @return      true if data input has value and is not digit, false otherwise
     */
    public boolean isString(String str) {
        for (char c: str.toCharArray()) {
            // If found character is an integer,
            if (Character.isDigit(c)) {
                return false;
            }
        }

        // Otherwise
        return !str.isEmpty();
    }

    /**
     * Method to checking if input/scanner has the number (integer/float) data.
     * @param str   string that will be checked by the method
     * @return      true if data input has a int/float value without whitespace, false otherwise
     */
    public boolean isNumber(String str) {
        for (char c : str.toCharArray()) {
            // Stop if not digit
            if (!Character.isDigit(c)) {
                return false;
            }

        }

        // Otherwise
        return !str.isEmpty();
    }

    /**
     * Method to get User List from JSON File.
     * @param file      Json File to be used store data.
     * @return          JsonArray value.
     */
    public JsonArray getDatabaseList(String file) {
        try (var reader = new FileReader(file)) {
            // Check if the file have a existing data inside
            if (reader.ready()) {
               // Get the list
                return userList = gson.fromJson(reader, JsonArray.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("File %s is Empty %n", file);
        }

        return null;
    }

    // Not yet finished
    public String[] getJsonArrayString(String file) {
        gson = new Gson();
        try (var reader = new FileReader(file)) {
            // Add list to the file
            return gson.fromJson(reader, String[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

