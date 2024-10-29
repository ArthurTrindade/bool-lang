package interpreter;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class Main {
	static Map<String, Command> commands = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		List<String> codes = Files.readAllLines(Paths.get("src/interpreter/test2.boolc"));
		
		Program program = new Program();
		program.init(codes);
		
		commands.put("\\s*const\\s*(-?[0-9]+)", new ConstCommand(program));
		commands.put("\\s*store\\s*([a-zA-Z]+)", new StoreCommand(program));
		commands.put("\\s*load\\s*([a-zA-Z]+)", new LoadCommand(program));
		commands.put("\\s*call\\s*([a-zA-Z]+)", new CallCommand(program));
		commands.put("\\s*new\\s+([a-zA-Z]+)", new NewCommand(program));
		commands.put("\\s*(add|sub|mul|div)", new MathCommands(program));
		commands.put("\\s*(gt|ge|lt|le|eq|ne)", new CompareCommand(program));
		commands.put("\\s*set\\s+([a-zA-Z]+)", new SetCommand(program));
		commands.put("\\s*get\\s+([a-zA-Z]+)", new GetCommand(program));
		commands.put("\\s*if\\s+([0-9]+)", new IfCommand(program));
		commands.put("\\s*else\\s+([0-9]+)", new ElseCommand(program));
		commands.put("\\s*(sout|sin)", new IoCommand(program));
		commands.put("\\s*(pop)", new PopCommand(program));
		commands.put("\\s*(ret)", new RetCommand(program));
		
		while (true) {
			Method currentMethod = program.getCurrentMethod();
			int currentPc = currentMethod.getPc();
			if (currentPc >= currentMethod.getBody().size()) break;
			String line = currentMethod.getBody().get(currentPc);
			getCommand(line).execute();
			currentMethod.updatePc(1);
			//CollectionCheck();
		}
		
		for (var key : program.getCurrentMethod().getVars().keySet()) {
			System.out.println(key + ": " + program.getCurrentMethod().getVars().get(key).getValue());
		}

		Systen.out.println("\n");

		Variable aux;
		for (var key : program.getMemory() ) {
			aux = key;
			System.out.println(aux.getClasse().getName());
			System.out.println(aux.getValue());
		}
	}
	
	public static Command getCommand(String line) {
		for (String key : commands.keySet()) {
			Pattern pattern = Pattern.compile(key);
			Matcher matcher = pattern.matcher(line);
			
			if (matcher.matches()) {
				Command c = commands.get(key);
				c.setMatcher(matcher);
				return c;
			}
		}
		
		return null;
	}
}
