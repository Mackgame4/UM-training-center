package Classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Atividade implements Serializable {
    private int planoTreinoId;
    private String nome;
    private double duracao; // em horas
    private LocalDateTime dataHora;
    private double caloriasQueimadas;
    private int user_id;

    public Atividade() {
        this.planoTreinoId = 0;
        this.nome = "";
        this.duracao = 0.0;
        this.user_id = 0;
        this.caloriasQueimadas = 0.0;
        this.dataHora = LocalDateTime.now();
    }

    public Atividade(int planoTreinoId, String nome, double duracao, LocalDateTime dataHora, double caloriasQueimadas, int user_id) {
        this.planoTreinoId = planoTreinoId;
        this.nome = nome;
        this.duracao = duracao;
        this.user_id = user_id;
        this.caloriasQueimadas = caloriasQueimadas; 
        this.dataHora = dataHora;
    }

    public Atividade(Atividade outraAtividade) {
        this.planoTreinoId = outraAtividade.getPlanoTreinoId();
        this.nome = outraAtividade.getNome();
        this.duracao = outraAtividade.getDuracao();
        this.user_id = outraAtividade.getRelatedUserId();
        this.caloriasQueimadas = outraAtividade.getCaloriasQueimadas();
        this.dataHora = outraAtividade.getDataHora();
    }

    // Método abstrato para calcular calorias
    public abstract double calcularCalorias(Utilizador utilizador);

    protected double obterFatorMultiplicativo(Utilizador utilizador) {
        switch (utilizador.getTipo()) {
            case PROFISSIONAL: return 1.2;
            case AMADOR: return 1.0;
            case PRATICANTE_OCASIONAL: return 0.8;
            default: return 1.0;
        }
    }

    // Getters e Setters
    public int getPlanoTreinoId() {
        return planoTreinoId;
    }

    public void setPlanoTreinoId(int planoTreinoId) {
        this.planoTreinoId = planoTreinoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setRelatedUserId(int user_id) {
        this.user_id = user_id;
    } 

    public int getRelatedUserId() {
        return this.user_id;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getCaloriasQueimadas() {
        return caloriasQueimadas;
    }

    public void setCaloriasQueimadas(double caloriasQueimadas) {
        this.caloriasQueimadas = caloriasQueimadas;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // para termos o padrao habitual

        String dataHoraFormatada = dataHora.format(formatador);

        return (
            // if theres a planoTreinoId, print it
            (planoTreinoId > 0 ? "Plano de Treino ID:" + planoTreinoId + '\n' : "") +
            "Nome:" + nome + '\n' +
            "Duração:" + duracao + '\n' +
            "Data e Hora:" + dataHoraFormatada + '\n' +
            "Calorias Queimadas:" + caloriasQueimadas + '\n' +
            "User ID:" + user_id + '\n'
        );
    }
}