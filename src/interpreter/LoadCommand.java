package interpreter;


import java.util.*;

public class LoadCommand extends Command {
	public LoadCommand(Program p) {
		super(p);
	}
	
	public void execute() {
		Map<String, Variable> vars = program.getCurrentMethod().getVars();
		Variable v = vars.get(matcher.group(1));
		program.addVariableInStack(v);
	}
}
