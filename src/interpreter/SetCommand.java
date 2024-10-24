package interpreter;

public class SetCommand extends Command {

    public SetCommand(Program m) {
        super(m);
    }

    public void execute() {
        String attributeName = matcher.group(1);

        Class obj = program.getStack().pop().getClasse();
        Class objAttr = searchVariable(attributeName, obj);

        Variable value = program.getStack().pop();

        objAttr.getVars().put(attributeName, value);
    }

    public static Class searchVariable(String attributeName, Class obj) {

        if (obj.getVars().containsKey(attributeName)) {
            return obj;
        }

        return searchVariable(attributeName, obj.getVars().get("_prototype").getClasse());
    }
}
