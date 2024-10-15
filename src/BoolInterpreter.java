import java.io.File;
import java.io.IOException;
import java.util.*;


public class BoolInterpreter {
	public static Stack<String> stack;
	private static Scanner sc;
	
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
		MainMethod mainMethod = new MainMethod();
		
		line = sc.nextLine();	// vars num1, num2
		// TODO: pegar variáveis da main com regex
		mainMethod.addVariable("numUm", 0);
		mainMethod.addVariable("numDois", 0);
		mainMethod.addVariable("numTres", 0);
		
		line = sc.nextLine();	// begin
		// TODO: descobrir instrução com regex
		
		line = sc.nextLine();	// const 10
		stack.push("10");
		
		line = sc.nextLine();	// store numUm
		mainMethod.updateVariable("numUm");
		
		line = sc.nextLine();	// const 20
		stack.push("20");
		
		line = sc.nextLine();	// store numDois
		mainMethod.updateVariable("numUm");
		
		line = sc.nextLine();	// load numUm
		stack.push("numUm");
		
		line = sc.nextLine();	// load numDois
		stack.push("numDois");
		
		line = sc.nextLine();	// add
		var v2 = stack.pop();
		var v1 = stack.pop();
		var result = v1 + v2;
		stack.push(result);
		
		line = sc.nextLine();	// store numTres
		mainMethod.updateVariable("numTres");
		
		line = sc.nextLine();	// end
		
		System.out.println("Valores das variáveis:");
		
		System.out.println(mainMethod.getVariable("numUm"));
		System.out.println(mainMethod.getVariable("numDois"));
		System.out.println(mainMethod.getVariable("numTres"));
		
		sc.close();
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
	private Map<String, Object> vars = new HashMap<>();
	private List<String> body;
	
	public void addVariable(String name, Object value) {
		vars.put(name, value);
	}
	
	public void updateVariable(String name) {
		var pop = BoolInterpreter.stack.pop();
		vars.put(name, pop);
	}
	
	public Object getVariable(String name) {
		return vars.get(name);
	}
}
