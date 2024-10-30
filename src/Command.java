import java.util.regex.Matcher;

public abstract class Command {
    Program program;
    Matcher matcher;

    public Command(Program program) {
        this.program = program;
    }

    public void setMatcher(Matcher m) {
        this.matcher = m;
    }

    public abstract void execute();
}
