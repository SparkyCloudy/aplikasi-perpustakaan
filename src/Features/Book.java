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

        System.out.println("+__________________________________________________________+");
        System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
        System.out.println("|               |_________________________|                |");
        System.out.println("|                      Donasi Buku                         |");
        System.out.println("|                                                          |");
        System.out.println("|  Masukan Nama Buku yang di donasikan.                    |");
        System.out.println("|  ___                                                     |");
        System.out.println("| |^B | Back                                               |");
        System.out.println("| |^C | Cancel                                             |");
        System.out.println("| |___|                                                    |");
        System.out.println("+__________________________________________________________+");
        System.out.print(">> ");

        String value = input.nextLine();

        // If User input whitespace (ENTER only) then repeat it
        if (value.isEmpty()) {
            System.out.println("Input tidak boleh kosong!");
            check.clearConsole(3);
            initDonation();

            // Stop current menu and let new current menu run
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

            // Stop current menu and let new current menu run
            return;
        }

        // Add input to JsonObject
        obj.addProperty("bookName", value);

        // Save current value to temp
        temp.add(value);

        while (true) {
            check.clearConsole();
            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                      Donasi Buku                         |");
            System.out.println("|                                                          |");
            System.out.printf("| Masukan Nama donatur buku %s. %n", temp.get(0));
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |^C | Cancel                                             |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
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
                    // Stop current menu and let new current menu run
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

            // Save current value to temp
            temp.add(value);

            // Input has been added, then we stop the loop
            break;
        }

        while (true) {
            check.clearConsole();
            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                      Donasi Buku                         |");
            System.out.println("|                                                          |");
            System.out.printf("| Masukan Jumlah buku \"%s\" yang didonasikan. %n", temp.get(0));
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |^C | Cancel                                             |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
            System.out.print(">> ");

            value = input.nextLine();

            if (value.startsWith("^") && value.length() == 2) {
                // Back button
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    // Stop current menu and let new current menu run
                    initDonation();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!check.isNumber(value)) {
                System.out.println("Input jumlah buku salah, Harus berupa nilai angka tanpa spasi!.");
                check.clearConsole(3);
                continue;
            }

            // Add input to JsonObject
            obj.addProperty("quantity", Long.valueOf(value));

            // Save current value to temp
            temp.add(value);

            // Input has been added, then we stop the loop
            break;
        }

        // Confirmation prompt
        while (true) {
            check.clearConsole();
            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                      Donasi Buku                         |");
            System.out.println("|                                                          |");
            System.out.println("| [KONFIRMASI] Apakah detail buku dibawah sudah tepat?");
            System.out.println("| Nama Buku\t : " + temp.get(0));
            System.out.println("| Nama Donatur\t : " + temp.get(1));
            System.out.println("| Jumlah Buku\t : " + temp.get(2));
            System.out.println("|  ___                                                     |");
            System.out.println("| | Y | Yes                                                |");
            System.out.println("| | N | No                                                 |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
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

        // Add an object into a list and write the list to the JSON file
        check.writeListToFile(check.addObjectToList(obj, bookpath), bookpath);
    }

    public static void initBorrow() {
        check.clearConsole();
        temp.clear();
        var obj = new JsonObject();
        var jArray = new JsonArray();
        String value;
        while (true) {
            check.clearConsole();
            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                      Pinjam Buku                         |");
            System.out.println("|                                                          |");
            System.out.println("|  Masukan NIM mahasiswa yang ingin meminjam buku          |");
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
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

            if (!check.elementIsValid(value, check.getDatabaseList(userpath), "nim")) {
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

        while (true) {
            check.clearConsole();
            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                      Pinjam Buku                         |");
            System.out.println("|                                                          |");
            System.out.println("|  List buku yang tersedia di Perpustakaan.                |");
            System.out.println("|                                                          |");
            for (int i = 0; i < check.getDatabaseList(bookpath).size(); i++) {
                var name = check
                        .getDatabaseList(bookpath)
                        .get(i)
                        .getAsJsonObject()
                        .get("bookName")
                        .getAsString();

                System.out.printf("|%s. %s %n", (i+1), name);
            }
            System.out.println("|  ____________________________________________________    |");
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |^C | Cancel                                             |");
            System.out.println("| |^R | Next                                               |");
            System.out.println("| |___|                                                    |");
            System.out.println("|                                                          |");
            System.out.println("|   Note : Setelah Memilih Buku Harap untuk Next           |");
            System.out.println("+__________________________________________________________+");
            System.out.print(">> ");

            value = input.nextLine();

            if (value.isEmpty()) {
                System.out.println("Input tidak boleh kosong!");
                check.clearConsole(3);
                continue;
            }

            if (check.isNumber(value)
                    && Integer.parseInt(value) <= check.getDatabaseList(bookpath).size()
                    && Integer.parseInt(value) > 0) {
                // Save current value to temp
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
                    // Stop current menu and let new current menu run
                    initBorrow();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    check.clearConsole(3);
                    continue;
                }
            }

            if (!check.isNumber(value)
                    || Integer.parseInt(value) > check.getDatabaseList(bookpath).size()
                    || Integer.parseInt(value) <= 0) {
                System.out.println("Buku/Pilihan tidak ditemukan.!");
                check.clearConsole(2);
                continue;
            }

            if (check.isString(value) && check.elementIsValid(value, check.getDatabaseList(bookpath), "bookName")) {
                value = check.getDatabaseList(bookpath).getAsJsonObject().get("bookName").getAsString();
                jArray.add(value);
                continue;
            }

            // Get JSON Element on x index
            var quantity = check
                    .getDatabaseList(bookpath)
                    .get(Integer.parseInt(value) - 1)
                    .getAsJsonObject().get("quantity")
                    .getAsInt();

            if (quantity == 0) {
                System.out.println("Buku sudah habis, tidak dapat meminjam buku tersebut");
                check.clearConsole(2);
                continue;
            }

            // Get JSON Property value and parse it as String
            value = check
                    .getDatabaseList(bookpath)
                    .get(Integer.parseInt(value) - 1)
                    .getAsJsonObject().get("bookName")
                    .getAsString();

            // Add the string into the JsonArray
            jArray.add(value);
        }

        // Iterate every choice and subtract the quantity value inside Database
        for (String s : temp) {
            var quantity = check.getDatabaseList(bookpath)
                    .get(Integer.parseInt(s) - 1)
                    .getAsJsonObject().get("quantity")
                    .getAsInt();

            var list = check.getDatabaseList(bookpath);
            list.get(Integer.parseInt(s) - 1)
                    .getAsJsonObject()
                    .addProperty("quantity", (quantity - 1));

            check.writeListToFile(list, bookpath);
        }

        var list = check.addObjectToList(obj, loanpath);
        check.writeListToFile(list, loanpath);
        check.clearConsole();
    }

    public static void initSearch() {
        check.clearConsole();
        while (true) {

            if (check.getDatabaseList(loanpath) == null || check.getDatabaseList(loanpath).isEmpty()) {
                System.out.println("Database kosong, harap diisi terlebih dahulu menggunakan menu \"Pinjam Buku\".");
                check.clearConsole(3);
                break;
            }

            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                   Cari Pemimjam Buku                     |");
            System.out.println("|                                                          |");
            System.out.println("|  List Mahasiswa/i yang meminjam di Perpustakaan.         |");
            System.out.println("|                                                          |");
            for (int i = 0; i < check.getDatabaseList(loanpath).size(); i++) {
                var name = check
                        .getDatabaseList(loanpath)
                        .get(i)
                        .getAsJsonObject()
                        .get("nim")
                        .getAsString();

                System.out.printf("|%s. %s %n", (i+1), name);
            }
            System.out.println("|  ____________________________________________________    |");
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
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

            if (!check.isNumber(value)
                    || Integer.parseInt(value) > check.getDatabaseList(bookpath).size()
                    || Integer.parseInt(value) <= 0) {
                System.out.println("Buku/Pilihan tidak ditemukan.!");
                check.clearConsole(2);
                continue;
            }

            check.clearConsole();
            var jArray = check
                    .getDatabaseList(loanpath)
                    .get(Integer.parseInt(value) - 1)
                    .getAsJsonObject()
                    .get("book")
                    .getAsJsonArray();

            var name = check
                    .getDatabaseList(loanpath)
                    .get(Integer.parseInt(value) - 1)
                    .getAsJsonObject()
                    .get("nim")
                    .getAsString();

            check.clearConsole();
            System.out.println("+__________________________________________________________+");
            System.out.printf("| List buku yang dipinjam oleh %s. %n", name);
            System.out.println("|                                                          |");
            for (int i = 0; i < jArray.size(); i++) {
                value = jArray.get(i).getAsString();
                System.out.printf("|%d. %s %n", (i+1), value);
            }
            System.out.println("+__________________________________________________________+");

        }
    }

    public static void initRemoveBook() {
        while (true) {
            var list = check.getDatabaseList(bookpath);

            if (check.getDatabaseList(bookpath) == null) {
                System.out.println("Database kosong, harap diisi terlebih dahulu menggunakan menu \"Donasi Buku\".");
                check.clearConsole(3);
                break;
            }

            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                       Hapus Buku                         |");
            System.out.println("|                                                          |");
            System.out.println("|  List Buku yang terdaftar di Perpustakaan.               |");
            System.out.println("|                                                          |");
            for (int i = 0; i < check.getDatabaseList(bookpath).size(); i++) {
                var name = check
                        .getDatabaseList(bookpath)
                        .get(i)
                        .getAsJsonObject()
                        .get("bookName")
                        .getAsString();

                System.out.printf("|%s. %s %n", (i+1), name);
            }
            System.out.println("|  ____________________________________________________    |");
            System.out.println("|  Pilih nomer Buku yang ingin dihapus dari Database       |");
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
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

            if (!check.isNumber(value)
                    || Integer.parseInt(value) > check.getDatabaseList(bookpath).size()
                    || Integer.parseInt(value) <= 0) {
                System.out.println("Pilihan tidak ditemukan.!");
                check.clearConsole(2);
                continue;
            }

            list.remove(Integer.parseInt(value) - 1);

            check.writeListToFile(list, bookpath);
            System.out.println("Buku berhasil di hapus dari Database!");
            check.clearConsole(3);
            break;
        }
    }

    public static void initBookReturn() {
        temp.clear();
        check.clearConsole();
        String value;

        if (check.getDatabaseList(loanpath).isEmpty() || check.getDatabaseList(loanpath) == null) {
            System.out.println("Database kosong, harap diisi terlebih dahulu menggunakan menu \"Pinjam Buku\".");
            check.clearConsole(3);
            return;
        }

        while (true) {
            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                   Pengembalian Buku                      |");
            System.out.println("|                                                          |");
            System.out.println("| Masukan NIM mahasiswa yang ingin mengembalikan buku      |");
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
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
                check.clearConsole(3);
                continue;
            }

            if (!check.elementIsValid(value, check.getDatabaseList(loanpath), "nim")) {
                System.out.println("NIM Mahasiswa tidak ditemukan!");
                check.clearConsole(2);
                continue;
            }
            temp.add(value);
            break;
        }

        while (true) {
            check.clearConsole();

            int index;
            JsonArray jArray = new JsonArray();
            String nim = null;
            for (index = 0; index < check.getDatabaseList(loanpath).size(); index++) {
                String value2 = check.getDatabaseList(loanpath).get(index).getAsJsonObject().get("nim").getAsString();
                if (temp.get(0).equalsIgnoreCase(value2)) {
                    jArray = check
                            .getDatabaseList(loanpath)
                            .get(index)
                            .getAsJsonObject()
                            .get("book")
                            .getAsJsonArray();

                    nim = check
                            .getDatabaseList(loanpath)
                            .get(index)
                            .getAsJsonObject()
                            .get("nim")
                            .getAsString();
                    break;
                }
            }

            System.out.println("+__________________________________________________________+");
            System.out.println("|               |  APLIKASI PERPUSTAKAAN  |                |");
            System.out.println("|               |_________________________|                |");
            System.out.println("|                   Pengembalian Buku                      |");
            System.out.println("|                                                          |");
            System.out.printf("|  List buku yang dipinjam oleh %s. %n", nim);
            System.out.println("|                                                          |");
            for (int x = 0; x < jArray.size(); x++) {
                value = jArray.get(x).getAsString();
                System.out.printf("|%d. %s %n", (x+1), value);
            }
            System.out.println("|  ____________________________________________________    |");
            System.out.println("| Pilih nomer Buku yang ingin dikembalikan dari Pinjaman   |");
            System.out.println("|  ___                                                     |");
            System.out.println("| |^B | Back                                               |");
            System.out.println("| |^C | Cancel                                             |");
            System.out.println("| |___|                                                    |");
            System.out.println("+__________________________________________________________+");
            System.out.print(">> ");

            value = input.nextLine();

            if (value.startsWith("^") && value.length() == 2) {
                if (value.contains("B")) {
                    // Back to last menu
                    return;
                }

                if (value.contains("C")) {
                    // Stop current menu and let new current menu run
                    initBookReturn();
                    return;
                } else {
                    System.out.println("Kombinasi tidak ditemukan!");
                    check.clearConsole(3);
                    break;
                }
            }

            if (!check.isNumber(value)
                    || Integer.parseInt(value) > jArray.size()
                    || Integer.parseInt(value) <= 0) {
                System.out.println("Pilihan tidak ditemukan.!");
                check.clearConsole(2);
                continue;
            }

            String bookName = jArray
                    .get(Integer.parseInt(value)-1)
                    .getAsString();

            for (int i = 0; i < check.getDatabaseList(bookpath).size(); i++) {
                var bookDatabaseName = check.getDatabaseList(bookpath)
                        .get(i)
                        .getAsJsonObject()
                        .get("bookName")
                        .getAsString();

                if (bookName.equalsIgnoreCase(bookDatabaseName)) {
                    var list = check.getDatabaseList(bookpath);
                    var quantity = list
                            .get(i)
                            .getAsJsonObject()
                            .get("quantity")
                            .getAsInt();

                    list.get(i).getAsJsonObject().addProperty("quantity", quantity+1);
                    check.writeListToFile(list, bookpath);
                    break;
                }
            }

            jArray.remove(Integer.parseInt(value) - 1);

            var list = check.getDatabaseList(loanpath);

            list.get(index).getAsJsonObject().add("book", jArray);
            check.writeListToFile(list, loanpath);

            if (check.getDatabaseList(loanpath)
                    .get(index)
                    .getAsJsonObject()
                    .get("book")
                    .getAsJsonArray()
                    .size() == 0) {
                list = check.getDatabaseList(loanpath);
                list.remove(index);
                check.writeListToFile(list, loanpath);
                break;
            }
        }
    }
}
