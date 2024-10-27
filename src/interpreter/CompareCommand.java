package interpreter;

public class CompareCommand extends Command {
    public CompareCommand(Program p) {
        super(p);
    }

    @Override
    public void execute() {
        String op = matcher.group(1);
        int v2 = program.getStack().pop().getValue();
        int v1 = program.getStack().pop().getValue();

        boolean result = switch (op) {
            case "gt" -> v1 > v2;
            case "ge" -> v1 >= v2;
            case "lt" -> v1 < v2;
            case "le" -> v1 <= v2;
            case "eq" -> v1 == v2;
            case "ne" -> v1 != v2;
            default -> false;
        };

        Variable newVariable = new Variable(result);
        program.getStack().push(newVariable);
    }


}
