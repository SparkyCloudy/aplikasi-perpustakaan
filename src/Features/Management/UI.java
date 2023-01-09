package Features.Management;

public class UI extends Form {
    public static void initUI() {
        while (true) {
            System.out.println("1. Register Mahasiswa");
            System.out.println("2. Check Mahasiswa");
            System.out.println("3. Exit Program");
            System.out.print("Pilihan: ");

            String choice = input.nextLine();
            switch (Integer.parseInt(choice)) {
                case 1 -> initRegister();
                case 2 -> initChecker("nim");
                case 3 -> {
                    return;
                }
                default -> System.out.println("Pilihan yang dimasukan salah!");
            }
        }
    }
}
