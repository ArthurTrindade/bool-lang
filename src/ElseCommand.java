public class ElseCommand extends Command {
    public ElseCommand(Program p) {
        super(p);
    }

    @Override
    public void execute() {
        int numIns = Integer.parseInt(matcher.group(1));
        Method method = program.getCurrentMethod();
        method.updatePc(numIns);
    }

}
