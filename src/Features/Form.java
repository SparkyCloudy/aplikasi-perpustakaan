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
    final static String userpath = env.get("USERJSON");

    /**
     * Initialize Register Form
     */
    public static void initRegister() {
        // instantiation JsonObject
        var obj = new JsonObject();

        // Keep repeat until the input correct
        while (true) {
            check.clearConsole();
            System.out.println("Masukan Nama Lengkap dari Mahasiswa/i.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            // Initialize Variables
            String value = input.nextLine();

            // Check if the input is correct
            if (value.startsWith("^") && value.length() == 2) {
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
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!check.isString(value)) {
                // Tell user if their input incorrect
                System.out.println("Input nama salah, Harus berupa huruf!.");
                check.clearConsole(3);
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("name", value);

            // Input has been added, then we stop the loop
            break;
        }

        // Keep repeat until the input correct
        while (true) {
            check.clearConsole();
            System.out.println("Masukan Nomer Telpon dari Mahasiswa/i.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            // Initialize Variables
            String value = input.nextLine();

            if (value.startsWith("^") && value.length() == 2) {
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
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!check.isNumber(value)) {
                // Tell user if their input incorrect
                System.out.println("Input Nomer Telpon salah, Harus berupa angka tanpa spasi!.");
                check.clearConsole(3);
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("phone", Long.valueOf(value));

            // Input has been added, then we stop the loop
            break;
        }

        // Keep repeat until the input correct
        while (true) {
            check.clearConsole();
            System.out.println("Masukan NIM dari Mahasiswa/i.");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            // Initialize Variables
            String value = input.nextLine();

            // Check if the input is correct
            if (value.startsWith("^") && value.length() == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    // Stop last menu to let new menu run
                    initRegister();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!check.isNumber(value) || value.length() != 10) {
                // Tell user if their input incorrect
                System.out.println("Input NIM salah, Harus berupa 10 digit angka tanpa spasi!.");
                check.clearConsole(3);
                continue;
            }

            if (check.elementIsValid(value, check.getDatabaseList(userpath), "nim")) {
                System.out.println("Mahasiswa sudah Terdaftar di Database!");
                check.clearConsole(2);
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("nim", Long.valueOf(value));

            // Input has been added, then we stop the loop
            break;
        }

        // Add an object into a list and Write the list to the JSON file
        check.writeListToFile(check.addObjectToList(obj, userpath), userpath);
    }

    public static void initChecker() {
        while (true) {
            check.clearConsole();
            System.out.println("Masukan data dari Mahasiswa/i yang ingin dicari.");
            System.out.println("Back: ^B");
            System.out.print(">> ");

            String value = input.nextLine();

            // Check if the input is correct
            if (value.startsWith("^") && value.length() == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    check.clearConsole(3);
                    continue;
                }
            }

            // Check what JSON Element what we want to check
            if (check.isNumber(value) && value.length() == 10) {
                // Check if user input exist on JSON file
                if (!check.elementIsValid(value, check.getDatabaseList(userpath), "nim")) {
                    System.out.println("Not Found!");
                    check.clearConsole(1);
                    continue;
                }
                System.out.println("NIM Found!");
                check.clearConsole(1);
            } else if (check.isNumber(value)) {
                if (!check.elementIsValid(value, check.getDatabaseList(userpath), "phone")) {
                    System.out.println("Not Found!");
                    check.clearConsole(1);
                    continue;
                }
                System.out.println("Phone Found!");
                check.clearConsole(1);
            } else if (check.isString(value)) {
                if (!check.elementIsValid(value, check.getDatabaseList(userpath), "name")) {
                    System.out.println("Not Found!");
                    check.clearConsole(1);
                    continue;
                }
                System.out.println("Name Found!");
                check.clearConsole(1);
            } else {
                // Tell user if their input incorrect
                System.out.println("Input NIM salah, Harus berupa angka tanpa spasi!.");
                check.clearConsole(3);
            }
        }
    }

    public static void initRemove() {
        while (true) {

            if (check.getDatabaseList(userpath) == null || check.getDatabaseList(userpath).isEmpty()) {
                System.out.println("Database user kosong, harap diisi terlebih dahulu menggunakan menu \"Register Mahasiswa\".");
                check.clearConsole(3);
                break;
            }

            System.out.println("List Mahasiswa/i yang terdaftar di Perpustakaan.");
            for (int i = 0; i < check.getDatabaseList(userpath).size(); i++) {
                var name = check
                        .getDatabaseList(userpath)
                        .get(i).getAsJsonObject()
                        .get("name")
                        .getAsString();

                System.out.printf("%s. %s %n", (i+1), name);
            }
            System.out.println("================");
            System.out.println("Pilih Nomer Mahasiswa yang ingin dihapus dari Database");
            System.out.println("Back: ^B");
            System.out.print(">> ");

            String value = input.nextLine();

            if (value.startsWith("^") && value.length() == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!check.isNumber(value)) {
                System.out.println("Input Pilihan salah, Harus berupa angka tanpa spasi!.");
                check.clearConsole(3);
                continue;
            }

            var list = check.getDatabaseList(userpath);
            list.remove(Integer.parseInt(value) - 1);

            check.writeListToFile(list, userpath);
            System.out.println("Mahasiswa/i berhasil di hapus dari Database!");
            check.clearConsole(3);
            break;
        }
    }
}