import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

import Classes.FileManager;
import Classes.Terminal;
import Classes.Utilizador;
import Classes.Atividade;
import Atividades.Corrida;
import Atividades.LevantamentoPesos;
import Atividades.Remo;
import Atividades.Abdominais;
import Atividades.Elevacoes;

public class GestaoAtividades {
    private List<Atividade> atividades;
    @SuppressWarnings("unused") // o warning é devido à variavel poder ser sempre substituida por LocalDateTime.now(), mas como nós queremos utilizar getters e setters ignoramos o warning
    private LocalDateTime dataHora;

    public GestaoAtividades() {
        this.atividades = new ArrayList<>();
        this.dataHora = LocalDateTime.now(); 
    }

    public void registaAtividade(Atividade atividade) {
        atividades.add(atividade);
        saveAtividades();
    }

    public void addAtividade(Atividade atividade) {
        atividades.add(atividade);
    }

    public void loadAtividades() {
        String readAtividades = FileManager.readFromFile(FileManager.activitiesFile);
        List<String> atividades = new ArrayList<>();
        if (!readAtividades.isEmpty()) {
            atividades = new ArrayList<>(List.of(readAtividades.split("\n")));
        }
        for (String atividade : atividades) {
            // the first 5 elements are the same for all activities the rest is specific to each activity
            String[] atividadeData = atividade.split(";");
            int planoTreino = Integer.parseInt(atividadeData[0]);
            String activityName = atividadeData[1];
            double duracao = Double.parseDouble(atividadeData[2]);
            LocalDateTime dataHora = LocalDateTime.parse(atividadeData[3]);
            int userId = Integer.parseInt(atividadeData[4]);
            double caloriasQueimadas = Double.parseDouble(atividadeData[5]);
            // from 6 to the end is specific to each activity
            if (activityName.equals("Corrida")) {
                double distancia = Double.parseDouble(atividadeData[6]);
                double altimetria = Double.parseDouble(atividadeData[7]);
                Corrida corrida = new Corrida(planoTreino, duracao, dataHora, userId, caloriasQueimadas, distancia, altimetria);
                addAtividade(corrida);
            }
            else if (activityName.equals("LevantamentoPesos")) {
                int repeticoes = Integer.parseInt(atividadeData[6]);
                double peso = Double.parseDouble(atividadeData[7]);
                LevantamentoPesos levantamentoPesos = new LevantamentoPesos(planoTreino, duracao, dataHora, userId, caloriasQueimadas, repeticoes, peso);
                addAtividade(levantamentoPesos);
            }
            else if (activityName.equals("Remo")) {
                double distancia = Double.parseDouble(atividadeData[6]);
                Remo remo = new Remo(planoTreino, duracao, dataHora, userId, caloriasQueimadas, distancia);
                addAtividade(remo);
            }
            else if (activityName.equals("Abdominais")) {
                int repeticoes = Integer.parseInt(atividadeData[6]);
                Abdominais abdominais = new Abdominais(planoTreino, duracao, dataHora, userId, caloriasQueimadas, repeticoes);
                addAtividade(abdominais);
            }
            else if (activityName.equals("Elevações")) {
                int repeticoes = Integer.parseInt(atividadeData[6]);
                Elevacoes elevacoes = new Elevacoes(planoTreino, duracao, dataHora, userId, caloriasQueimadas, 3.0, repeticoes);
                addAtividade(elevacoes);
            } else {
                System.out.println(Terminal.ANSI_RED + "Atividade não reconhecida." + Terminal.ANSI_RESET);
            }
        }
    }

    public void saveAtividades() {
        String atividades = "";
        for (Atividade atividade : this.atividades) {
            atividades += atividade.getPlanoTreinoId() + ";" + atividade.getNome() + ";" + atividade.getDuracao() + ";" + atividade.getDataHora() + ";" + atividade.getRelatedUserId() + ";" + atividade.getCaloriasQueimadas() + ";";
            if (atividade instanceof Corrida) {
                Corrida corrida = (Corrida) atividade;
                atividades += corrida.getDistancia() + ";" + corrida.getAltimetria();
            } else if (atividade instanceof LevantamentoPesos) {
                LevantamentoPesos levantamentoPesos = (LevantamentoPesos) atividade;
                atividades += levantamentoPesos.getRepeticoes() + ";" + levantamentoPesos.getPeso();
            } else if (atividade instanceof Remo) {
                Remo remo = (Remo) atividade;
                atividades += remo.getDistancia();
            } else if (atividade instanceof Abdominais) {
                Abdominais abdominais = (Abdominais) atividade;
                atividades += abdominais.getRepeticoes();
            } else if (atividade instanceof Elevacoes) {
                Elevacoes elevacoes = (Elevacoes) atividade;
                atividades += elevacoes.getRepeticoes();
            }
            atividades += "\n";
        }
        FileManager.writeToFileWithoutAppending(FileManager.activitiesFile, atividades);
    }

