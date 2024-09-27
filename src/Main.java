import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        run("src/main.bool");
    }

    public static void run(String name) throws IOException {
        String newName = createFile(name);

        var sc = new Scanner(new File(name));
        FileWriter fw = new FileWriter(newName);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            String data = Test.test(line);

            fw.append(data);
            fw.append("\n");

        }

        fw.close();
        sc.close();
    }

    public static String createFile(String name) {
        String newName = name + "c";

        try {

            var newFile = new File(newName);

            if (newFile.createNewFile()) {
                System.out.println("arguivo criado: " + newFile.getName());
            } else {
                System.out.println("arquivo ja existe");
            }

        } catch (IOException e) {
            System.out.println("erro");
            e.printStackTrace();
        }

        return newName;
    }


}

