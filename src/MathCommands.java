public class MathCommands extends Command {
    public MathCommands(Program p) {
        super(p);
    }

    public void execute() {
        String op = matcher.group(1);
        int v2 = program.getStack().pop().getValue();
        int v1 = program.getStack().pop().getValue();

        int result = switch (op) {
            case "add" -> v1 + v2;
            case "sub" -> v1 - v2;
            case "mul" -> v1 * v2;
            case "div" -> v1 / v2;
            default -> 0;
        };

        Variable v = new Variable(result);
        program.getStack().push(v);
    }
}
