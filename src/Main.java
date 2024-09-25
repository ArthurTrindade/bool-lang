import java.util.regex.*;

public class Main {
    enum Instructions {
        STD, RET, ATR
    }

    public static void main(String[] args) {

        String line = "class Pessoa";

        switch (test(line)) {
            case RET -> System.out.println("Linha: Return");
            case ATR -> System.out.println("Linha: atribuição");
            default -> System.out.println("linha");
        }

    }

    public static Instructions test(String line) {
        if (regexReturn(line)) {
            return Instructions.RET;
        } else if (atriRegex(line)) {
            return Instructions.ATR;
        }
        return Instructions.STD;
    }

    public static boolean atriRegex(String text) {

        Pattern pattern = Pattern.compile(Regex.ATR);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public static boolean regexReturn(String text) {

        Pattern pattern = Pattern.compile(Regex.RET);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }
}


