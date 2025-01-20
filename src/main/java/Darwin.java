import java.util.Scanner;
import java.util.ArrayList;

public class Darwin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();
        String line = "____________________________________________________________";
        System.out.println(line + "\nHello, I'm Darwin!\nWhat can I do for you?\n" + line);
        while (true) {
            String input = scanner.nextLine();
            System.out.println(line);
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                scanner.close();
                break;
            } else if (input.equals("list")) {
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i - 1));
                }
            } else {
                tasks.add(input);
                System.out.println("added: " + input);
            }
            System.out.println(line);
        }
    }
}
