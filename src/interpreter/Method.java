package interpreter;

import java.util.*;
import java.util.Map;

public class Method {
	private int pc = 0;
	private String name;
	private Map<String, Variable> vars = new HashMap<>();
	private List<String> params = new ArrayList<>();
	private List<String> body;
	private Variable self = new Variable();
	
	public Method() {
	}
	
	public Method(String name, Map<String, Variable> vars, List<String> body) {
		this.name = name;
		this.vars = vars;
		this.body = body;
	}
	
	public Method(String name, Map<String, Variable> vars, List<String> body, List<String> params) {
		this.name = name;
		this.vars = vars;
		this.body = body;
		this.params = params;
	}
	
	public int getPc() {
		return pc;
	}
	
	public void setPc(int pc) {
		this.pc = pc;
	}
	
	public void updatePc(int num) {
		this.pc = this.pc + num;
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
	
	public List<String> getParams() {
		return params;
	}
	
	public void setParams(List<String> params) {
		this.params = params;
	}
}
