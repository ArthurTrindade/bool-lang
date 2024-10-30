class ConstCommand extends Command {
	public ConstCommand(Program p) {
		super(p);
	}
	
	public void execute() {
		Variable v = new Variable(Integer.parseInt(matcher.group(1).trim()));
		program.addVariableInStack(v);
	}
}
