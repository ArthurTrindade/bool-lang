import java.util.HashMap;
import java.util.Map;

public class NewCommand extends Command {
	public NewCommand(Program p) {
		super(p);
	}
	
	public void execute() {
		String name = matcher.group(1).trim();
		Class newClass = new Class();
		Class classObj = new Class();
		
		for (var c : program.getClasses()) {
			if (c.getName().equals(name)) {
				classObj = c;
			}
		}
		
		Map<String, Variable> newVars = new HashMap<>(classObj.getVars());
		
		// Necessário para evitar duas variáveis apontando para o mesmo objeto
		newClass.setName(name);
		newClass.setVars(newVars);
		newClass.setMethods(classObj.getMethods());
		
		Variable newVariable = new Variable(newClass);
		
		program.addVariableInStack(newVariable);
		program.addVariableInMemory(newVariable);
	}
}
