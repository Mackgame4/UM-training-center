import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Atividades.Abdominais;
import Atividades.Corrida;
import Atividades.LevantamentoPesos;
import Atividades.Remo;
import Classes.PlanoTreino;
import Classes.FileManager;
import Classes.Terminal;
import Classes.Utilizador;
import Classes.Atividade;

public class GestaoPlanoTreino {
    private List<PlanoTreino> planosDeTreino;

    public GestaoPlanoTreino() {
        this.planosDeTreino = new ArrayList<>();
    }

    public void registaPlanoTreino(PlanoTreino planoTreino) {
        this.planosDeTreino.add(planoTreino);
        savePlanosTreino();
    }

    public void adicionarPlanoTreino(PlanoTreino planoTreino) {
        this.planosDeTreino.add(planoTreino);
    }

    public void savePlanosTreino() {
        String planosTreino = "";
        for (PlanoTreino plano : planosDeTreino) {
            planosTreino += plano.getCodigo() + ";" + plano.getRelatedUserId() + ";" + plano.getDataInicio() + ";" + plano.getNomePlano() + ";" + plano.getVezesPorSemana() + "\n";
        }
        FileManager.writeToFileWithoutAppending(FileManager.trainingPlansFile, planosTreino);
    }

    public void loadPlanosTreino(GestaoAtividades gestaoAtividades) {
        String readPlanos = FileManager.readFromFile(FileManager.trainingPlansFile);
        List<String> planos = new ArrayList<>();
        if (!readPlanos.isEmpty()) {
            planos = new ArrayList<>(List.of(readPlanos.split("\n")));
        }
        for (String plano : planos) {
            String[] planoData = plano.split(";");
            int codigo = Integer.parseInt(planoData[0]);
            int codigoUtilizador = Integer.parseInt(planoData[1]);
            LocalDateTime dataInicio = LocalDateTime.parse(planoData[2]);
            String nomePlano = planoData[3];
            int vezesPorSemana = Integer.parseInt(planoData[4]);

            if (codigoUtilizador >= 0) {
                PlanoTreino planoTreino = new PlanoTreino(codigo, codigoUtilizador, dataInicio, nomePlano, vezesPorSemana);
                planoTreino.setAtividades(gestaoAtividades.getPlanoActivitiesList(planoTreino.getCodigo()));
                planosDeTreino.add(planoTreino);
            }
        }
    }

    public void removePlanoTreinoByPlanoId(int planoId, GestaoAtividades gestaoAtividades) {
        PlanoTreino planoToRemove = null;
        for (PlanoTreino plano : planosDeTreino) {
            if (plano.getCodigo() == planoId) {
                planoToRemove = plano;
                break;
            }
        }
        if (planoToRemove != null) {
            planosDeTreino.remove(planoToRemove);
            // also remove the activities related to this plan
            gestaoAtividades.removeAtividadesByPlanoId(planoId);
            System.out.println("Plano de treino removido com sucesso.");
        } else {
            System.out.println("Plano de treino não encontrado.");
        }
    }

    public void mostrarPlanosTreino() {
        if (planosDeTreino.isEmpty()) {
            System.out.println("Não existem planos de treino.");
        } else {
            for (PlanoTreino plano : planosDeTreino) {
                System.out.println(plano);
            }
        }
    }

