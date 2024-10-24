package interpreter;
import java.util.*;

public class Program {
    private Stack<Variable> stack = new Stack<>();
    private Stack<Method> methods = new Stack<>();
    private List<Class> classes = new ArrayList<>();
    private Method currentMethod = new Method();
    private List<Variable> memory = new ArrayList<>();

    public Program() {
    }

    public Program(Stack<Variable> stack, Stack<Method> methods, Method currentMethod) {
        this.stack = stack;
        this.methods = methods;
        this.currentMethod = currentMethod;
    }

    public void init(List<String> codes) {
        List<Class> classes = new ArrayList<>();
        Method mainMethod = new Method();

        for (int i = 0; i < codes.size(); i++) {
            codes.set(i, codes.get(i).trim());
        }

        codes.removeIf(String::isBlank);

        int classIndex = 0;
        for (int i = 0; i < codes.size(); i++) {

            if (codes.get(i).equals("end-class")) {
                List<String> classe = new ArrayList<>(codes.subList(classIndex, i + 1));

                Class newClass = Util.createClass(classe);

                List<Method> methods = new ArrayList<>();
                int methodIndex = 2;
                for (int j = 0; j < classe.size(); j++) {
                    if (classe.get(j).trim().equals("end-method")) {
                        List<String> methodList = new ArrayList<>(classe.subList(methodIndex, j + 1));
                        methodIndex = j + 1;
                        Method method = Util.createMethod(methodList);
                        methods.add(method);
                    }
                }

                newClass.setMethods(methods);
                classes.add(newClass);

                classIndex = i + 1;
            }

            if (codes.get(i).contains("main()")) {
                int m = i;
                List<String> mainList = new ArrayList<>(codes.subList(m, codes.size()));
                mainMethod = Util.createMethod(mainList);
                mainMethod.setName("main");
            }
        }

        this.classes = classes;
        this.currentMethod = mainMethod;

    }

    public void addVariableInMemory(Variable v) {
        this.memory.add(v);
    }

    public void addVariableInStack(Variable v) {
        this.stack.push(v);
    }

    public Stack<Variable> getStack() {
        return stack;
    }

    public Stack<Method> getMethods() {
        return methods;
    }

    public void addMethods(Method method) {
        this.methods.push(method);
    }

    public void setMethods(Stack<Method> methods) {
        this.methods = methods;
    }

    public Method getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
    }

    public void setStack(Stack<Variable> stack) {
        this.stack = stack;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public void addClass(Class c) {
        classes.add(c);
    }

    public List<Variable> getMemory() {
        return memory;
    }

    public void setMemory(List<Variable> memory) {
        this.memory = memory;
    }


}
