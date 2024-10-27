package interpreter;

public class RetCommand extends Command {
    public RetCommand(Program p) {
        super(p);
    }

    public void execute() {
        // System.out.println(program.getMethods().size());
        // System.out.println(program.getCurrentMethod());
        // System.out.println(program.getCurrentMethod().getPc());

        program.getCurrentMethod().setPc(0);

        Method m = program.getMethods().pop();

        // System.out.println(m);
        program.setCurrentMethod(m);
    }
}
