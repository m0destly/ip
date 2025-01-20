import java.util.Scanner;

public class Darwin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "____________________________________________________________";
        System.out.println(line + "\n" + "Hello, I'm Darwin!\nWhat can I do for you?" + "\n" + line);
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                System.out.println(line + "\n" + "Bye. Hope to see you again soon!" + "\n" + line);
                scanner.close();
                break;
            } else {
                System.out.println(line + "\n" + command + "\n" + line);
            }
        }
    }
}
