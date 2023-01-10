package Features.Management;

import com.google.gson.JsonObject;
import java.util.Scanner;

class Form {
    // Instantiation Objects
    static Scanner input = new Scanner(System.in);
    static Checker check = new Checker();

    /**
     * Initialize Register Form
     */
    public static void initRegister() {
        // instantiation JsonObject
        var obj = new JsonObject();

        // Keep repeat until the input correct
        while (true) {
            System.out.println("Masukan Nama Lengkap dari Mahasiswa/i atau \"Kembali\" ke menu Utama");
            System.out.print(">> ");

            // Initialize Variables
            String name = input.nextLine();

            // Check if the input is correct
            boolean isString = check.isString(name);
            if (name.equalsIgnoreCase("kembali")) {
                // Back to UI home menu
                return;
            } else if (isString) {
                // Add input to JsonObject
                obj.addProperty("name", name);

                // Input has been added, then we stop the loop
                break;
            } else {
                // Tell user if their input incorrect
                System.out.println("Input nama salah, Harus berupa huruf!.");
            }
        }

        // Keep repeat until the input correct
        while (true) {
            System.out.println("Masukan Nomer Telpon dari Mahasiswa/i atau \"Kembali\" ke menu Utama");
            System.out.print(">> ");

            // Initialize Variables
            String phone = input.nextLine();

            // Check if the input is correct
            boolean isNumber = check.isNumber(phone);
            if (phone.equalsIgnoreCase("kembali")) {
                // Back to UI home menu
                return;
            } else if (isNumber) {
                // Add input to JsonObject
                obj.addProperty("phone", Long.valueOf(phone));

                // Input has been added, then we stop the loop
                break;
            } else {
                // Tell user if their input incorrect
                System.out.println("Input Nomer Telpon salah, Harus berupa angka tanpa spasi!.");
            }
        }

        // Keep repeat until the input correct
        while (true) {
            System.out.println("Masukan NIM dari Mahasiswa/i atau \"Kembali\" ke menu Utama");
            System.out.print(">> ");

            // Initialize Variables
            String nim = input.nextLine();

            // Check if the input is correct
            boolean isNumber = check.isNumber(nim);
            if (nim.equalsIgnoreCase("kembali")) {
                // Back to UI home menu
                return;
            } else if (nim.length() == 10 && isNumber) {
                // Add input to JsonObject
                obj.addProperty("nim", Long.valueOf(nim));

                // Input has been added, then we stop the loop
                break;
            } else {
                // Tell user if their input incorrect
                System.out.println("Input NIM salah, Harus berupa 10 digit angka tanpa spasi!.");
            }
        }

        // Add an object into a list
        var list = check.addObjectToList(obj);

        // Write the list to the JSON file
        check.writeListToFile(list);
    }

    public static void initChecker(String jsonElement) {
        while (true) {
            System.out.println("Masukan NIM dari Mahasiswa/i yang ingin dicari atau \"Kembali\" ke menu Utama");
            System.out.print(">> ");

            String value = input.nextLine();

            // Check if the input is correct
            boolean isNumber = check.isNumber(value);
            if (value.equalsIgnoreCase("kembali")) {
                // Back to UI main menu
                return;
            } else if (isNumber) {
                // Check if user input exist on JSON file
                if (check.dataIsValid(value, check.getUserList(), jsonElement)) {
                    System.out.println("Found!");
                } else {
                    System.out.println("Not Found!");
                }
            } else {
                // Tell user if their input incorrect
                System.out.println("Input NIM salah, Harus berupa angka tanpa spasi!.");
            }
        }
    }
}