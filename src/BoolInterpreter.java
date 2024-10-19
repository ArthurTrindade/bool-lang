import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BoolInterpreter {
	public static Stack<Variable> stack;
	private static Scanner sc;
	private static List<Class> classes = new ArrayList<>();

	static MainMethod mainMethod = new MainMethod();

	private static void setScanner(String file) throws IOException {
		sc = new Scanner(new File(file));
	}
	
	private static void setStack() {
		stack = new Stack<>();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 0) {
			run(args[0]);
		}
	}
	
	private static void run(String sourceName) throws IOException {
		setScanner(sourceName);
		setStack();

		Map<String, Variable> atributes1 = new HashMap<>();
		atributes1.put("id", new Variable());
		atributes1.put("idade", new Variable(20));

		Map<String, Variable> atributesM = new HashMap<>();
		atributesM.put("x", new Variable());
		List<String> bodyM = List.of("const 20", "ret");
		Method m1 = new Method("retorna", atributesM, bodyM);
		List<Method> methods = List.of(m1);

		Class c1 = new Class("Pessoa", atributes1);

		Map<String, Variable> atributes2 = new HashMap<>();
		atributes2.put("num", new Variable(100));
		Class c2 = new Class("Num", atributes2, methods);

		classes.add(c1);
		classes.add(c2);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			interpret(line); 
		}
		
		System.out.println("Valores das variáveis:");
		System.out.println("numUm: " + mainMethod.getVariable("numUm").getValue());
		System.out.println("numDois: " + mainMethod.getVariable("numDois").getValue());
		System.out.println("numTres: " + mainMethod.getVariable("numTres").getValue());
		System.out.println("p: " + mainMethod.getVariable("p").getClasse().getName());
		System.out.println("a: " + mainMethod.getVariable("a").getClasse().getName());

		System.out.println("p.id: " + mainMethod.getVariable("p").getClasse().getVars().get("id").getValue());
		System.out.println("a.id: " + mainMethod.getVariable("a").getClasse().getVars().get("id").getValue());

		System.out.println("p.idade: " + mainMethod.getVariable("p").getClasse().getVars().get("idade").getValue());
		System.out.println("a.idade: " + mainMethod.getVariable("a").getClasse().getVars().get("idade").getValue());

		System.out.println("n.num: " + mainMethod.getVariable("n").getClasse().getVars().get("num").getValue());

		System.out.println("b: " + mainMethod.getVariable("m").getValue());

		sc.close();
	}
	
	// Regexes
	final static String[] regexes = {
			"\\s*(add|sub|mul|div)",						// 0. operações aritméticas
			
			"\\s*const\\s*(-?[0-9]+)",						// 1. const 10
			"\\s*load\\s*([a-zA-Z]+)",						// 2. load var
			
			"\\s*store\\s*([a-zA-Z]+)",						// 3. store var
			
			"\\s*vars\\s+([a-zA-Z]+(?:,? ?[a-zA-Z]+)*)",	// 4. vars varUm, varDois
			
			"\\s*(gt|ge|lt|le|eq|ne)",						// 5. comparações
			"\\s*if\\s+([0-9]+)",							// 6. if
			"\\s*else\\s+([0-9]+)",							// 7. else
			"\\s*new\\s*([a-zA-Z]+)",                       // 8. new <name>
			"\\s*call\\s*([a-zA-Z]+)"						// 9. call <name>
	};

	public static void interpret(String line) {
		boolean matched = false;
		
		// Comparar todos os regexes para descobrir qual a instrução
		for (int i = 0; i < regexes.length; i++) {
			Pattern pattern = Pattern.compile(regexes[i]);
			Matcher matcher = pattern.matcher(line);
			
			if (!matcher.matches()) { continue; }
			
			switch (i) {
				// operações aritméticas
				case 0:
					interpretMath(matcher.group(1));
					matched = true;
					break;
				
				// const
				case 1:
					interpretConst(matcher.group(1));
					matched = true;
					break;
					
				// load
				case 2:
					interpretLoad(matcher.group(1));
					matched = true;
					break;
					
				// store
				case 3:
					interpretStore(matcher.group(1));
					matched = true;
					break;
					
				// vars
				case 4:
					interpretVars(matcher.group(1));
					matched = true;
					break;
					
				// comparações
				case 5:
					interpretLogic(matcher.group(1));
					matched = true;
					break;
					
				// if
				case 6:
					interpretIf(matcher.group(1));
					matched = true;
					break;
					
				// else
				case 7:
					interpretElse(matcher.group(1));
					matched = true;
					break;

				case 8:
					interpretNew(matcher.group(1));
					matched = true;
					break;

				case 9:
					interpretCall(matcher.group(1));
					matched = true;
					break;
				default:
					break;
			}

			if (matched) { break; }
		}
	}
	
	public static void interpretMath(String op) {
		int v2 = stack.pop().getValue();
		int v1 = stack.pop().getValue();

		int result = switch (op) {
			case "add" -> v1 + v2;
			case "sub" -> v1 - v2;
			case "mul" -> v1 * v2;
			case "div" -> v1 / v2;
			default -> 0;
		};

		Variable v = new Variable(result);
		stack.push(v);
	}

	public static void interpretConst(String value) {
		Variable v = new Variable(Integer.parseInt(value));
		stack.push(v);
	}

	public static void interpretLoad(String name) {
		stack.push(mainMethod.getVariable(name));
	}
	
	public static void interpretStore(String name) {
		Variable variable = stack.pop();
		mainMethod.updateVariable(name, variable);
	}
	
	public static void interpretVars(String names) {
		String[] namesList = names.split("\\s*,\\s+");
		
		for (String name : namesList) {
			mainMethod.addVariable(name, new Variable());
		}
	}
	
	public static void interpretLogic(String op) {
		int v2 = stack.pop().getValue();
		int v1 = stack.pop().getValue();

		boolean result = switch (op) {
			case "gt" -> v1 >  v2;
			case "ge" -> v1 >= v2;
			case "lt" -> v1 <  v2;
			case "le" -> v1 <= v2;
			case "eq" -> v1 == v2;
			case "ne" -> v1 != v2;
			default -> false;
		};

		Variable newVariable = new Variable(result);
		stack.push(newVariable);
	}
	
	public static void interpretIf(String value) {
		int numLines = Integer.parseInt(value);
		boolean skip = stack.pop().getCondition();

		if (!skip) {	// pula if e executa else
			for (int i = 0; i < numLines; i++) {
				String line = sc.nextLine(); // instruções do if

				// Ignora linhas em branco
				if (line.trim().isEmpty()) {
					i--;
				}
			}
			
			sc.nextLine(); // else
		}
	}
	
	public static void interpretElse(String value) {
		int numLines = Integer.parseInt(value);
		
		// Se chegar aqui é porque o if deu certo e simplesmente
		// devemos pular as linhas do else
		for (int i = 0; i < numLines; i++) {
			String line = sc.nextLine(); // instruções do else
			
			// Ignora linhas em branco
			if (line.trim().isEmpty()) {
				i--;
			}
		}
	}

	public static void interpretNew(String name) {
		Class newClass = new Class();
		for (var c : classes) {
			if (c.getName().equals(name.trim())) {
				newClass = c;
			}
		}
		stack.push(new Variable(newClass));
	}

	public static void interpretCall(String name) {
		Variable v = stack.pop();
		List<Method> methods = v.getClasse().getMethods();

		for (var m : methods) {
			if (m.getName().equals(name.trim())) {
				List<String> lines = m.getBody();
				for (var line : lines) {
					interpret(line);
				}
			}
		}
	}
}

class MainMethod {
	private final Map<String, Variable> vars = new HashMap<>();
	private List<String> body;


	public void addVariable(String name, Variable value) {
		vars.put(name, value);
	}
	
	public void updateVariable(String name, Variable variable) {
		vars.put(name, variable);
	}
	
	public Variable getVariable(String name) {
		return vars.get(name);
	}
}

