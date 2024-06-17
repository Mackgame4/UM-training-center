import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import Classes.Atividade;
import Classes.Utilizador;
import Classes.PlanoTreino;
import Atividades.*;

public class GestaoEstatisticas {
    private GestaoUtilizadores gestaoUtilizadores;
    private GestaoAtividades gestaoAtividades;
    private GestaoPlanoTreino gestaoPlanoTreino;
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public GestaoEstatisticas(GestaoUtilizadores gestaoUtilizadores, GestaoAtividades gestaoAtividades, GestaoPlanoTreino gestaoPlanoTreino) {
        this.gestaoUtilizadores = gestaoUtilizadores;
        this.gestaoAtividades = gestaoAtividades;
        this.gestaoPlanoTreino = gestaoPlanoTreino;
    }

    public void utilizadorMaiorGastoPeriodo(String dataInicioStr, String dataFimStr) {
        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

        Map<Integer, Double> caloriasPorUtilizador = new HashMap<>();
        for (Atividade atividade : gestaoAtividades.getAtividadesList()) {
            if (atividade.getDataHora().isAfter(dataInicio) && atividade.getDataHora().isBefore(dataFim)) {
                int user_id = atividade.getRelatedUserId();
                double caloriasAtividade = atividade.getCaloriasQueimadas();
                caloriasPorUtilizador.put(user_id, caloriasPorUtilizador.getOrDefault(user_id, 0.0) + caloriasAtividade);
            }
        }

        double maiorGasto = 0;
        int codigoMaiorGasto = 0;
        for (Map.Entry<Integer, Double> entry : caloriasPorUtilizador.entrySet()) {
            if (entry.getValue() > maiorGasto) {
                maiorGasto = entry.getValue();
                codigoMaiorGasto = entry.getKey();
            }
        }

        System.out.println("\nO utilizador com maior gasto calórico entre " + dataInicioStr + " e " + dataFimStr + " é o utilizador " + codigoMaiorGasto + " com um gasto de " + maiorGasto + " calorias.");
    }


    public void utilizadorMaiorGasto() {
        Map<Integer,Double> caloriasPorUtilizador = new HashMap<>();
        for (Atividade atividade : gestaoAtividades.getAtividadesList()) {
            int user_id = atividade.getRelatedUserId();
            double caloriasAtividade = atividade.getCaloriasQueimadas();
            if (caloriasPorUtilizador.containsKey(user_id)) {
                double temp = caloriasPorUtilizador.get(user_id);
                caloriasPorUtilizador.put(user_id, temp + caloriasAtividade);
            } 
            else {
                caloriasPorUtilizador.put(user_id, caloriasAtividade);
            }
        }
        double maiorGasto = 0;
        int codigoMaiorGasto = 0;
        for (Map.Entry<Integer,Double> entry : caloriasPorUtilizador.entrySet()) {
            if (entry.getValue() > maiorGasto) {
                maiorGasto = entry.getValue();
                codigoMaiorGasto = entry.getKey();
            }
        }
        System.out.println("\nO utilizador com maior gasto calórico é o utilizador " + codigoMaiorGasto + " com um gasto de " + maiorGasto + " calorias.");
    }

    public void utilizadorMaisAtivoPeriodo(String dataInicioStr, String dataFimStr){
        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

        int utilizadorMaisAtivo = 0;
        int atividadesUtilizadorMaisAtivo = 0;
        for (Utilizador utilizador : gestaoUtilizadores.getUtilizadores().values()) {
            int atividadesUtilizador = 0;
            for (Atividade atividade : gestaoAtividades.getUserActivitiesList(utilizador.getUid())) {
                if (atividade.getDataHora().isAfter(dataInicio) && atividade.getDataHora().isBefore(dataFim)) {
                    atividadesUtilizador++;
                }
            }
            if (atividadesUtilizador > atividadesUtilizadorMaisAtivo) {
                atividadesUtilizadorMaisAtivo = atividadesUtilizador;
                utilizadorMaisAtivo = utilizador.getUid();
            }
        }

        System.out.println("\nO utilizador mais ativo entre " + dataInicioStr + " e " + dataFimStr + " é o utilizador " + utilizadorMaisAtivo + " com " + atividadesUtilizadorMaisAtivo + " atividades registadas.");
    }

    public void utilizadorMaisAtivo(){
        int utilizadorMaisAtivo = 0;
        int atividadesUtilizadorMaisAtivo = 0;

        for (Utilizador utilizador : gestaoUtilizadores.getUtilizadores().values()) {
            int atividadesUtilizador = gestaoAtividades.getUserActivitiesList(utilizador.getUid()).size();
            if (atividadesUtilizador > atividadesUtilizadorMaisAtivo) {
                atividadesUtilizadorMaisAtivo = atividadesUtilizador;
                utilizadorMaisAtivo = utilizador.getUid();
            }
        }

        System.out.println("\nO utilizador mais ativo é o utilizador " + utilizadorMaisAtivo + " com " + atividadesUtilizadorMaisAtivo + " atividades registadas.");
    }

