import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static Scanner sc;
	
	public static void setScanner(String file) throws IOException {
		sc = new Scanner(new File(file));
	}
	
	public static void main(String[] args) throws IOException {
		run("src/main.bool");
	}
	
	// Regexes
	final static String[] regexes = {
			"(\\s*)return\\s+([a-zA-Z]+)",											// return
			"(\\s*)([a-zA-Z]+)\\s+=\\s+([a-zA-Z|0-9]+)",							// attribution
			"\\s*if\\s+([a-zA-Z]+)\\s+([a-zA-Z]+)\\s+([a-zA-Z]+)\\s+then"			// if
	};
	
	public static boolean isNumber(String term) throws IOException {
		Pattern patternType = Pattern.compile("[0-9]+");
		Matcher matcher = patternType.matcher(term);
		
		return matcher.matches();
	}
	
	public static boolean isName(String term) throws IOException {
		Pattern patternType = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = patternType.matcher(term);
		
		return matcher.matches();
	}
	
	public static List<String> compile(String line) throws IOException {
		List<String> str = new ArrayList<>();
		
		// Comparar com todos os regexes
		for (int i = 0; i < regexes.length; i++) {
			Pattern pattern = Pattern.compile(regexes[i]);
			Matcher matcher = pattern.matcher(line);
			
			if (!matcher.matches()) { continue; }
			
			// return
			if (i == 0) {
				str.add(matcher.group(1) + "load " + matcher.group(2));
				str.add(matcher.group(1) + "ret");
				break;
			}
			
			// attribution
			if (i == 1) {
				String lhs = matcher.group(2);
				String rhs = matcher.group(3);
				
				if (isNumber(rhs)) {
					str.add(matcher.group(1) + "const " + rhs);
				} else if (isName(rhs)) {
					str.add(matcher.group(1) + "load " + rhs);
				}
				
				str.add(matcher.group(1) + "store " + lhs);
				
				break;
			}
			
			// if
			if (i == 2) {
				str.add("load " + matcher.group(1));
				str.add("load " + matcher.group(3));
				str.add(matcher.group(2));
				str.add("if 0");
				
				String nextLine = sc.nextLine();
				int countIf = 0, countElse = 0;
				boolean temElse = false;
				
				while (!nextLine.matches("\\s*end-if") && sc.hasNextLine()) {
					// voltar na linha do if e modificar ela com countIf
					List<String> str2 = compile(nextLine);
					countIf += str2.size();
					str.addAll(str2);
					
					nextLine = sc.nextLine();
					
					if (temElse) {
						countElse++;
					}
					
					if (nextLine.matches("\\s*else")) {
						temElse = true;
						str.add("else 0");
					}
				}
				
				if (temElse) {
					countIf -= countElse;
					
					int indexElse = str.indexOf("else 0");
					str.set(indexElse, "else " + countElse);
				}
				
				int indexIf = str.indexOf("if 0");
				str.set(indexIf, "if " + countIf);
			}
		}
		
		return str;
	}
	
	public static void run(String name) throws IOException {
		String newName = createFile(name);
		
		setScanner(name);
		FileWriter fw = new FileWriter(newName);
		
		List<String> outfile = new ArrayList<>();
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			List<String> str = compile(line);
			outfile.addAll(str);
		}
		
		Files.write(Paths.get(newName), outfile);
		
		fw.close();
		sc.close();
	}
	
	public static String createFile(String name) {
		String newName = name + "c";
		
		try {
			
			var newFile = new File(newName);
			
			if (newFile.createNewFile()) {
				System.out.println("arquivo criado: " + newFile.getName());
			} else {
				System.out.println("arquivo ja existe");
			}
			
		} catch (IOException e) {
			System.out.println("erro");
			e.printStackTrace();
		}
		
		return newName;
	}
	
	
}

