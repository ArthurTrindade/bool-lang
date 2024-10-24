package interpreter;


public class PopCommand extends Command {

    public PopCommand(Program program) {
        super(program);
    }

    public void execute() {
        program.getStack().pop();
    }

}
