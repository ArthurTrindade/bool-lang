package interpreter;

public class IfCommand extends Command {

    public IfCommand(Program program) {
        super(program);

    }

    @Override
    public void execute() {
        int numIns = Integer.parseInt(matcher.group(1));
        boolean result = program.getStack().pop().getCondition();
        Method method = program.getCurrentMethod();

        if (!result) {
            method.updatePc(numIns + 1);
        }

    }

}