package interpreter;

public class Variable {
    private int value = 0;
    private boolean condition;
    private Class classe = new Class();

    public Variable() {
    }

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
