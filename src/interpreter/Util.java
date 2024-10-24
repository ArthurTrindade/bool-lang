package interpreter;

import java.util.*;
import java.util.regex.*;

public class Util {

    public static Class searchVariable(String attributeName, Class obj) {

        if (obj.getVars().containsKey(attributeName)) {
            return obj;
        }

        return searchVariable(attributeName, obj.getVars().get("_prototype").getClasse());
    }

    public static Method searchMethod(String methodName, Class obj) {

        for (var method : obj.getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        return searchMethod(methodName, obj.getVars().get("_prototype").getClasse());
    }

    public static Class createClassIO() {

        Map<String, Variable> varsMethod = new HashMap<>();
        varsMethod.put("zero", new Variable());
        List<String> params = List.of("x");
        List<String> body = List.of("load x", "sout", "load zero", "ret");

        Method method = new Method("print", varsMethod, body, params);
        List<Method> methods = List.of(method);

        Class Io = new Class("Io", new HashMap<>(), methods);

        return Io;
    }

    public static Class createClass(List<String> classe) {
        Pattern classPattern = Pattern.compile("\\s*class\\s+([A-Z][a-zA-Z]*)");
        Matcher classMatcher = classPattern.matcher(classe.get(0));

        String name = "";
        Map<String, Variable> attributes = createVars(classe.get(1));

        if (classMatcher.matches()) {
            name = classMatcher.group(1);
        }

        attributes.put("_prototype", null);

        return new Class(name, attributes);
    }

    public static Method createMethod(List<String> method) {
        Pattern paramsPattern = Pattern.compile("\\s*method\\s+([a-zA-Z]+)\\(((\\s*[a-zA-Z]+,?\\s*)*)\\)");
        Matcher paramsMatcher = paramsPattern.matcher(method.get(0));

        String name = "";
        List<String> body = new ArrayList<>(method.subList(3, method.size() - 1));
        List<String> params = new ArrayList<>();
        Map<String, Variable> vars = createVars(method.get(1));

        if (paramsMatcher.matches()) {
            name = paramsMatcher.group(1);
            String parametros[] = paramsMatcher.group(2).split("\\s*,\\s+");
            for (var p : parametros) {
                params.add(p);
            }
        }

        vars.put("self", new Variable());

        return new Method(name, vars, body, params);
    }

    public static Map<String, Variable> createVars(String line) {
        Pattern varsPattern = Pattern.compile("vars\\s*((\\s*[a-zA-Z]+,?\\s*)*)");
        Matcher varsMatcher = varsPattern.matcher(line);

        Map<String, Variable> vars = new HashMap<>();

        if (varsMatcher.matches()) {
            String v[] = varsMatcher.group(1).split("\\s*,\\s+");
            for (var p : v) {
                vars.put(p, new Variable());
            }
        }

        return vars;
    }
}
