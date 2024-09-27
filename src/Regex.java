import java.util.regex.Pattern;

public class Regex {
    final static String RET = "return\\s+([a-zA-Z]+)";
    final static String ATR = "(.+)\\s+=\\s+(.+)";
    final static String NAME = "([a-zA-Z]+)";
    final static String NUMBER = "(-?\\d+)";

    public static boolean testName(String text) {
        return testRegex(text, Regex.NAME);
    }

    public static boolean testNumber(String text) {
        return testRegex(text, Regex.NUMBER);
    }

    public static boolean testAtr(String text) {
        return testRegex(text, Regex.ATR);
    }

    public static boolean testReturn(String text) {
        return testRegex(text, Regex.RET);
    }

    public static boolean testRegex(String text, String regex) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(text);

        return matcher.find();
    }

}
