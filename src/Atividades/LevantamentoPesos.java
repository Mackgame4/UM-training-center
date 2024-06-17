package Atividades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Classes.Atividade;
import Classes.Utilizador;

public class LevantamentoPesos extends Atividade {
    private int repeticoes;
    private double peso; // Peso em kg

    public LevantamentoPesos() {
        super();
        this.repeticoes = 0;
        this.peso = 0.0;
    }

    public LevantamentoPesos(int planoTreinoId, double duracao, LocalDateTime dataHora, int user_id, double caloriasQueimadas, int repeticoes, double peso) {
        super(planoTreinoId, "LevantamentoPesos", duracao, dataHora, caloriasQueimadas, user_id);
        this.repeticoes = repeticoes;
        this.peso = peso;
    }

    @Override
    public double calcularCalorias(Utilizador utilizador) {
        double fatorMultiplicativo = utilizador.obterFatorMultiplicativo();
        
        return (repeticoes * peso) * 0.2 * getDuracao() * fatorMultiplicativo;
    }

    // Getters e Setters
    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    // toString e clone
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // para termos o padrao habitual

        String dataHoraFormatada = getDataHora().format(formatador);

        return (
            getNome() + '\n' +
            (getPlanoTreinoId() > 0 ? "Plano de Treino ID:" + getPlanoTreinoId() + '\n' : "") +
            "Duração:" + getDuracao() + '\n' +
            "Data e Hora:" + dataHoraFormatada + '\n' +
            "User ID:" + getRelatedUserId() + '\n' +
            "Repetições:" + repeticoes + '\n' +
            "Peso:" + peso + '\n' +
            '\n'
        );
    }

    @Override
    public LevantamentoPesos clone() {
        return new LevantamentoPesos(getPlanoTreinoId(), getDuracao(), getDataHora(), getRelatedUserId(), getCaloriasQueimadas(), repeticoes, peso);
    }
}