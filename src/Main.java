import Features.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    // Environment Variables
    static Dotenv env = Dotenv.load();
    final static String bookpath = env.get("BOOKJSON");
    final static String userpath = env.get("USERJSON");
    final static String loanpath = env.get("LOANJSON");

    public static void main(String[] args) {
        var program = new Main();
        if (bookpath != null && userpath != null && loanpath != null) {
            program.initModule();
        } else {
            System.out.println("Environtment variables for Database not ready, please read \".env.example\" file!");
        }

    }

    void initModule() {
        UI.initUI();
    }
}