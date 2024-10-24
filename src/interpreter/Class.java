package interpreter;

import java.util.*;

public class Class {
    private String name;
    private Map<String, Variable> vars;
    private List<Method> methods;

    public Class() {
    }

    public Class(String name, List<Method> methods) {
        this.name = name;
        this.methods = methods;
    }

    public Class(String name, Map<String, Variable> vars) {
        this.name = name;
        this.vars = vars;
    }

    public Class(String name, Map<String, Variable> vars, List<Method> methods) {
        this.name = name;
        this.vars = vars;
        this.methods = methods;
    }

    public Class(String name) {
        this.name = name;
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

    public List<Method> getMethods() {
        return this.methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }
}
