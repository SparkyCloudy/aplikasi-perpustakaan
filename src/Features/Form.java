package Features;

import Features.Management.Checker;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Scanner;

class Form {
    // Instantiation Objects
    static Scanner input = new Scanner(System.in);
    static Checker check = new Checker();

    // JSON database location
    static Dotenv env = Dotenv.load();
    final static String filepath = env.get("USERJSON");

    /**
     * Initialize Register Form
     */
    public static void initRegister() {
        // instantiation JsonObject
        var obj = new JsonObject();

        // Keep repeat until the input correct
        while (true) {
            System.out.println("Masukan Nama Lengkap dari Mahasiswa/i.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            // Initialize Variables
            String value = input.nextLine();

            // Check if the input is correct
            var length = value.length();
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    initRegister();

                    // Stop last menu to let new menu run
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            var isString = check.isString(value);
            if (!isString) {
                // Tell user if their input incorrect
                System.out.println("Input nama salah, Harus berupa huruf!.");
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("name", value);

            // Input has been added, then we stop the loop
            break;
        }

        // Keep repeat until the input correct
        while (true) {
            System.out.println("Masukan Nomer Telpon dari Mahasiswa/i.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            // Initialize Variables
            String value = input.nextLine();

            var length = value.length();
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    initRegister();

                    // Stop last menu to let new menu run
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            var isNumber = check.isNumber(value);
            if (!isNumber) {
                // Tell user if their input incorrect
                System.out.println("Input Nomer Telpon salah, Harus berupa angka tanpa spasi!.");
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("phone", Long.valueOf(value));

            // Input has been added, then we stop the loop
            break;
        }

        // Keep repeat until the input correct
        while (true) {
            System.out.println("Masukan NIM dari Mahasiswa/i.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            // Initialize Variables
            String value = input.nextLine();

            // Check if the input is correct
            var length = value.length();
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    initRegister();

                    // Stop last menu to let new menu run
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            var isNumber = check.isNumber(value);
            if (!isNumber || length != 10) {
                // Tell user if their input incorrect
                System.out.println("Input NIM salah, Harus berupa 10 digit angka tanpa spasi!.");
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("nim", Long.valueOf(value));

            // Input has been added, then we stop the loop
            break;
        }

        // Add an object into a list
        var list = check.addObjectToFile(obj, filepath);

        // Write the list to the JSON file
        check.writeListToFile(list, filepath);
    }

    public static void initChecker() {
        while (true) {
            System.out.println("Masukan data dari Mahasiswa/i yang ingin dicari.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            var value = input.nextLine();

            // Check if the input is correct
            var list = check.getUserList(filepath);
            var length = value.length();
            var isNumber = check.isNumber(value);
            var isString = check.isString(value);
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    initRegister();

                    // Stop last menu to let new menu run
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            // Check what JSON Element what we want to check
            if (isNumber && length == 10) {
                // Check if user input exist on JSON file
                if (!check.elementIsValid(value, list, "nim")) {
                    System.out.println("Not Found!");
                    continue;
                }
                System.out.println("NIM Found!");
            } else if (isNumber) {
                if (!check.elementIsValid(value, list, "phone")) {
                    System.out.println("Not Found!");
                    continue;
                }
                System.out.println("Phone Found!");
            } else if (isString) {
                if (!check.elementIsValid(value, list, "name")) {
                    System.out.println("Not Found!");
                    continue;
                }
                System.out.println("Name Found!");
            } else {
                // Tell user if their input incorrect
                System.out.println("Input NIM salah, Harus berupa angka tanpa spasi!.");
            }
        }
    }
}