import java.util.regex.*;

public class Test {

    public static String test(String line) {

        switch (testAtr(line)) {
            case RET: return matchRet(line);
            case ATR: return matchAtr(line);
            default: System.out.println("linha");
        }

        return line;
    }

    public static String matchAtr(String line) {
        String str = "", str2 = "",  lhs = "", rhs = "";

        Pattern pattern = Pattern.compile(Regex.ATR);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            lhs = matcher.group(1);
            rhs = matcher.group(2);
        }

        if (testName(rhs)) {
            str = "load " + rhs;
        } else if (testNumber(rhs)) {
            str = "const " + rhs ;
        }

        if (testName(lhs)) {
            str2 = "store " + lhs;
        }

        return str + "\n" + str2;
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

    public static boolean testName(String text) {
        return testRegex(text, Regex.NAME);
    }

    public static boolean testNumber(String text) {
        return testRegex(text, Regex.NUMBER);
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


