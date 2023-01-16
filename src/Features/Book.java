package Features;

import Features.Management.Checker;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

class BookDatabase {
    String bookName;
    String donorName;
    int quantity;
    String timeAdded;
}

class Book {
    static Scanner input = new Scanner(System.in);
    static Checker check = new Checker();
    static LinkedList<String> temp = new LinkedList<>();

    // Environment Variables
    static Dotenv env = Dotenv.load();
    final static String bookpath = env.get("BOOKJSON");
    final static String userpath = env.get("USERJSON");
    final static String loanpath = env.get("LOANJSON");

    public static void initDonation() {
        var obj = new JsonObject();
        temp.clear();

        System.out.println("Masukan Nama Buku yang di donasikan.");
        System.out.println("Back: ^B");
        System.out.println("Cancel: ^C");
        System.out.print(">> ");

        String value = input.nextLine();

        // If User input whitespace (ENTER only) then repeat it
        if (value.isEmpty()) {
            System.out.println("Input tidak boleh kosong!");
            initDonation();

            // Stop last menu to let new menu run
            return;
        }

        int length = value.length();
        if (value.startsWith("^") && length == 2) {
            if (value.contains("B")) {
                // Back to last menu
                return;
            }

            if (value.contains("C")) {
                initDonation();
            } else {
                System.out.println("Kombinasi tidak ditemukan!");
                initDonation();
            }

            // Stop last menu to let new menu run
            return;
        }

        // Add input to JsonObject
        obj.addProperty("bookName", value);
        temp.add(value);

        while (true) {
            System.out.printf("Masukan Nama donatur buku %s. %n", temp.get(0));
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            value = input.nextLine();

            // Cancel and Back Buttons
            length = value.length();
            if (value.startsWith("^") && length == 2) {
                // Back Button
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                // Cancel Button
                if (value.contains("C")) {
                    // Stop last menu to let new menu run
                    initDonation();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            boolean isString = check.isString(value);
            if (!isString) {
                System.out.println("Input Nama Donatur salah, Harus berupa huruf!.");
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("donorName", value);
            temp.add(value);

            // Input has been added, then we stop the loop
            break;
        }

        while (true) {
            System.out.printf("Masukan Jumlah buku \"%s\" yang didonasikan. %n", temp.get(0));
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            value = input.nextLine();

            length = value.length();
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    // Stop last menu to let new menu run
                    initDonation();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            boolean isNumber = check.isNumber(value);
            if (!isNumber) {
                System.out.println("Input jumlah buku salah, Harus berupa nilai angka tanpa spasi!.");
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("quantity", Long.valueOf(value));
            temp.add(value);

            // Input has been added, then we stop the loop
            break;
        }

        // Confirmation prompt
        while (true) {
            System.out.println("[KONFIRMASI] Apakah detail buku dibawah sudah tepat?");
            System.out.println("Nama Buku\t : " + temp.get(0));
            System.out.println("Nama Donatur : " + temp.get(1));
            System.out.println("Jumlah Buku\t : " + temp.get(2));
            System.out.print("Pilihan [Y/n]: ");

            value = input.nextLine();

            // Default answear, Y or blank
            if (value.isBlank() || value.equalsIgnoreCase("y")) {
                System.out.println("Buku berhasil ditambahkan ke Database!");
                break;
            }

            if (!value.equalsIgnoreCase("n")) {
                System.out.println("Input konfirmasi salah, Input diantara [Y/N]!.");
                continue;
            }

            initDonation();
            return;
        }

        // Get current time for TimeStamp
        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var formattedTimestamp = instant.atZone(ZoneId.of("UTC+8")).format(formatter);

        // Add TimeAdded to JsonObject
        obj.addProperty("timeAdded", formattedTimestamp);

        // Add an object into a list
        var list = check.addObjectToList(obj, bookpath);

        // Write the list to the JSON file
        check.writeListToFile(list, bookpath);
    }

    public static void initBorrow() {
        temp.clear();
        var obj = new JsonObject();
        var jArray = new JsonArray();
        var list = check.getDatabaseList(userpath);
        while (true) {
            System.out.println("Masukan NIM mahasiswa yang ingin meminjam buku");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            String value = input.nextLine();
            temp.add(value);

            var length = value.length();
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    // Stop last menu to let new menu run
                    initBorrow();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            if (!check.elementIsValid(value, list, "nim")) {
                System.out.println("NIM Mahasiswa tidak ditemukan, harap mendaftarkan NIM tersebut");
                continue;
            }

            obj.addProperty("nim", value);

            break;
        }

        list = check.getDatabaseList(bookpath);
        while (true) {
            System.out.println("List buku yang tersedia di Perpustakaan.");
            for (int i = 0; i < list.size(); i++) {
                var element = list.get(i);
                var name = element.getAsJsonObject().get("bookName").getAsString();
                System.out.printf("%s. %s %n", (i+1), name);
            }
            System.out.println("================");
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.println("Next: ^R");
            System.out.print(">> ");

            var value = input.nextLine();

            if (value.isEmpty()) {
                System.out.println("Input tidak boleh kosong!");
                continue;
            }

            var length = value.length();
            if (value.startsWith("^") && length == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("R")) {
                    // Go to next section
                    obj.add("book", jArray);
                    break;
                }

                if (value.contains("C")) {
                    // Stop last menu to let new menu run
                    initBorrow();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    continue;
                }
            }

            var isNumber = check.isNumber(value);
            if (isNumber) {
                var element = list.get(Integer.parseInt(value) - 1);
                value = element.getAsJsonObject().get("bookName").getAsString();
                jArray.add(value);
                continue;
            }

            // var isString = check.isString(value);
            /*if (isString) {

            }*/

            System.out.println("Buku/Pilihan tidak ditemukan.!");
        }

        list = check.addObjectToList(obj, loanpath);
        check.writeListToFile(list, loanpath);
    }

    public static void initSearch() {

    }
}
