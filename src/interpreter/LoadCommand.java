package interpreter;


import java.util.*;

public class LoadCommand extends Command {

    private Map<String, Variable> vars = new HashMap<>();

    public LoadCommand(Program m) {
        super(m);
    }

    public void execute() {
        vars = program.getCurrentMethod().getVars();
        Variable v = vars.get(matcher.group(1));
        program.getStack().push(v);
    }
}

