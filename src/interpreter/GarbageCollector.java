package interpreter;

public class GarbageCollector {
    private Program program;
    private String color;
    private int Pc;

    public GarbageCollector(Program program) {
        this.program = program;
        this.color = "red";
        this.Pc = 0;
    }

    public void CollectionCheck() {
        Pc++;
        if (Pc >= 5) {
            Collect();
            Pc = 0;
        }
    }

    private void Collect() {
        Mark();

        program.getMemory().removeIf(var -> shouldBeCollected(var)); // Replace with your condition


        // Alterna a cor para a próxima coleta
        color = color.equals("red") ? "black" : "red";
    }

    private void Mark() {

        for (Variable aux : program.getStack()) {
            if (aux != null) {
                MarkVariable(aux);
            }
        }

        //variáveis do metodo atual?? todos metodos???
        for (var entry : program.getCurrentMethod().getVars().entrySet()) {
            Variable aux = entry.getValue();
            if (aux != null) {
                MarkVariable(aux);
            }
        }

        for (interpreter.Class classe : program.getClasses())
            for (var entry : classe.getVars().entrySet()) {
                Variable aux = entry.getValue();
                if (aux != null) {
                    MarkVariable(aux);
                }
            }

    }

    private void MarkVariable(Variable var) {
        if (var.getClasse() != null && var.getClasse().getVars() != null) {
            for (var entry : var.getClasse().getVars().entrySet()) {
                Variable aux = entry.getValue();
                if (aux != null) {
                    MarkVariable(aux);
                }
            }
        }

        if (!var.getColor().equals(color)) {
            var.setColor(color);
        }
    }

    private boolean shouldBeCollected(Variable var) {
        return !var.getColor().equals(color);  // Returns true if `var` is unmarked
    }

}


