package Classes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class FileManager {
    // write the received text to the file
    private static String filesPath = "src/data/";

    public static final String usersFile = "users.csv";
    public static final String activitiesFile = "atividades.csv";
    public static final String trainingPlansFile = "planos_treino.csv";

    public static boolean writeToFile(String fileName, String text) {
        try {
            // if file does not exist, create it
            File file = new File(filesPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                // if file exists, append the text
                FileWriter fileWriter = new FileWriter(filesPath + fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(text);
                printWriter.close();
            }
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    // write without appending (replace the content of the file)
    public static boolean writeToFileWithoutAppending(String fileName, String text) {
        try {
            // if file does not exist, create it
            File file = new File(filesPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // if file exists, write the text
            FileWriter fileWriter = new FileWriter(filesPath + fileName, false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(text);
            printWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    // read the content of the file
    public static String readFromFile(String fileName) {
        String content = "";
        try {
            File file = new File(filesPath + fileName);
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                while (scanner.hasNextLine()) {
                    content += scanner.nextLine() + "\n";
                }
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }

    // get number of lines in the file (excluding empty lines)
    public static int getNumberOfLines(String fileName) {
        int lines = 0;
        try {
            File file = new File(filesPath + fileName);
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.isEmpty()) {
                        lines++;
                    }
                }
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }
}
