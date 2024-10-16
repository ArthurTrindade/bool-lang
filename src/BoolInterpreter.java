import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BoolInterpreter {
	public static Stack<String> stack;
	private static Scanner sc;

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
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			interpret(line); 
		}
		
		System.out.println("Valores das variáveis:");
		System.out.println("numUm: " + mainMethod.getVariable("numUm"));
		System.out.println("numDois: " + mainMethod.getVariable("numDois"));
		System.out.println("numTres: " + mainMethod.getVariable("numTres"));
		
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
					
				default:
					break;
			}
			
			if (matched) { break; }
		}
	}
	
	public static void interpretMath(String op) {
		int v2 = Integer.parseInt(mainMethod.getVariable(stack.pop()));
		int v1 = Integer.parseInt(mainMethod.getVariable(stack.pop()));
		int result = switch (op) {
			case "add" -> v1 + v2;
			case "sub" -> v1 - v2;
			case "mul" -> v1 * v2;
			case "div" -> v1 / v2;
			default -> 0;
		};
		stack.push(String.valueOf(result));
	}
	
	public static void interpretConst(String value) {
		stack.push(value);
	}

	public static void interpretLoad(String name) {
		stack.push(name);
	}
	
	public static void interpretStore(String name) {
		mainMethod.updateVariable(name);
	}
	
	public static void interpretVars(String names) {
		String[] namesList = names.split("\\s*,\\s+");
		
		for (String name : namesList) {
			mainMethod.addVariable(name, "0");
		}
	}
	
	public static void interpretLogic(String op) {
		int v2 = Integer.parseInt(mainMethod.getVariable(stack.pop()));
		int v1 = Integer.parseInt(mainMethod.getVariable(stack.pop()));
		boolean result = switch (op) {
			case "gt" -> v1 >  v2;
			case "ge" -> v1 >= v2;
			case "lt" -> v1 <  v2;
			case "le" -> v1 <= v2;
			case "eq" -> v1 == v2;
			case "ne" -> v1 != v2;
			default -> false;
		};
		stack.push(String.valueOf(result));
	}
	
	public static void interpretIf(String value) {
		int numLines = Integer.parseInt(value);
		boolean skip = stack.pop().equals("false");
		
		if (skip) {	// pula if e executa else
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
}

class MainMethod {
	private Map<String, String> vars = new HashMap<>();
	private List<String> body;
	
	public void addVariable(String name, String value) {
		vars.put(name, value);
	}
	
	public void updateVariable(String name) {
		String pop = BoolInterpreter.stack.pop();
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = pattern.matcher(pop);
		
		// name
		if (matcher.matches()) {
			pop = getVariable(pop);
		}
		
		vars.put(name, pop);
	}
	
	public String getVariable(String name) {
		return vars.get(name);
	}
}

class Class {
	private String name;
	private List<String> vars;
	
	private List<Method> methods;
	public Class() {
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
