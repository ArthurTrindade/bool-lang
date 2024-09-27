import java.util.regex.Pattern;

public class Regex {
    final static String RET = "return\\s+([a-zA-Z]+)";
    final static String ATR = "(.+)\\s+=\\s+(.+)";
    final static String NAME = "(^[a-zA-Z]+^)";
    final static String NUMBER = "(-?\\d+)";
    final static String OBJ = "(\\w+).(\\w+)";

    public static boolean testObj(String text) {
        return testRegex(text, Regex.OBJ);
    }

    public static boolean testName(String text) {
        return testRegex(text, Regex.NAME);
    }

    public static boolean testNumber(String text) {
        return testRegex(text, Regex.NUMBER);
    }

    public static boolean testRegex(String text, String regex) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(text);

        return matcher.find();
    }

    public static Instructions compare(String line) {
        if (testRegex(line, Regex.RET)) {
            return Instructions.RET;
        } else if (testRegex(line, Regex.ATR)) {
            return Instructions.ATR;
        }
        return Instructions.STD;
    }

}