    public void createPlanoTreino(Scanner scanner, Utilizador utilizador, GestaoAtividades gestaoAtividades) {
        /*System.out.print(Terminal.ANSI_YELLOW + "Insira o código do utilizador para o plano de treino: ");
        int codigoUtilizador = scanner.nextInt();
        scanner.nextLine();
    
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado.");
            return;
        }*/
    
        System.out.print(Terminal.ANSI_YELLOW + "Insira o nome do plano de treino: " + Terminal.ANSI_RESET);
        String nomePlano = scanner.nextLine();
    
        System.out.print(Terminal.ANSI_YELLOW + "Quantas vezes pretende realizar o plano por semana? " + Terminal.ANSI_RESET);
        int vezesPorSemana = scanner.nextInt();

        if (vezesPorSemana < 1) {
            System.out.println("O plano de treino deve ser realizado pelo menos uma vez por semana.");
            return;
        }
    
        int codigo = planosDeTreino.size() + 1;
        PlanoTreino novoPlano = new PlanoTreino(codigo, utilizador.getUid(), LocalDateTime.now(), nomePlano, vezesPorSemana);
    
        boolean adicionaAtividades = true;
        while (adicionaAtividades) {
            System.out.println(Terminal.ANSI_YELLOW + "Selecione a atividade que pretende adicionar ao plano de treino:" + Terminal.ANSI_RESET);
            System.out.println("1. " + Terminal.ANSI_YELLOW + "Corrida" + Terminal.ANSI_RESET);
            System.out.println("2. " + Terminal.ANSI_YELLOW + "Levantamento de Pesos" + Terminal.ANSI_RESET);
            System.out.println("3. " + Terminal.ANSI_YELLOW + "Remo" + Terminal.ANSI_RESET);
            System.out.println("4. " + Terminal.ANSI_YELLOW + "Abdominais" + Terminal.ANSI_RESET);
            System.out.println("0. " + Terminal.ANSI_YELLOW + "Finalizar adição de atividades" + Terminal.ANSI_RESET);
            System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
            int opcaoAtividade = scanner.nextInt();

            int duracaoAtividade = 0;
            if (opcaoAtividade != 0) {
                System.out.print(Terminal.ANSI_YELLOW + "Insira a duração da atividade (em horas): " + Terminal.ANSI_RESET);
                duracaoAtividade = scanner.nextInt();
            }

            switch (opcaoAtividade) {
                case 1: // Corrida
                    Corrida corrida = gestaoAtividades.createCorrida(scanner, utilizador, novoPlano.getCodigo(), duracaoAtividade);
                    novoPlano.adicionarAtividade(corrida);
                    // we also add the activity to the user's activities and save later
                    gestaoAtividades.addAtividade(corrida);
                    break;
                case 2: // Levantamento de Pesos
                    LevantamentoPesos levantamentoPesos = gestaoAtividades.createLevantamentoPesos(scanner, utilizador, novoPlano.getCodigo(), duracaoAtividade);
                    novoPlano.adicionarAtividade(levantamentoPesos);
                    gestaoAtividades.addAtividade(levantamentoPesos);
                    break;
                case 3: // Remo
                    Remo remo = gestaoAtividades.createRemo(scanner, utilizador, novoPlano.getCodigo(), duracaoAtividade);
                    novoPlano.adicionarAtividade(remo);
                    gestaoAtividades.addAtividade(remo);
                    break;
                case 4: // Abdominais
                    Abdominais abdominais = gestaoAtividades.createAbdominais(scanner, utilizador, novoPlano.getCodigo(), duracaoAtividade);
                    novoPlano.adicionarAtividade(abdominais);
                    gestaoAtividades.addAtividade(abdominais);
                    break;
                case 0:
                    adicionaAtividades = false;
                    break;
                default:
                    System.out.println("Opção inválida!\n");
            }
        }

        adicionarPlanoTreino(novoPlano);
        System.out.println("Plano de treino criado com sucesso!\n" + novoPlano + '\n');
    }
    
