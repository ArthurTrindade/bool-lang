import java.util.regex.*;

public class Test {

    public static String test(String line) {

        switch (testAtr(line)) {
            case RET: return matchRet(line);
            case ATR: System.out.println("Linha: atribuição");
            default: System.out.println("linha");
        }

        return line;
    }

    public static String matchRet(String line) {
        String str = "";
        Pattern pattern = Pattern.compile(Regex.RET);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            str = "load " + matcher.group(1) + "\n" + "ret";
        }

        return str;
    }

    public static Instructions testAtr(String line) {
        if (regexReturn(line)) {
            return Instructions.RET;
        } else if (atriRegex(line)) {
            return Instructions.ATR;
        }
        return Instructions.STD;
    }

    public static boolean atriRegex(String text) {
        return testRegex(text, Regex.ATR);
    }

    public static boolean regexReturn(String text) {
        return testRegex(text, Regex.RET);
    }

    public static boolean testRegex(String text, String regex) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(text);

        return matcher.find();
    }
}


