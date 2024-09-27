import java.util.regex.*;

public class Test {

    public static String test(String line) {

        switch (Regex.compare(line)) {
            case RET: return matchRet(line);
            case ATR: return matchAtr(line);
            default:;
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

        if (Regex.testName(rhs)) {
            str = "load " + rhs;
        } else if (Regex.testNumber(rhs)) {
            str = "const " + rhs ;
        }

        if (Regex.testName(lhs)) {
            str2 = "store " + lhs;

        } else if (Regex.testObj(lhs)) {
            Pattern p2 = Pattern.compile(Regex.OBJ);
            Matcher m2 = p2.matcher(lhs);

            String o = "", me = "";

            if (m2.find()) {
                o = m2.group(1);
                me = m2.group(2);
            }

            str2 = "load " + o + "\n" + "set " + me;
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

}


