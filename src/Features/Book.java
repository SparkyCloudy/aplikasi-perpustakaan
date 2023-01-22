package Features;

import Features.Management.Checker;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

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
        check.clearConsole();
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
            check.clearConsole(3);
            initDonation();

            // Stop last menu to let new menu run
            return;
        }

        if (value.startsWith("^") && value.length() == 2) {
            if (value.contains("B")) {
                // Back to last menu
                return;
            }

            if (value.contains("C")) {
                initDonation();
            } else {
                System.out.println("Kombinasi tidak ditemukan!");
                check.clearConsole(2);
                initDonation();
            }

            // Stop last menu to let new menu run
            return;
        }

        // Add input to JsonObject
        obj.addProperty("bookName", value);
        temp.add(value);

        while (true) {
            check.clearConsole();
            System.out.printf("Masukan Nama donatur buku %s. %n", temp.get(0));
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            value = input.nextLine();

            // Cancel and Back Buttons
            if (value.startsWith("^") && value.length() == 2) {
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
                    check.clearConsole(3);
                    continue;
                }
            }

            boolean isString = check.isString(value);
            if (!isString) {
                System.out.println("Input Nama Donatur salah, Harus berupa huruf!.");
                check.clearConsole(3);
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("donorName", value);
            temp.add(value);

            // Input has been added, then we stop the loop
            break;
        }

        while (true) {
            check.clearConsole();
            System.out.printf("Masukan Jumlah buku \"%s\" yang didonasikan. %n", temp.get(0));
            System.out.println("Back: ^B");
            System.out.println("Cancel: ^C");
            System.out.print(">> ");

            value = input.nextLine();

            if (value.startsWith("^") && value.length() == 2) {
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
                    check.clearConsole(3);
                    continue;
                }
            }

            boolean isNumber = check.isNumber(value);
            if (!isNumber) {
                System.out.println("Input jumlah buku salah, Harus berupa nilai angka tanpa spasi!.");
                check.clearConsole(3);
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
            check.clearConsole();
            System.out.println("[KONFIRMASI] Apakah detail buku dibawah sudah tepat?");
            System.out.println("Nama Buku\t : " + temp.get(0));
            System.out.println("Nama Donatur\t : " + temp.get(1));
            System.out.println("Jumlah Buku\t : " + temp.get(2));
            System.out.print("Pilihan [Y/n]: ");

            value = input.nextLine();

            // Default answear, Y or blank
            if (value.isBlank() || value.equalsIgnoreCase("y")) {
                System.out.println("Buku berhasil ditambahkan ke Database!");
                check.clearConsole(3);
                break;
            }

            if (!value.equalsIgnoreCase("n")) {
                System.out.println("Input konfirmasi salah, Input diantara [Y/N]!.");
                check.clearConsole(3);
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
        check.clearConsole();
    }

    public static void initBorrow() {
        temp.clear();
        var obj = new JsonObject();
        var jArray = new JsonArray();
        var list = check.getDatabaseList(userpath);
        String value;
        while (true) {
            check.clearConsole();
            System.out.println("Masukan NIM mahasiswa yang ingin meminjam buku");
            System.out.println("Back: ^B");
            System.out.print(">> ");

            value = input.nextLine();

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

            if (!check.isNumber(value) || value.length() != 10) {
                System.out.println("Input NIM salah, Harus berupa 10 digit angka tanpa spasi!.");
                check.clearConsole(2);
                continue;
            }

            if (!check.elementIsValid(value, list, "nim")) {
                System.out.println("NIM Mahasiswa tidak ditemukan, harap mendaftarkan NIM tersebut");
                check.clearConsole(2);
                continue;
            }

            if (check.elementIsValid(value, check.getDatabaseList(loanpath), "nim")) {
                System.out.println("Mahasiswa sudah meminjam buku, harap dikembalikan terlebih dahulu!");
                check.clearConsole(2);
                continue;
            }

            obj.addProperty("nim", value);

            break;
        }

        list = check.getDatabaseList(bookpath);
        while (true) {
            check.clearConsole();
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

            value = input.nextLine();

            var isNumber = check.isNumber(value);
            if (value.isEmpty()) {
                System.out.println("Input tidak boleh kosong!");
                check.clearConsole(3);
                continue;
            }

            if (isNumber
                    && Integer.parseInt(value) <= list.size()
                    && Integer.parseInt(value) > 0) {
                temp.add(value);
            }

            if (value.startsWith("^") && value.length() == 2) {
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
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!isNumber || Integer.parseInt(value) > list.size() || Integer.parseInt(value) <= 0) {
                System.out.println("Buku/Pilihan tidak ditemukan.!");
                check.clearConsole(2);
                continue;
            }

            var isString = check.isString(value);
            if (isString && check.elementIsValid(value, list, "bookName")) {
                value = list.getAsJsonObject().get("bookName").getAsString();
                jArray.add(value);
                continue;
            }

            // Get JSON Element on x index
            var quantity = list
                    .get(Integer.parseInt(value) - 1)
                    .getAsJsonObject().get("quantity")
                    .getAsInt();

            if (quantity == 0) {
                System.out.println("Buku sudah habis, tidak dapat meminjam buku tersebut");
                check.clearConsole(2);
                continue;
            }

            // Get JSON Property value and parse it as String
            value = list
                    .get(Integer.parseInt(value) - 1)
                    .getAsJsonObject().get("bookName")
                    .getAsString();

            // Add the string into the JsonArray
            jArray.add(value);
        }

        // Iterate every choice and subtract the quantity value inside Database
        for (String s : temp) {
            var quantity = list
                    .get(Integer.parseInt(s) - 1)
                    .getAsJsonObject().get("quantity")
                    .getAsInt();

            list = check.getDatabaseList(bookpath);
            list.get(Integer.parseInt(s) - 1)
                    .getAsJsonObject()
                    .addProperty("quantity", (quantity - 1));

            check.writeListToFile(list, bookpath);
        }

        list = check.addObjectToList(obj, loanpath);
        check.writeListToFile(list, loanpath);
        check.clearConsole();
    }

    public static void initSearch() {
        check.clearConsole();
        while (true) {
            var list = check.getDatabaseList(loanpath);

            if (list == null || list.isEmpty()) {
                System.out.println("Database kosong, harap diisi terlebih dahulu menggunakan menu \"Pinjam Buku\".");
                check.clearConsole(3);
                break;
            }

            System.out.println("List Mahasiswa/i yang meminjam di Perpustakaan.");
            for (int i = 0; i < list.size(); i++) {
                var element = list.get(i);
                var name = element.getAsJsonObject().get("nim").getAsString();
                System.out.printf("%s. %s %n", (i+1), name);
            }
            System.out.println("================");
            System.out.println("Back: ^B");
            System.out.print(">> ");

            var value = input.nextLine();

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

            var isNumber = check.isNumber(value);
            if (!isNumber) {
                System.out.println("Input harus berupa angka dan tanpa spasi!.");
                check.clearConsole(3);
                continue;
            }

            check.clearConsole();
            var element = list.get(Integer.parseInt(value) - 1);
            var jArray = element.getAsJsonObject().get("book").getAsJsonArray();
            var name = element.getAsJsonObject().get("nim").getAsString();
            System.out.printf("List buku yang dipinjam oleh %s. %n", name);
            for (int i = 0; i < jArray.size(); i++) {
                value = jArray.get(i).getAsString();
                System.out.printf("%d. %s %n", (i+1), value);
            }
            System.out.println();
        }
    }

    public static void initRemoveBook() {
        while (true) {
            var list = check.getDatabaseList(bookpath);

            if (list == null) {
                System.out.println("Database kosong, harap diisi terlebih dahulu menggunakan menu \"Donasi Buku\".");
                check.clearConsole(3);
                break;
            }

            System.out.println("List Buku yang terdaftar di Perpustakaan.");
            for (int i = 0; i < list.size(); i++) {
                var element = list.get(i);
                var name = element.getAsJsonObject().get("bookName").getAsString();
                System.out.printf("%s. %s %n", (i+1), name);
            }
            System.out.println("================");
            System.out.println("Pilih nomer Buku yang ingin dihapus dari Database");
            System.out.println("Back: ^B");
            System.out.print(">> ");

            var value = input.nextLine();

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

            list.remove(Integer.parseInt(value) - 1);

            check.writeListToFile(list, bookpath);
            System.out.println("Buku berhasil di hapus dari Database!");
            check.clearConsole(3);
            break;
        }
    }
}
