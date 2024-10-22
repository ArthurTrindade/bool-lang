import java.util.List;
import java.util.Map;

public class Class {
	private String name;
	private Map<String, Variable> vars;
	private List<Method> methods;

	public Class() {
	}
	
	public Class(String name) {
		this.name = name;
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
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public void setSelf() {
		for (var m : methods) {
			m.setSelf(new Variable(this));
		}
	}

}