    public List<Atividade> getUserActivitiesList(int userId) {
        List<Atividade> userActivities = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getRelatedUserId() == userId) {
                userActivities.add(atividade);
            }
        }
        return userActivities;
    }

    public List<Atividade> getPlanoActivitiesList(int planoTreinoId) {
        List<Atividade> planoActivities = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if (atividade.getPlanoTreinoId() == planoTreinoId) {
                planoActivities.add(atividade);
            }
        }
        return planoActivities;
    }
    
    public void mostrarAtividades(LocalDateTime dataHoraAtual) {
        if (atividades.isEmpty()) {
            System.out.println("Não existem atividades registadas.\n");
        } else {
            System.out.println("Atividades Registadas:");
            for (Atividade atividade : atividades) {
                System.out.println(atividade);
                LocalDateTime fimAtividade = atividade.getDataHora().plusHours((long) atividade.getDuracao());
                if (dataHoraAtual.isAfter(fimAtividade) || dataHoraAtual.isEqual(fimAtividade)) {
                    System.out.println("ATIVIDADE CONCLUÍDA!");
                    System.out.println("Calorias queimadas: " + atividade.getCaloriasQueimadas()+ "\n");
                } else {
                    System.out.println("Atividade ainda não concluída.\n");
                }
            }
        }
    }

    public void removeAtividadesByPlanoId(int planoTreinoId) {
        atividades.removeIf(atividade -> atividade.getPlanoTreinoId() == planoTreinoId);
        saveAtividades();
    }

    public void mostrarAtividadesByUid(int user_id, LocalDateTime dataHoraAtual) {
        if (atividades.isEmpty()) {
            System.out.println("Não existem atividades registadas.");
            System.out.print("\n");
        } else {
            System.out.println("Atividades Registadas:");
            for (Atividade atividade : atividades) {
                if (atividade.getRelatedUserId() == user_id) {
                    // we could do "if atividade.getPlandTreinoId() == 0" to only show activities that are not related to a plan
                    System.out.println(atividade);
                    LocalDateTime fimAtividade = atividade.getDataHora().plusHours((long) atividade.getDuracao());
                    if (dataHoraAtual.isAfter(fimAtividade) || dataHoraAtual.isEqual(fimAtividade)) {
                        System.out.println("ATIVIDADE CONCLUÍDA!");
                        System.out.println("Calorias queimadas: " + atividade.getCaloriasQueimadas()+ "\n");
                    } else {
                        System.out.println("Atividade ainda não concluída.\n");
                    }
                }
            }
        }
    }

    public List<Atividade> getAtividadesList() {
        return this.atividades;
    }

    // Atividades Individuais
    public Corrida createCorrida(Scanner scanner, Utilizador user, int planoTreinoId, double duracao) {
        System.out.print(Terminal.ANSI_YELLOW + "Insira a distância percorrida (em km): " + Terminal.ANSI_RESET);
        double distancia = scanner.nextDouble();
        System.out.print(Terminal.ANSI_YELLOW + "Insira a altimetria (em metros): " + Terminal.ANSI_RESET);
        double altimetria = scanner.nextDouble();
        scanner.nextLine();
    
        Corrida corrida = new Corrida(planoTreinoId, duracao, LocalDateTime.now(), user.getUid(), 0, distancia, altimetria);
        double calorias = corrida.calcularCalorias(user);
        corrida.setCaloriasQueimadas(calorias);
    
        return corrida;
    }

    public LevantamentoPesos createLevantamentoPesos(Scanner scanner, Utilizador user, int planoTreinoId,  double duracao) {
        System.out.print(Terminal.ANSI_YELLOW + "Insira o número de repetições: " + Terminal.ANSI_RESET);
        int repeticoes = scanner.nextInt();
        scanner.nextLine();
        System.out.print(Terminal.ANSI_YELLOW + "Insira o peso levantado (em kg): " + Terminal.ANSI_RESET);
        double peso = scanner.nextDouble();
        scanner.nextLine();

        LevantamentoPesos levantamentoPesos = new LevantamentoPesos(planoTreinoId, duracao, LocalDateTime.now(), user.getUid(), 0, repeticoes, peso);
        double calorias = levantamentoPesos.calcularCalorias(user);
        levantamentoPesos.setCaloriasQueimadas(calorias);

        return levantamentoPesos;
    }

    public Remo createRemo(Scanner scanner, Utilizador user, int planoTreinoId, double duracao) {
        System.out.print(Terminal.ANSI_YELLOW + "Insira a distância percorrida (em km): " + Terminal.ANSI_RESET);
        double distancia = scanner.nextDouble();
        scanner.nextLine();

        Remo remo = new Remo(planoTreinoId, duracao, LocalDateTime.now(), user.getUid(), 0, distancia);
        double calorias = remo.calcularCalorias(user);
        remo.setCaloriasQueimadas(calorias);

        return remo;
    }

    public Abdominais createAbdominais(Scanner scanner, Utilizador user, int planoTreinoId, double duracao) {
        System.out.print(Terminal.ANSI_YELLOW + "Insira o número de repetições: " + Terminal.ANSI_RESET);
        int repeticoes = scanner.nextInt();
        scanner.nextLine();

        Abdominais abdominais = new Abdominais(planoTreinoId, duracao, LocalDateTime.now(), user.getUid(), 0, repeticoes);
        double calorias = abdominais.calcularCalorias(user);
        abdominais.setCaloriasQueimadas(calorias);

        return abdominais;
    }

    public Elevacoes createElevacoes(Scanner scanner, Utilizador user, int planoTreinoId, double duracao) {
        System.out.print(Terminal.ANSI_YELLOW + "Insira o número de repetições: " + Terminal.ANSI_RESET);
        int repeticoes = scanner.nextInt();
        scanner.nextLine();

        Elevacoes elevacoes = new Elevacoes(planoTreinoId, duracao, LocalDateTime.now(), user.getUid(), 0, 3.0, repeticoes);
        double calorias = elevacoes.calcularCalorias(user);
        elevacoes.setCaloriasQueimadas(calorias);

        return elevacoes;
    }
}