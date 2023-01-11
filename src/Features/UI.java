package Features;

public class UI extends Form {
    public static void initUI() {
        while (true) {
            System.out.println("1. Register Mahasiswa");
            System.out.println("2. Check NIM Mahasiswa");
            System.out.println("3. Exit Program");
            System.out.print("Pilihan: ");

            String choice = input.nextLine();
            if (check.isNumber(choice)) {
                switch (Integer.parseInt(choice)) {
                    case 1 -> initRegister();
                    case 2 -> initChecker();
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Pilihan yang dimasukan salah!");
                }
            } else {
                System.out.println("Pilihan yang dimasukan salah!");
            }

        }
    }
}
