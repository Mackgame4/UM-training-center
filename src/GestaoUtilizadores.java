import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import Classes.FileManager;
import Classes.Terminal;
import Classes.Utilizador;

public class GestaoUtilizadores {
    private Map<Integer, Utilizador> utilizadores;
    private int uidCounter;

    public GestaoUtilizadores() {
        this.utilizadores = new HashMap<Integer,Utilizador>();
        this.uidCounter = FileManager.getNumberOfLines(FileManager.usersFile) + 1;
    }

    public void loadUtilizadores() {
        String readUsers = FileManager.readFromFile(FileManager.usersFile);
        List<String> users = new ArrayList<>();
        if (!readUsers.isEmpty()) {
            users = new ArrayList<>(List.of(readUsers.split("\n")));
        }
        for (String user : users) {
            String[] userParts = user.split(";");
            Utilizador utilizador = new Utilizador();
            utilizador.setUid(Integer.parseInt(userParts[0]));
            utilizador.setNome(userParts[1]);
            utilizador.setEmail(userParts[2]);
            utilizador.setPassword(userParts[3]);
            utilizador.setMorada(userParts[4]);
            utilizador.setTipo(userParts[5].equals("PRATICANTE_OCASIONAL") ? Utilizador.TipoUtilizador.PRATICANTE_OCASIONAL : userParts[5].equals("AMADOR") ? Utilizador.TipoUtilizador.AMADOR : Utilizador.TipoUtilizador.PROFISSIONAL);
            utilizador.setPermissionLevel(userParts[6].equals("USER") ? Utilizador.PermissionLevel.USER : Utilizador.PermissionLevel.ADMIN);
            utilizador.setFreqCardiacaMedia(Double.parseDouble(userParts[7]));
            this.utilizadores.put(utilizador.getUid(), utilizador);
        }
    }

    public boolean doesUserExist(String email, String password) {
        /*for (Utilizador utilizador : this.utilizadores.values()) {
            if (utilizador.getEmail().equals(email) && utilizador.getPassword().equals(password)) {
                return true;
            }
        }
        return false;*/
        // using streams
        return this.utilizadores.values().stream().anyMatch(utilizador -> utilizador.getEmail().equals(email) && utilizador.getPassword().equals(password));
    }

    public void saveUtilizadores() {
        String users = "";
        for (Utilizador utilizador : this.utilizadores.values()) {
            users += utilizador.getUid() + ";" + utilizador.getNome() + ";" + utilizador.getEmail() + ";" + utilizador.getPassword() + ";" + utilizador.getMorada() + ";" + utilizador.getTipo() + ";" + utilizador.getPermissionLevel() + ";" + utilizador.getFreqCardiacaMedia() + "\n";
        }
        //FileManager.writeToFile(FileManager.usersFile, users);
        // Write without appending because we always load the users before saving them (so we would be writing the same users twice)
        FileManager.writeToFileWithoutAppending(FileManager.usersFile, users);
    }

    public void createUtilizador(Scanner scanner) {
        Utilizador utilizador = new Utilizador();
        System.out.print(Terminal.ANSI_YELLOW + "Enter your email: " + Terminal.ANSI_RESET);
        String email = scanner.nextLine();
        System.out.print(Terminal.ANSI_YELLOW + "Enter your password: " + Terminal.ANSI_RESET);
        String password = scanner.nextLine();
        System.out.print(Terminal.ANSI_CLEAR); // Clear
        if (doesUserExist(email, password)) {
            System.out.println(Terminal.ANSI_RED + "User already exists." + Terminal.ANSI_RESET);
            return;
        } else {
            utilizador.setEmail(email);
            utilizador.setPassword(password);
            System.out.println(Terminal.ANSI_GREEN + "Account created successfully, please enter the following information:" + Terminal.ANSI_RESET);
            System.out.print(Terminal.ANSI_YELLOW + "Enter your name: " + Terminal.ANSI_RESET);
            utilizador.setNome(scanner.nextLine());
            System.out.print(Terminal.ANSI_YELLOW + "Enter your address: " + Terminal.ANSI_RESET);
            utilizador.setMorada(scanner.nextLine());
            System.out.print(Terminal.ANSI_YELLOW + "Enter your activity level (0 - Ocasional, 1 - Amador, 2 - Profissional): " + Terminal.ANSI_RESET);
            utilizador.setTipo(Utilizador.TipoUtilizador.values()[scanner.nextInt()]);
            scanner.nextLine();
            System.out.print(Terminal.ANSI_YELLOW + "Enter your permission level (0 - User, 1 - Admin): " + Terminal.ANSI_RESET);
            utilizador.setPermissionLevel(Utilizador.PermissionLevel.values()[scanner.nextInt()]);
            scanner.nextLine();
            System.out.print(Terminal.ANSI_YELLOW + "Enter your average heart rate: " + Terminal.ANSI_RESET);
            utilizador.setFreqCardiacaMedia(scanner.nextDouble());
            scanner.nextLine();
            addUtilizador(utilizador);
            System.out.print(Terminal.ANSI_CLEAR); // Clear terminal
            System.out.println(Terminal.ANSI_GREEN + "User registered successfully!" + Terminal.ANSI_RESET);
        }
    }

    public void addUtilizador(Utilizador utilizador) {
        utilizador.setUid(uidCounter);
        this.utilizadores.put(utilizador.getUid(), utilizador);
        uidCounter++;
    }

    public void removeUtilizador(Utilizador utilizador) {
        this.utilizadores.remove(utilizador.getUid());
    }

    public boolean removeUtilizadorByUid(int uid) {
        Utilizador utilizador = getUtilizadorByUid(uid);
        if (utilizador != null) {
                removeUtilizador(utilizador);
            return true;
        }
        return false;
    }

    public void updateUtilizador(Utilizador utilizador) {
        this.utilizadores.put(utilizador.getUid(), utilizador);
    }

    public Utilizador getUtilizadorByUid(int uid) {
        Utilizador utilizador = this.utilizadores.get(uid);
        if (utilizador != null) {
            return utilizador;
        }
        return null;
    }

    public Utilizador getUtilizadorByEmail(String email) {
        for (Utilizador utilizador : this.utilizadores.values()) {
            if (utilizador.getEmail().equals(email)) {
                return utilizador;
            }
        }
        return null;
    }

    public Map<Integer, Utilizador> getUtilizadores() {
        Map<Integer, Utilizador> utilizadoresMap = new HashMap<>();
        for (Utilizador utilizador : this.utilizadores.values()) {
            utilizadoresMap.put(utilizador.getUid(), utilizador);
        }
        return utilizadoresMap;
    }

    public List<Utilizador> getUtilizadoresList() {
        return new ArrayList<>(this.utilizadores.values());
    }
}
