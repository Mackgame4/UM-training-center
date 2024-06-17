package Atividades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Classes.Atividade;
import Classes.Utilizador;

public class Abdominais extends Atividade {
    private int repeticoes;

    public Abdominais(int planoTreinoId, double duracao, LocalDateTime dataHora, int user_id, double caloriasQueimadas, int repeticoes) {
        super(planoTreinoId, "Abdominais", duracao, dataHora, caloriasQueimadas, user_id);
        this.repeticoes = repeticoes;
    }

    @Override
    public double calcularCalorias(Utilizador utilizador) {
        double fatorMultiplicativo = utilizador.obterFatorMultiplicativo();
        return repeticoes * 0.1 * getDuracao() * fatorMultiplicativo;
    }

    // Getters e Setters
    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    // toString e clone
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // para termos o padrao habitual

        String dataHoraFormatada = getDataHora().format(formatador);

        return (
            getNome() +'\n' +
            (getPlanoTreinoId() > 0 ? "Plano de Treino ID:" + getPlanoTreinoId() + '\n' : "") +
            "Duração:" + getDuracao() + '\n' +
            "Data e Hora:" + dataHoraFormatada + '\n' +
            "User ID:" + getRelatedUserId() + '\n' +
            "Repetições:" + repeticoes + '\n' +
            '\n'
        );
    }

    @Override
    public Abdominais clone() {
        return new Abdominais(getPlanoTreinoId(), getDuracao(), getDataHora(), getRelatedUserId(), getCaloriasQueimadas(), repeticoes);
    }
}