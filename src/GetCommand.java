import java.util.Map;

public class GetCommand extends Command {
    private Map<String, Variable> vars;

    public GetCommand(Program p) {
        super(p);
    }

    public void execute() {
        String attributeName = matcher.group(1);
        Variable obj = program.getStack().pop();

        Class objAttr = Util.searchVariable(attributeName, obj.getClasse());
        vars = objAttr.getVars();

        Variable v = vars.get(attributeName);
        program.addVariableInStack(v);
    }


}