    public void criarPlanoDefinido(Scanner scanner, Utilizador user, GestaoAtividades gestaoAtividades) {
        System.out.println("Selecione o tipo de plano de treino que pretende:");
        System.out.println("1. Cardio");
        System.out.println("2. Força");
        System.out.print("Escolha uma opção: ");
        int opcaoTipoPlano = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (opcaoTipoPlano) {
            case 1:
                criarPlanoCardio(scanner, user, gestaoAtividades);
                break;
            case 2:
                criarPlanoForca(scanner, user, gestaoAtividades);
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    public void criarPlanoCardio(Scanner scanner, Utilizador utilizador, GestaoAtividades gestaoAtividades) {
        int codigoUtilizador = utilizador.getUid();
    
        System.out.print("Quantas vezes por semana deseja treinar? ");
        int vezesPorSemana = scanner.nextInt();
    
        LocalDateTime dataInicio = LocalDateTime.now(); // usamos a data atual como data de início
    
        PlanoTreino planoCardio = new PlanoTreino(planosDeTreino.size() + 1, codigoUtilizador, dataInicio, "Cardio", vezesPorSemana);
        int planoId = planoCardio.getCodigo();
        
        Atividade corrida = new Corrida(planoId, 1.4, LocalDateTime.now(), codigoUtilizador, 21.5, 2.0, 0.3);
        planoCardio.adicionarAtividade(corrida);
        gestaoAtividades.addAtividade(corrida);
    
        Atividade remo = new Remo(planoId, 2.0, LocalDateTime.now(), codigoUtilizador, 43.0, 3.0);
        planoCardio.adicionarAtividade(remo);
        gestaoAtividades.addAtividade(remo);
    
        adicionarPlanoTreino(planoCardio);
        gestaoAtividades.saveAtividades();
        savePlanosTreino();
    
        System.out.println("Plano de treino de Cardio criado com sucesso:\n" + planoCardio);
    }

    public void criarPlanoForca(Scanner scanner, Utilizador utilizador, GestaoAtividades gestaoAtividades) {
        int codigoUtilizador = utilizador.getUid();
    
        System.out.print("Quantas vezes por semana deseja treinar? ");
        int vezesPorSemana = scanner.nextInt();
    
        LocalDateTime dataInicio = LocalDateTime.now(); // Use a data atual como data de início
    
        PlanoTreino planoForca = new PlanoTreino(planosDeTreino.size() + 1, codigoUtilizador, dataInicio, "Força", vezesPorSemana);
        int planoId = planoForca.getCodigo();
        
        Atividade levantamentoPesos = new LevantamentoPesos(planoId, 2.0, LocalDateTime.now(), codigoUtilizador, 20.0, 3, 30.0);
        planoForca.adicionarAtividade(levantamentoPesos);
        gestaoAtividades.addAtividade(levantamentoPesos);
    
        Atividade abdominais = new Abdominais(planoId, 1.5, LocalDateTime.now(), codigoUtilizador, 13.0, 2);
        planoForca.adicionarAtividade(abdominais);
        gestaoAtividades.addAtividade(abdominais);
    
        adicionarPlanoTreino(planoForca);
        gestaoAtividades.saveAtividades();
        savePlanosTreino();
    
        System.out.println("Plano de treino de Força criado com sucesso:\n" + planoForca);
    }

    // cria um plano de treino de acordo com os objetivos do utilizador
    public void criarPlanoPersonalizado(Scanner scanner, Utilizador utilizador, GestaoAtividades gestaoAtividades) {
        int codigoUtilizador = utilizador.getUid();

        System.out.print("Quantas vezes por semana deseja treinar? ");
        int vezesPorSemana = scanner.nextInt();

        LocalDateTime dataInicio = LocalDateTime.now(); 

        PlanoTreino planoPersonalizado = new PlanoTreino(planosDeTreino.size() + 1, codigoUtilizador, dataInicio, "Personalizado", vezesPorSemana);
        int planoId = planoPersonalizado.getCodigo();
        
        System.out.println("Quantas calorias pretende queimar por sessão de treino? ");
        double calorias = scanner.nextDouble();

        System.out.println("Quantos minutos de atividade cardiovascular pretende fazer por sessão de treino? ");
        double duracaoCardio = scanner.nextDouble();

        System.out.println("Quantos minutos de atividade de força pretende fazer por sessão de treino? ");
        double duracaoForca = scanner.nextDouble();      

        // Estimar distância com base nas calorias e duração
        double caloriasPorKm = 100; // calorias queimadas por km
        double distanciaCorrida = calorias / caloriasPorKm;   

        Atividade corrida = new Corrida(planoId, duracaoCardio, LocalDateTime.now(), codigoUtilizador, calorias, distanciaCorrida, 0.3);

        planoPersonalizado.adicionarAtividade(corrida);
        gestaoAtividades.addAtividade(corrida);

        // Estimar peso e repetições
        double caloriasPorRepeticao = 0.3; // Calorias queimadas por repetição por kg levantado
        double pesoPesos = 10; // um peso de 10kg
        int repeticoesNecessarias = (int) (calorias / (caloriasPorRepeticao * pesoPesos));

        Atividade levantamentoPesos = new LevantamentoPesos(planoId, duracaoForca, LocalDateTime.now(), codigoUtilizador, calorias, repeticoesNecessarias, pesoPesos);

        planoPersonalizado.adicionarAtividade(levantamentoPesos);
        gestaoAtividades.addAtividade(levantamentoPesos);

        adicionarPlanoTreino(planoPersonalizado);
        gestaoAtividades.saveAtividades();
        savePlanosTreino();

        System.out.println("Plano de treino personalizado criado com sucesso:\n" + planoPersonalizado);
    }

    public void mostrarPlanosDeTreinoByUid(int uid) {
        for (PlanoTreino plano : planosDeTreino) {
            if (plano.getRelatedUserId() == uid) {
                System.out.println(plano);
            }
        }
    }
    
    public List<PlanoTreino> getPlanosDeTreino() {
        List<PlanoTreino> planos = new ArrayList<>();
        for (PlanoTreino plano : planosDeTreino) {
            planos.add(plano.clone());
        }
        return planos;
    }
}