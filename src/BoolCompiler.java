import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoolCompiler {
	private static Scanner sc;
	
	public static void setScanner(String file) throws IOException {
		sc = new Scanner(new File(file));
	}
	
	public static void run(String sourceName, String destName) throws IOException {
		File dest  = new File(destName);
		boolean createdFile = dest.createNewFile();
		setScanner(sourceName);
		
		List<String> compiledFile = new ArrayList<>();
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			List<String> str = compile(line);
			compiledFile.addAll(str);
		}
		
		Files.write(Paths.get(destName), compiledFile);
		
		sc.close();
	}
	
	// Regexes
	final static String[] regexes = {
			"(\\s*)return\\s+([a-zA-Z]+)",                     									 // 0. return
			
			"(\\s*)([a-zA-Z]+)\\s+=\\s+([0-9]+)", 												// 1. atribuição a = 10
			"(\\s*)([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)",                                            // 2. atribuição a = b
			"(\\s*)([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)\\.+([a-zA-Z]+)",                             // 3. atribuição a = b.obj
			
			"(\\s*)([a-zA-Z]+)\\.([a-zA-Z]+)\\s+=\\s+([0-9]+)",									// 4. atribuição a.obj = 10
			"(\\s*)([a-zA-Z]+)\\.([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)",								// 5. atribuição a.obj = b
			"(\\s*)([a-zA-Z]+)\\.(_prototype)\\s+=\\s+([a-zA-Z]+)",								// 6. prototype
			"(\\s*)([a-zA-Z]+)\\.([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)\\.+([a-zA-Z]+)",				// 7. atribuição a.obj = b.obj
			
			"(\\s*)([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)\\s+([+*/-])\\s+([a-zA-Z]+)", 				// 8. arirmetica a = b + c
			"(\\s*)([a-zA-Z]+)\\.([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)\\s+([+*/-])\\s+([a-zA-Z]+)",	// 9. aritmetica a.obj = b + c
			
			"(\\s*)([a-zA-Z]+)\\s+=\\s+new\\s+([a-zA-Z]+)",										// 10. object creation
			
			"(\\s*)([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)\\.([a-zA-Z]+)\\(([^)]*)\\)",					// 11. chamada de metodo a = obj.method() ou a = obj.method(x,y...)
			"(\\s*)([a-zA-Z]+)\\.([a-zA-Z]+)\\s+=\\s+([a-zA-Z]+)\\.([a-zA-Z]+)\\(([^)]*)\\)",	// 12. chamada de metodo a.obj = obj.method() ou a.obj = obj.method(x,y...)
			"(\\s*)([a-zA-Z]+)\\.([a-zA-Z]+)\\(([^)]*)\\)",										// 13. chamada de metodo obj.method() ou  obj.method(x,y...)
			
			"\\s*if\\s+([a-zA-Z]+)\\s+([a-zA-Z]+)\\s+([a-zA-Z]+)\\s+then",						// 14. if
		
	};
	
	
	public static List<String> compile(String line) throws IOException {
		List<String> str = new ArrayList<>();
		
		boolean matched = false;
		
		// Comparar com todos os regexes
		for (int i = 0; i < regexes.length; i++) {
			Pattern pattern = Pattern.compile(regexes[i]);
			Matcher matcher = pattern.matcher(line);
			
			if (!matcher.matches()) { continue; }
			matched = true;
			
			// return
			if (i == 0) { // return
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "ret");
				
				break;
			}
			
			if (i == 1) { // atribuição a = 10
				
				str.add(matcher.group(1) + "const " + matcher.group(3));
				str.add(matcher.group(1) + "store " + matcher.group(2));
				
				break;
			}
			
			if  (i == 2) { //  atribuição a = b
				
				str.add(matcher.group(1) + "load " + matcher.group(3));
				str.add(matcher.group(1) + "store " + matcher.group(2));
				
				break;
			}
			
			if (i == 3) { //atribuição a = b.obj
				
				str.add(matcher.group(1) + "load " + matcher.group(3));
				str.add(matcher.group(1) + "get " + matcher.group(4));
				str.add(matcher.group(1) + "store " + matcher.group(2));
				
				break;
			}
			
			if (i == 4) { //atribuição a.obj = 10 {
				
				str.add(matcher.group(1) + "const " + matcher.group(4));
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "set " + matcher.group(3));
				
				break;
			}
			
			if (i == 5 || i == 6 ) { //atribuição a.obj = b ou obj._prototype = b
				
				str.add(matcher.group(1) + "load " + matcher.group(4));
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "set " + matcher.group(3));
				
				break;
			}
			
			if ( i == 7) { //atribuição a.obj = b.obj {
				
				str.add(matcher.group(1) + "load " + matcher.group(4));
				str.add(matcher.group(1) + "get " + matcher.group(4));
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "set " + matcher.group(3));
				
				break;
			}
			
			if (i == 8) { //atribução a = b op c
				
				str.add(matcher.group(1) + "load " + matcher.group(3));
				str.add(matcher.group(1) + "load " + matcher.group(5));
				
				switch (matcher.group(4)) {
					case "+":
						str.add(matcher.group(1) + "add");
						break;
					case "-":
						str.add(matcher.group(1) + "sub");
						break;
					case "*":
						str.add(matcher.group(1) + "mul");
						break;
					case "/":
						str.add(matcher.group(1) + "div");
						break;
					default:
						break;
				}
				
				str.add(matcher.group(1) + "store " + matcher.group(2));
				
				break;
			}
			
			
			if (i == 9) { //atribução a.obj = b op c
				
				str.add(matcher.group(1) + "load " + matcher.group(4));
				str.add(matcher.group(1) + "load " + matcher.group(6));
				
				switch (matcher.group(5)) {
					case "+":
						str.add(matcher.group(1) + "add");
						break;
					case "-":
						str.add(matcher.group(1) + "sub");
						break;
					case "*":
						str.add(matcher.group(1) + "mul");
						break;
					case "/":
						str.add(matcher.group(1) + "div");
						break;
					default:
						break;
				}
				
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "set " + matcher.group(3));
				
				break;
			}
			
			
			if (i == 10) { //criação de objeto
				
				str.add(matcher.group(1) + "new " + matcher.group(3));
				str.add(matcher.group(1) + "store " + matcher.group(2));
				
				break;
			}
			
			if  (i == 11) { // chamada de metodo a = obj.method() ou a = obj.method(x,y...)
				
				String aux = matcher.group(5);
				if (!aux.isEmpty()) {
					String[] varsArray = aux.split(", ");
					
					for (String vars : varsArray) {
						str.add(matcher.group(1) + "load " + vars);
					}
				}
				
				str.add(matcher.group(1) + "load " + matcher.group(3));
				str.add(matcher.group(1) + "call " + matcher.group(4));
				str.add(matcher.group(1) + "store " + matcher.group(2));
				
				break;
			}
			
			
			if (i == 12) { // chamada de metodo a.obj = obj.method() ou a.obj = obj.method(x,y...)
				
				// tratamento dos argumentos de função
				String aux = matcher.group(6);
				if (!aux.isEmpty()) {
					String[] varsArray = aux.split(", ");
					
					for (String vars : varsArray) {
						str.add(matcher.group(1) + "load " + vars);
					}
				}
				
				str.add(matcher.group(1) + "load " + matcher.group(4));
				str.add(matcher.group(1) + "call " + matcher.group(5));
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "set " + matcher.group(3));
				
				break;
			}
			
			if (i == 13) { // chamada de metodo obj.method() ou  obj.method(x,y...)
				
				// tratamento dos argumentos de função
				String aux = matcher.group(4);
				if (!aux.isEmpty()) {
					String[] varsArray = aux.split(", ");
					
					for (String vars : varsArray) {
						str.add(matcher.group(1) + "load " + vars);
					}
				}
				
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "call " + matcher.group(3));
				str.add(matcher.group(1) + "pop");
				
				break;
			}
			
			if (i == 14) { // if
				
				str.add("load " + matcher.group(1));
				str.add("load " + matcher.group(3));
				str.add(matcher.group(2));
				str.add("if 0");
				
				String nextLine = sc.nextLine();
				int countIf = 0, countElse = 0;
				boolean temElse = false;
				
				while (!nextLine.matches("\\s*end-if") && sc.hasNextLine()) {
					List<String> str2 = compile(nextLine);
					
					// se str2 tiver linha em branco não pode aumentar os contadores
					if (!str2.getFirst().trim().isEmpty()) {
						countIf += str2.size();
						
						if (temElse) {
							countElse += str2.size();
						}
					}
					
					str.addAll(str2);
					nextLine = sc.nextLine();
					
					if (nextLine.matches("\\s*else")) {
						temElse = true;
						str.add("else 0");
						nextLine = sc.nextLine();
					}
				}
				
				if (temElse) {
					countIf -= countElse;
					
					int indexElse = str.indexOf("else 0");
					str.set(indexElse, "else " + countElse);
				}
				
				// voltar na linha do if e modificar ela com countIf
				int indexIf = str.indexOf("if 0");
				str.set(indexIf, "if " + countIf);
				
				break;
			}
			
		}
		
		if (!matched) {
			str.add(line);
		}
		
		return str;
	}
}
