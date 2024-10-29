package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Variable {
    private int value = 0;
    private boolean condition;
    private Class classe = new Class();
    private String color = "gray";
    private List<Variable> refs = new ArrayList<Variable>();

    public Variable() {
    }

    public Variable(Class classe) {
        this.classe = classe;
        color = "gray";
    }

    public Variable(int value) {
        this.value = value;
        color = "gray";
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

    public String getColor() {return color; }

    public void setColor(String color) { this.color = color; }

}
