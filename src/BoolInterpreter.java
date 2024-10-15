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
		
//		while (sc.hasNextLine()) {
//			String line = sc.nextLine();
//			// TODO: interpret() 
//		}
		
		// main
		String line = sc.nextLine();
		// TODO: verificar main()

		
		line = sc.nextLine();	// vars num1, num2
		// TODO: pegar variáveis da main com regex
		mainMethod.addVariable("numUm", "0");
		mainMethod.addVariable("numDois", "0");
		mainMethod.addVariable("numTres", "0");

		line = sc.nextLine();	// begin
		// TODO: descobrir instrução com regex
		
		line = sc.nextLine();	// const 10
//		stack.push("10");
		interpret(line);
		
		line = sc.nextLine();	// store numUm
		mainMethod.updateVariable("numUm");
		
		line = sc.nextLine();	// const 20
//		stack.push("20");
		interpret(line);
		
		line = sc.nextLine();	// store numDois
		mainMethod.updateVariable("numDois");
		
		line = sc.nextLine();	// load numUm
//		stack.push("numUm");
		interpret(line);

		line = sc.nextLine();	// load numDois
//		stack.push("numDois");
		interpret(line);

		line = sc.nextLine();	// add

		interpret(line);

		line = sc.nextLine();	// store numTres
		mainMethod.updateVariable("numTres");
		
		line = sc.nextLine();	// end
		
		System.out.println("Valores das variáveis:");
		
		System.out.println(mainMethod.getVariable("numUm"));
		System.out.println(mainMethod.getVariable("numDois"));
		System.out.println(mainMethod.getVariable("numTres"));
		
		sc.close();
	}

	public static void interpret(String line) {
		Pattern pattern = Pattern.compile("\\s*add|\\s*sub|\\s*mul|\\s*div");
		Matcher matcher = pattern.matcher(line);

		if (matcher.matches()) {
			eval(line);
		}

		pattern = Pattern.compile("\\s*const\\s*(-?[0-9]+)");
		matcher = pattern.matcher(line);

		if (matcher.matches()) {
			interpretConst(matcher.group(1));
		}

		pattern = Pattern.compile("\\s*load\\s*([a-zA-Z]+)");
		matcher = pattern.matcher(line);

		if (matcher.matches()) {
			interpretLoad(matcher.group(1));
		}
	}

	public static void interpretConst(String value) {
		stack.push(value);
	}

	public static void interpretLoad(String name) {
		stack.push(name);
	}

	public static void eval(String line) {
		var v2 = Integer.parseInt(mainMethod.getVariable(stack.pop()));
		var v1 = Integer.parseInt(mainMethod.getVariable(stack.pop()));
		int result = switch (line.trim()) {
            case "add" -> v1 + v2;
            case "sub" -> v1 - v2;
            case "mul" -> v1 * v2;
            case "div" -> v1 / v2;
            default -> 0;
        };
		System.out.println("eval");
        stack.push(String.valueOf(result));
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

class MainMethod {
	private Map<String, String> vars = new HashMap<>();
	private List<String> body;
	
	public void addVariable(String name, String value) {
		vars.put(name, value);
	}
	
	public void updateVariable(String name) {
		var pop = BoolInterpreter.stack.pop();
		vars.put(name, pop);
	}
	
	public String getVariable(String name) {
		return vars.get(name);
	}
}
