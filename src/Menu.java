import java.util.Scanner;
import java.util.List;
import Classes.Terminal;
import Classes.Utilizador;
import Classes.Clock;
import Atividades.Corrida;
import Atividades.LevantamentoPesos;
import Atividades.Remo;
import Atividades.Abdominais;
import Atividades.Elevacoes;

public class Menu {
    private static Clock clock = new Clock();

    public static void showMenu(Utilizador user) {
        // Start System Clock
        System.out.print(Terminal.ANSI_CLEAR); // Clear
        // Show title
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "Welcome to " + Terminal.ANSI_BLUE + "CENTRO FIT" + Terminal.ANSI_RED + " main menu" + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        // Ask for an option
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Gerir Minhas Atividades" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Gerir Meus Planos de Treino" + Terminal.ANSI_RESET);
        System.out.println("3. " + Terminal.ANSI_YELLOW + "Estatísticas" + Terminal.ANSI_RESET);
        if (user.getPermissionLevel() == Utilizador.PermissionLevel.ADMIN) {
            System.out.println("4. " + Terminal.ANSI_YELLOW + "Gerir Utilizadores" + Terminal.ANSI_RESET);
        }
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Sair" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        System.out.print(Terminal.ANSI_CLEAR); // Clear
        // Check the option
        if (option == 0) {
            System.exit(0);
        } else if (option == 1) {
            // Atividades
            gerirAtividades(user);
        } else if (option == 2) {
            // Planos de Treino
            gerirPlanosDeTreino(user);
        } else if (option == 3) {
            // Estatísticas
            showEstatisticas(user);
        } else if (option == 4 && user.getPermissionLevel() == Utilizador.PermissionLevel.ADMIN) {
            // Gerir Utilizadores
            gerirUtilizadores(user);
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option" + Terminal.ANSI_RESET);
        }
        // Close the scanner
        scanner.close();
    }

    private static void gerirUtilizadores(Utilizador user) {
        GestaoUtilizadores gestaoUtilizadores = new GestaoUtilizadores();
        gestaoUtilizadores.loadUtilizadores();
        // Show title
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "Welcome to " + Terminal.ANSI_BLUE + "CENTRO FIT" + Terminal.ANSI_RED + " users management" + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        // Ask for an option
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Listar Utilizadores" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Adicionar Utilizador" + Terminal.ANSI_RESET);
        System.out.println("3. " + Terminal.ANSI_YELLOW + "Remover Utilizador" + Terminal.ANSI_RESET);
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Voltar" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        // Check the option
        if (option == 0) {
            showMenu(user);
        } else if (option == 1) {
            // Listar Utilizadores
            System.out.print(Terminal.ANSI_CLEAR); // Clear
            List<Utilizador> utilizadores = gestaoUtilizadores.getUtilizadoresList();
            if (utilizadores.isEmpty()) {
                System.out.println(Terminal.ANSI_RED + "No users found" + Terminal.ANSI_RESET);
            } else {
                System.out.println(Terminal.ANSI_YELLOW + "Users list:" + Terminal.ANSI_RESET);
                for (Utilizador utilizador : utilizadores) {
                    System.out.println(utilizador.displayUser());
                }
            }
        } else if (option == 2) {
            // Adicionar Utilizador
            gestaoUtilizadores.createUtilizador(scanner);
            gestaoUtilizadores.saveUtilizadores();
        } else if (option == 3) {
            // Remover Utilizador
            System.out.print(Terminal.ANSI_CLEAR); // Clear
            System.out.print(Terminal.ANSI_YELLOW + "Enter the user's uid: " + Terminal.ANSI_RESET);
            String uid = scanner.nextLine();
            // get user by email
            if (gestaoUtilizadores.removeUtilizadorByUid(Integer.parseInt(uid))) {
                System.out.println(Terminal.ANSI_GREEN + "User removed successfully." + Terminal.ANSI_RESET);
            } else {
                System.out.println(Terminal.ANSI_RED + "User not found." + Terminal.ANSI_RESET);
            }
            gestaoUtilizadores.saveUtilizadores();
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option." + Terminal.ANSI_RESET);
        }
        waitForEnter(scanner, user);
        // Close the scanner
        scanner.close();
    }

    private static void gerirAtividades(Utilizador user) {
        GestaoAtividades gestaoAtividades = new GestaoAtividades();
        gestaoAtividades.loadAtividades();
        // Show title
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "Welcome to " + Terminal.ANSI_BLUE + "CENTRO FIT" + Terminal.ANSI_RED + " activities management" + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        // Ask for an option
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Listar Atividades" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Adicionar Atividade" + Terminal.ANSI_RESET);
        System.out.println("3. " + Terminal.ANSI_YELLOW + "Avançar Tempo" + Terminal.ANSI_RESET);
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Voltar" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        // Check the option
        if (option == 0) {
            showMenu(user);
        } else if (option == 1) {
            // Listar Atividades
            System.out.print(Terminal.ANSI_CLEAR); // Clear
            //List<Atividade> atividades = gestaoAtividades.getAtividadesList(); // todas as atividades
            /*List<Atividade> atividades = gestaoAtividades.getUserActivitiesList(user.getUid());
            if (atividades.isEmpty()) {
                System.out.println(Terminal.ANSI_RED + "No activities found." + Terminal.ANSI_RESET);
            } else {
                System.out.println(Terminal.ANSI_YELLOW + "Activities list:" + Terminal.ANSI_RESET);
                for (Atividade atividade : atividades) {
                    System.out.println(atividade.toString());
                }
            }*/
            gestaoAtividades.mostrarAtividadesByUid(user.getUid(), clock.getSystemDataHora());
        } else if (option == 2) {
            // Adicionar Atividade
            adicionarAtividade(scanner, user, gestaoAtividades);
        } else if (option == 3) {
            // Avancar Tempo
            clock.avancarTempo(scanner, user);
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option" + Terminal.ANSI_RESET);
        }
        waitForEnter(scanner, user);
        // Close the scanner
        scanner.close();
    }

    private static void adicionarAtividade(Scanner scanner, Utilizador user, GestaoAtividades gestaoAtividades) {
        System.out.print(Terminal.ANSI_CLEAR); // Clear
        System.out.println(Terminal.ANSI_YELLOW + "Choose an activity type:" + Terminal.ANSI_RESET);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Corrida" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Levantamento de Pesos" + Terminal.ANSI_RESET);
        System.out.println("3. " + Terminal.ANSI_YELLOW + "Remo" + Terminal.ANSI_RESET);
        System.out.println("4. " + Terminal.ANSI_YELLOW + "Abdominais" + Terminal.ANSI_RESET);
        System.out.println("5. " + Terminal.ANSI_YELLOW + "Elevações" + Terminal.ANSI_RESET);
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Voltar" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        // Ask for the activity
        System.out.print(Terminal.ANSI_YELLOW + "Insira a duração da atividade (em horas): " + Terminal.ANSI_RESET);
        double duracao = scanner.nextDouble();
        scanner.nextLine();
        if (duracao <= 0) {
            System.out.println(Terminal.ANSI_RED + "Invalid duration." + Terminal.ANSI_RESET);
            return;
        }
        // Ask for the activity details
        if (option == 0) {
            return;
        } else if (option == 1) {
            // Corrida
            Corrida corrida = gestaoAtividades.createCorrida(scanner, user, 0, duracao);
            gestaoAtividades.registaAtividade(corrida);
        } else if (option == 2) {
            // Levantamento de Pesos
            LevantamentoPesos levantamentoPesos = gestaoAtividades.createLevantamentoPesos(scanner, user, 0, duracao);
            gestaoAtividades.registaAtividade(levantamentoPesos);
        } else if (option == 3) {
            // Remo
            Remo remo = gestaoAtividades.createRemo(scanner, user, 0, duracao);
            gestaoAtividades.registaAtividade(remo);
        } else if (option == 4) {
            // Abdominais
            Abdominais abdominais = gestaoAtividades.createAbdominais(scanner, user, 0, duracao);
            gestaoAtividades.registaAtividade(abdominais);
        } else if (option == 5) {
            // Elevações
            Elevacoes elevacoes = gestaoAtividades.createElevacoes(scanner, user, 0, duracao);
            gestaoAtividades.registaAtividade(elevacoes);
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option." + Terminal.ANSI_RESET);
        }
    }

    private static void gerirPlanosDeTreino(Utilizador user) {
        GestaoAtividades gestaoAtividades = new GestaoAtividades();
        gestaoAtividades.loadAtividades();
        GestaoPlanoTreino gestaoPlanoTreino = new GestaoPlanoTreino();
        gestaoPlanoTreino.loadPlanosTreino(gestaoAtividades);
        // Show title
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "Welcome to " + Terminal.ANSI_BLUE + "CENTRO FIT" + Terminal.ANSI_RED + " training plans management" + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        // Ask for an option
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Listar Planos de Treino" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Adicionar Plano de Treino" + Terminal.ANSI_RESET);
        System.out.println("3. " + Terminal.ANSI_YELLOW + "Remover Plano de Treino" + Terminal.ANSI_RESET);
        System.out.println("4. " + Terminal.ANSI_YELLOW + "Criar Plano de Treino Definido" + Terminal.ANSI_RESET);
        System.out.println("5. " + Terminal.ANSI_YELLOW + "Criar Plano de Treino Personalizado" + Terminal.ANSI_RESET);
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Voltar" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        // Check the option
        if (option == 0) {
            showMenu(user);
        } else if (option == 1) {
            // Listar Planos de Treino
            gestaoPlanoTreino.mostrarPlanosDeTreinoByUid(user.getUid());
        } else if (option == 2) {
            // Adicionar Plano de Treino
            gestaoPlanoTreino.createPlanoTreino(scanner, user, gestaoAtividades);
            gestaoAtividades.saveAtividades();
            gestaoPlanoTreino.savePlanosTreino();
        } else if (option == 3) {
            // Remover Plano de Treino
            System.out.print(Terminal.ANSI_CLEAR); // Clear
            System.out.print(Terminal.ANSI_YELLOW + "Enter the plan's id: " + Terminal.ANSI_RESET);
            String planoId = scanner.nextLine();
            gestaoPlanoTreino.removePlanoTreinoByPlanoId(Integer.parseInt(planoId), gestaoAtividades);
            gestaoPlanoTreino.savePlanosTreino();
        } else if (option == 4) {
            gestaoPlanoTreino.criarPlanoDefinido(scanner, user, gestaoAtividades);
        } else if (option == 5) {
            gestaoPlanoTreino.criarPlanoPersonalizado(scanner, user, gestaoAtividades);
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option" + Terminal.ANSI_RESET);
        }
        waitForEnter(scanner, user);
        // Close the scanner
        scanner.close();
    }

    private static void showEstatisticas(Utilizador user) {
        GestaoAtividades gestaoAtividades = new GestaoAtividades();
        gestaoAtividades.loadAtividades();
        GestaoUtilizadores gestaoUtilizadores = new GestaoUtilizadores();
        gestaoUtilizadores.loadUtilizadores();
        GestaoPlanoTreino gestaoPlanoTreino = new GestaoPlanoTreino();
        gestaoPlanoTreino.loadPlanosTreino(gestaoAtividades);
        GestaoEstatisticas gestaoEstatisticas = new GestaoEstatisticas(gestaoUtilizadores, gestaoAtividades, gestaoPlanoTreino);
        // Show title
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "Welcome to " + Terminal.ANSI_BLUE + "CENTRO FIT" + Terminal.ANSI_RED + " statistics" + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        // Ask for an option
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Utilizador que mais calorias queimou num período" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Utilizador que mais calorias queimou desde sempre" + Terminal.ANSI_RESET);
        System.out.println("3. " + Terminal.ANSI_YELLOW + "Utilizador que mais atividades realizou num período" + Terminal.ANSI_RESET);
        System.out.println("4. " + Terminal.ANSI_YELLOW + "Utilizador que mais atividades realizou desde sempre" + Terminal.ANSI_RESET);
        System.out.println("5. " + Terminal.ANSI_YELLOW + "Tipo de atividade mais realizada" + Terminal.ANSI_RESET);
        System.out.println("6. " + Terminal.ANSI_YELLOW + "Kms percorridos por um utilizador num período" + Terminal.ANSI_RESET);
        System.out.println("7. " + Terminal.ANSI_YELLOW + "Kms percorridos por um utilizador desde sempre" + Terminal.ANSI_RESET);
        System.out.println("8. " + Terminal.ANSI_YELLOW + "Metros de altimetria totalizados por um utilizador num período" + Terminal.ANSI_RESET);
        System.out.println("9. " + Terminal.ANSI_YELLOW + "Metros de altimetria totalizados por um utilizador desde sempre" + Terminal.ANSI_RESET);
        System.out.println("10. " + Terminal.ANSI_YELLOW + "Plano de treino mais exigente" + Terminal.ANSI_RESET);
        System.out.println("11. " + Terminal.ANSI_YELLOW + "Atividades realizadas por um utilizador" + Terminal.ANSI_RESET);
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Voltar" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        // Check the option
        if (option == 0) {
            showMenu(user);

        } else if (option == 1) {
        System.out.print("Insira a data de início do período (dd-MM-yyyy HH:mm): ");
        String dataInicioStr = scanner.nextLine();
        System.out.print("Insira a data de fim do período (dd-MM-yyyy HH:mm): ");
        String dataFimStr = scanner.nextLine();
            
        gestaoEstatisticas.utilizadorMaiorGastoPeriodo(dataInicioStr, dataFimStr);
        
        } else if (option == 2) {
            gestaoEstatisticas.utilizadorMaiorGasto();

        } else if (option == 3) {
            System.out.print("Insira a data de início do período (dd-MM-yyyy HH:mm): ");
            String dataInicioStr2 = scanner.nextLine();
            System.out.print("Insira a data de fim do período (dd-MM-yyyy HH:mm): ");
            String dataFimStr2 = scanner.nextLine();

            gestaoEstatisticas.utilizadorMaisAtivoPeriodo(dataInicioStr2, dataFimStr2);

        } else if (option == 4) {
            gestaoEstatisticas.utilizadorMaisAtivo();

        } else if (option == 5) {
            gestaoEstatisticas.tipoAtividadeMaisRealizada();

        } else if (option == 6) {
            System.out.print("Insira o código do utilizador: ");
            int codigoUtilizador6 = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Insira a data de início do período (dd-MM-yyyy HH:mm): ");
            String dataInicioStr6 = scanner.nextLine();
            System.out.print("Insira a data de fim do período (dd-MM-yyyy HH:mm): ");
            String dataFimStr6 = scanner.nextLine();

            gestaoEstatisticas.kmsUtilizadorPeriodo(dataInicioStr6, dataFimStr6, codigoUtilizador6);

        } else if (option == 7) {
            System.out.print("Insira o código do utilizador: ");
            int codigoUtilizador7 = scanner.nextInt();
            scanner.nextLine();

            gestaoEstatisticas.kmsUtilizador(codigoUtilizador7);

        } else if (option == 8) {
            System.out.print("Insira o código do utilizador: ");
            int codigoUtilizador8 = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Insira a data de início do período (dd-MM-yyyy HH:mm): ");
            String dataInicioStr8 = scanner.nextLine();
            System.out.print("Insira a data de fim do período (dd-MM-yyyy HH:mm): ");
            String dataFimStr8 = scanner.nextLine();

            gestaoEstatisticas.metrosUtilizadorPeriodo(dataInicioStr8, dataFimStr8, codigoUtilizador8);

        } else if (option == 9) {
            System.out.print("Insira o código do utilizador: ");
            int codigoUtilizador9 = scanner.nextInt();
            scanner.nextLine();

            gestaoEstatisticas.metrosUtilizador(codigoUtilizador9);

        } else if (option == 10) {
            gestaoEstatisticas.planoMaiorGasto();

        } else if (option == 11) {
            System.out.print("Insira o código do utilizador: ");
            int codigoUtilizador11 = scanner.nextInt();
            scanner.nextLine();

            gestaoEstatisticas.atividadesUtilizador(codigoUtilizador11);

        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option" + Terminal.ANSI_RESET);
        }
        waitForEnter(scanner, user);
        // Close the scanner
        scanner.close();
    }

    // wait for Enter key to go back to menu
    public static void waitForEnter(Scanner scanner, Utilizador user) {
        System.out.println(Terminal.ANSI_YELLOW + "Press Enter to go back to menu" + Terminal.ANSI_RESET);
        scanner.nextLine();
        //scanner.close();
        showMenu(user);
    }
}