package interpreter;

import java.util.*;

public class StoreCommand extends Command {

    private Map<String, Variable> vars = new HashMap<>();

    public StoreCommand(Program m) {
        super(m);
    }

    public void execute() {
        vars = program.getCurrentMethod().getVars();
        Variable v = program.getStack().pop();
        vars.put(matcher.group(1), v);
    }
}

