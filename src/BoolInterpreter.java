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

	private static void setClasses() {
		classes = new ArrayList<>();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 0) {
			run(args[0]);
		}
	}
	
	private static void run(String sourceName) throws IOException {
		setScanner(sourceName);
		setStack();

		Map<String, Variable> atributes = new HashMap<>();

		atributes.put("id", new Variable(10));
		atributes.put("idade", new Variable(20));

		Class c = new Class("Pessoa", atributes);

		classes.add(c);

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
			"\\s*new\\s*([a-zA-Z]+)"                        // 8. new <name>
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
		Class c = classes.getFirst();
		Variable variable = new Variable(c);
		stack.push(variable);
	}
}

class MainMethod {
	private final Map<String, Variable> vars = new HashMap<>();
	private List<String> body;


	public void addVariable(String name, Variable value) {
		vars.put(name, value);
	}
	
	public void updateVariable(String name, Variable variable) {
//		Pattern pattern = Pattern.compile("[a-zA-Z]+");
//		Matcher matcher = pattern.matcher(pop);
//		Variable v = new Variable();
//		 name
//		if (matcher.matches()) {
//			v = getVariable(pop);
//		}
//
//		v.setValue(Integer.parseInt(pop));

		vars.put(name, variable);
	}
	
	public Variable getVariable(String name) {
		return vars.get(name);
	}
}

class Class {
	private String name;
	private Map<String, Variable> vars;
	private List<Method> methods;

	public Class() {
	}

	public Class(String name, Map<String, Variable> vars) {
		this.name = name;
		this.vars = vars;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Variable> getVars() {
		return vars;
	}

	public void setVars(Map<String, Variable> vars) {
		this.vars = vars;
	}
}

class Method {
	private String name;
	private List<String> params;
	private List<String> vars;
	
	private List<String> body;
	public Method() {
	}
}

class Variable {
	private int value = 0;
	private boolean condition;
	private Class classe;

	public Variable() {}

	public Variable(Class classe) {
		this.classe = classe;
	}

	public Variable(int value) {
		this.value = value;
	}

	public Variable(boolean condition) {
		this.condition = condition;
	}

	public void setClasse(Class classe) {
		this.classe = classe;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Class getClasse() {
		return classe;
	}

	public int getValue() {
		return value;
	}

	public boolean getCondition() {
		return condition;
	}

	public void setCondition(boolean condition) {
		this.condition = condition;
	}
}