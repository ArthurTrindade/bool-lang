package interpreter;


public class ElseCommand extends Command {

    public ElseCommand(Program program) {
        super(program);
    }

    @Override
    public void execute() {
        int numIns = Integer.parseInt(matcher.group(1));
        Method method = program.getCurrentMethod();
        method.updatePc(numIns);
    }

}
