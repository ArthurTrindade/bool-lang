package interpreter;

import java.util.*;

public class StoreCommand extends Command {
	public StoreCommand(Program p) {
		super(p);
	}
	
	public void execute() {
		Map<String, Variable> vars = program.getCurrentMethod().getVars();
		Variable v = program.getStack().pop();
		System.out.print(matcher.group(1) + ": " + v);
		System.out.print("\n");
		program.addVariableInMemory(v);
		vars.put(matcher.group(1), v);
	}
}
