public class RetCommand extends Command {
    public RetCommand(Program p) {
        super(p);
    }

    public void execute() {
        program.getCurrentMethod().setPc(0);
        Method m = program.getMethods().pop();
        program.setCurrentMethod(m);
    }
}
