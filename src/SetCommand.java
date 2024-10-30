public class SetCommand extends Command {
    public SetCommand(Program p) {
        super(p);
    }

    public void execute() {
        String attributeName = matcher.group(1);

        Class obj = program.getStack().pop().getClasse();
        Class objAttr = Util.searchVariable(attributeName, obj);

        Variable value = program.getStack().pop();

        objAttr.getVars().put(attributeName, value);
    }
}
