import java.io.IOException;
import java.util.*;

public class Interface {

    public static void main(String[] args) {
        System.out.println("----------------------------------------");
        System.out.println("Welcome to the NBA Distance application!");
        System.out.println("----------------------------------------");
        System.out.println("The application is setting up...\n");
        Parser p = new Parser();
        try {
            p.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int question;
        String input;
        
        Scanner sc = new Scanner(System.in);
        String s;
        
        while (true) {
            System.out.println("Input a question number between 1-4.");
            s = sc.nextLine();
            question = Integer.parseInt(s);
            if (question < 1 || question > 4) {
                System.out.println("Invalid question number.");
                continue;
            }
            
            System.out.println("Input a keyword text.");
            input = sc.nextLine();
            
            System.out.println(redirect(question, input));
            System.out.println();
        }
    };
    
    public static String redirect(int question, String input) {
        String p1;
        String p2;
        switch (question) {
            case (1):
                if (input.split(",").length < 2) {
                    return "Invalid keyword text.";
                }
                p1 = input.replaceAll("\\s", "").split(",")[0].toLowerCase().replaceAll("[^A-Za-z]", "");
                p2 = input.replaceAll("\\s", "").split(",")[1].toLowerCase().replaceAll("[^A-Za-z]", "");
                return Parser.question1(p1, p2);
            case (2):
                if (input.split(",").length < 2) {
                    return "Invalid keyword text.";
                }
                p1 = input.replaceAll("\\s", "").split(",")[0].toLowerCase().replaceAll("[^A-Za-z]", "");
                p2 = input.replaceAll("\\s", "").split(",")[1].toLowerCase().replaceAll("[^A-Za-z]", "");
                return Parser.question2(p1, p2);
            case (3):
                p1 = input.replaceAll("\\s", "").toLowerCase();
                return Parser.question3(p1);
            case (4):
                return Parser.question4();
        }
        return "Invalid question number.";
    }

}
