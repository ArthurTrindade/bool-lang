package interpreter;


class ConstCommand extends Command {

    public ConstCommand(Program m) {
        super(m);
    }

    public void execute() {
        Variable v = new Variable(Integer.parseInt(matcher.group(1).trim()));
        program.getStack().push(v);
    }
}