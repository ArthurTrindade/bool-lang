package interpreter;


import java.util.Scanner;

public class IoCommand extends Command {
    public IoCommand(Program p) {
        super(p);
    }

    @Override
    public void execute() {
        String op = matcher.group(1);
        Variable variable;
        switch (op) {
            case "sout":
                variable = program.getStack().pop();
                System.out.println(variable.getValue());
                break;

            case "sin":
                Scanner sc = new Scanner(System.in);
                int num = sc.nextInt();
                variable = new Variable(num);
                program.getStack().push(variable);
            default:
                break;
        }
    }

}
