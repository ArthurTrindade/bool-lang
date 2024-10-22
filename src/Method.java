import java.util.List;
import java.util.Map;

public class Method {
	private String name;
	private List<String> params;
	private Map<String, Variable> vars;
	private List<String> body;
	private Variable self = new Variable();

	public Method(String name) {
		this.name = name;
	}

	public Method(String name, Map<String, Variable> vars) {
		this.name = name;
		this.vars = vars;
	}

	public Method(String name, Map<String, Variable> vars, List<String> body) {
		this.name = name;
		this.vars = vars;
		this.body = body;
	}

	public Method() {}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Map<String, Variable> getVars() {
		return vars;
	}

	public void setVars(Map<String, Variable> vars) {
		this.vars = vars;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(List<String> body) {
		this.body = body;
	}

	public Variable getSelf() {
		return self;
	}

	public void setSelf(Variable self) {
		this.self = self;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}
}
