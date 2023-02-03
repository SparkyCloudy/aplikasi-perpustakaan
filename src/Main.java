import Features.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    // Environment Variables
    static Dotenv env = Dotenv.load();
    final static String bookpath = env.get("BOOKJSON");
    final static String userpath = env.get("USERJSON");
    final static String loanpath = env.get("LOANJSON");

    public static void main(String[] args) {
        var program = new Main();
        var fileExist = new File("src/.env");

        if (!fileExist.exists()) {
            System.out.println("Environtment file not found, please read \".env.example\" file!");
            return;
        }

        try (var file = new FileReader("src/.env")) {
            if (bookpath == null || userpath == null || loanpath == null && file.ready()) {
                System.out.println("Environtment variables for Database not ready, please read \".env.example\" file!");
                return;
            }

            program.initModule();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void initModule() {
        UI.initUI();
    }
}