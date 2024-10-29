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
        // Fase de marcação
        //marcar();

        // Fase de coleta
        //coletarNaoMarcados();

        // Alterna a cor para a próxima coleta
        color = color.equals("red") ? "black" : "red";
    }

    private void Mark () {
        for (var key : program.getCurrentMethod().getVars().keySet()) {
            System.out.println(key + ": " + program.getCurrentMethod().getVars().get(key).getValue());
           // if
        }
    }


}