    public void tipoAtividadeMaisRealizada() {
        Map<String,Integer> atividadesPorTipo = new HashMap<>();
        for (Atividade atividade : gestaoAtividades.getAtividadesList()) {
            if (atividade instanceof LevantamentoPesos) {
                if (atividadesPorTipo.containsKey("LevantamentoPesos")) {
                    int temp = atividadesPorTipo.get("LevantamentoPesos");
                    atividadesPorTipo.put("LevantamentoPesos", temp + 1);
                } 
                else {
                    atividadesPorTipo.put("LevantamentoPesos", 1);
                }
            } 
            else if (atividade instanceof Remo) {
                if (atividadesPorTipo.containsKey("Remo")) {
                    int temp = atividadesPorTipo.get("Remo");
                    atividadesPorTipo.put("Remo", temp + 1);
                } 
                else {
                    atividadesPorTipo.put("Remo", 1);
                }
            } 
            else if (atividade instanceof Abdominais) {
                if (atividadesPorTipo.containsKey("Abdominais")) {
                    int temp = atividadesPorTipo.get("Abdominais");
                    atividadesPorTipo.put("Abdominais", temp + 1);
                } 
                else {
                    atividadesPorTipo.put("Abdominais", 1);
                }
            } else if (atividade instanceof Corrida) {
                if (atividadesPorTipo.containsKey("Corrida")) {
                    int temp = atividadesPorTipo.get("Corrida");
                    atividadesPorTipo.put("Corrida", temp + 1);
                } 
                else {
                    atividadesPorTipo.put("Corrida", 1);
                }
            }
        }
        int maiorNumeroAtividades = 0;
        String tipoAtividadeMaisRealizada = "";
        for (Map.Entry<String,Integer> entry : atividadesPorTipo.entrySet()) {
            if (entry.getValue() > maiorNumeroAtividades) {
                maiorNumeroAtividades = entry.getValue();
                tipoAtividadeMaisRealizada = entry.getKey();
            }
        }
        System.out.println("\nO tipo de atividade mais realizada é " + tipoAtividadeMaisRealizada + " com " + maiorNumeroAtividades + " ocurrências registadas.");
    }

    public void kmsUtilizadorPeriodo(String dataInicioStr, String dataFimStr, int codigoUtilizador)  {
        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

        double kms = 0;
        Utilizador utilizador = gestaoUtilizadores.getUtilizadores().get(codigoUtilizador);

        for (Atividade atividade : gestaoAtividades.getUserActivitiesList(utilizador.getUid())) {
            if (atividade.getDataHora().isAfter(dataInicio) && atividade.getDataHora().isBefore(dataFim)) {
                if (atividade instanceof Corrida) {
                    kms += ((Corrida) atividade).getDistancia();
                } 
                else if (atividade instanceof Remo) {
                    kms += ((Remo) atividade).getDistancia();
                }
            }
        }
        System.out.println("\nO utilizador " + codigoUtilizador + " percorreu " + kms + " kms entre " + dataInicioStr + " e " + dataFimStr + ".");
    }

    public void kmsUtilizador(int codigoUtilizador) {
        double kms = 0;
        Utilizador utilizador = gestaoUtilizadores.getUtilizadores().get(codigoUtilizador);

        for (Atividade atividade : gestaoAtividades.getUserActivitiesList(utilizador.getUid())) {
            if (atividade instanceof Corrida) {
                kms += ((Corrida) atividade).getDistancia();
            } 
            else if (atividade instanceof Remo) {
                kms += ((Remo) atividade).getDistancia();
            }
        }
        System.out.println("\nO utilizador " + codigoUtilizador + " percorreu " + kms + " kms.");
    }

    public void metrosUtilizadorPeriodo(String dataInicioStr, String dataFimStr, int codigoUtilizador) {
        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

        double metros = 0;
        Utilizador utilizador = gestaoUtilizadores.getUtilizadores().get(codigoUtilizador);

        for (Atividade atividade : gestaoAtividades.getUserActivitiesList(utilizador.getUid())) {
            if (atividade.getDataHora().isAfter(dataInicio) && atividade.getDataHora().isBefore(dataFim)) {
                if (atividade instanceof Corrida) {
                    metros += ((Corrida) atividade).getAltimetria();
                } 
                
            }
        }
        System.out.println("\nO utilizador " + codigoUtilizador + " subiu " + metros + " metros entre " + dataInicioStr + " e " + dataFimStr + ".");
    }

    public void metrosUtilizador(int codigoUtilizador) {
        double metros = 0;
        Utilizador utilizador = gestaoUtilizadores.getUtilizadores().get(codigoUtilizador);

        for (Atividade atividade : gestaoAtividades.getUserActivitiesList(utilizador.getUid())) {
            if (atividade instanceof Corrida) {
                metros += ((Corrida) atividade).getAltimetria();
            } 
        }
        System.out.println("\nO utilizador " + codigoUtilizador + " subiu " + metros + " metros.");
    }

    public void planoMaiorGasto() {
        Map<Integer,Double> caloriasPorPlano = new HashMap<>();
        for (PlanoTreino plano : gestaoPlanoTreino.getPlanosDeTreino()) {
            double caloriasPlano = 0;
            for (Atividade atividade : plano.getAtividades()) {
                caloriasPlano += atividade.getCaloriasQueimadas();
            }
            caloriasPorPlano.put(plano.getCodigo(), caloriasPlano);
        }
        double maiorGasto = 0;
        int codigoMaiorGasto = 0;
        for (Map.Entry<Integer,Double> entry : caloriasPorPlano.entrySet()) {
            if (entry.getValue() > maiorGasto) {
                maiorGasto = entry.getValue();
                codigoMaiorGasto = entry.getKey();
            }
        }
        System.out.println("\nO plano de treino com maior gasto calórico é o plano " + codigoMaiorGasto + " com um gasto de " + maiorGasto + " calorias.");
    }

    public void atividadesUtilizador(int codigoUtilizador) {
        // check if user exists
        if (!gestaoUtilizadores.getUtilizadores().containsKey(codigoUtilizador)) {
            System.out.println("Utilizador não encontrado.");
            return;
        }
        Utilizador utilizador = gestaoUtilizadores.getUtilizadores().get(codigoUtilizador);
        System.out.println("Atividades do utilizador " + codigoUtilizador + ":");
        for (Atividade atividade : gestaoAtividades.getUserActivitiesList(utilizador.getUid())) {
            System.out.println(atividade.toString());
        }
    }
}