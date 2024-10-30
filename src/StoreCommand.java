import java.util.Map;

public class StoreCommand extends Command {
	public StoreCommand(Program p) {
		super(p);
	}
	
	public void execute() {
		Map<String, Variable> vars = program.getCurrentMethod().getVars();
		Variable v = program.getStack().pop();
		vars.put(matcher.group(1), v);

		if (v.getClasse() != null) {
			program.addVariableInMemory(v);
		}
	}
}
