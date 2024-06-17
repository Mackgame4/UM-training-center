package Atividades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Classes.Atividade;
import Classes.Utilizador;

public class Remo extends Atividade {
    private double distancia; // Distância percorrida em quilômetros

    public Remo(int planoTreinoId, double duracao, LocalDateTime dataHora, int user_id, double caloriasQueimadas, double distancia) {
        super(planoTreinoId, "Remo", duracao, dataHora, caloriasQueimadas, user_id);
        this.distancia = distancia;
    }

    @Override
    public double calcularCalorias(Utilizador utilizador) {
        double fatorMultiplicativo = utilizador.obterFatorMultiplicativo();

        return distancia * 30 * getDuracao() * fatorMultiplicativo; 
    }

    // Getters e Setters
    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
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
            "Distância:" + distancia + '\n' +
            '\n'
        );
    }

    @Override
    public Remo clone() {
        return new Remo(getPlanoTreinoId(), getDuracao(), getDataHora(), getRelatedUserId(), getCaloriasQueimadas(), distancia);
    }
}