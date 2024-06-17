package Atividades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Classes.AtividadeHard;

public class Elevacoes extends AtividadeHard {
    private int repeticoes;

    public Elevacoes(int planoTreinoId, double duracao, LocalDateTime dataHora, int user_id, double caloriasQueimadas, double fatorDificuldade, int repeticoes) {
        super(planoTreinoId, "Elevações", duracao, dataHora, user_id, caloriasQueimadas, fatorDificuldade);
        this.repeticoes = repeticoes;
    }

    // podemos calcular com uma formula mais complexa usando na mesma o fator de dificuldade fazendo "getFatorDif()"
    // ou podemos utilizar a formula já predefinida na classe AtividadeHard
    /*@Override
    public double calcularCalorias(Utilizador utilizador) {
        double fatorMultiplicativo = utilizador.obterFatorMultiplicativo();
        return repeticoes * 0.1 * getDuracao() * fatorMultiplicativo;
    }*/

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
            "Fator de Dificuldade:" + getFatorDif() + '\n' +
            '\n'
        );
    }

    @Override
    public Elevacoes clone() {
        return new Elevacoes(getPlanoTreinoId(), getDuracao(), getDataHora(), getRelatedUserId(), getCaloriasQueimadas(), getFatorDif(), repeticoes);
    }
}
