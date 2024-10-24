package interpreter;

import java.util.*;

public class CallCommand extends Command {

    private Map<String, Variable> vars = new HashMap<>();

    public CallCommand(Program program) {
        super(program);
    }

    public void execute() {
        String methodName = matcher.group(1);
        Class obj = program.getStack().pop().getClasse();

        Method methodAux = Util.searchMethod(methodName, obj);

        Method method = new Method();
        method.setName(methodAux.getName());
        method.setParams(methodAux.getParams());
        method.setBody(methodAux.getBody());
        method.setPc(0);

        List<String> r = method.getParams().reversed();

        Map<String, Variable> mapParams = new HashMap<>();
        for (int i = 0; i < method.getParams().size(); i++) {
            if (program.getStack().isEmpty()) break;
            Variable value = program.getStack().pop();
            mapParams.put(r.get(i), value);
        }

        vars.putAll(mapParams);
        vars.putAll(methodAux.getVars());
        vars.put("self", new Variable(obj));

        method.setVars(new HashMap<>(vars));

        program.addMethods(program.getCurrentMethod());
        program.setCurrentMethod(method);

    }

}
