package Atividades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Classes.Atividade;
import Classes.Utilizador;

public class Corrida extends Atividade {
    private double distancia; // Distância em km
    private double altimetria; // Altura em metros

    public Corrida() {
        super();
        this.distancia = 0.0;
        this.altimetria = 0.0;
    }

    public Corrida(int planoTreinoId, double duracao, LocalDateTime dataHora, int user_id, double caloriasQueimadas, double distancia, double altimetria) {
        super(planoTreinoId, "Corrida", duracao, dataHora, caloriasQueimadas, user_id);
        this.distancia = distancia;
        this.altimetria = altimetria;
    }

    @Override
    public double calcularCalorias(Utilizador utilizador) {
        double fatorMultiplicativo = utilizador.obterFatorMultiplicativo();
        return (distancia + altimetria) * 50 * getDuracao() * fatorMultiplicativo;
    }


    // Getters e Setters
    public double getDistancia() {
        return distancia;
    }

    public double getAltimetria() {
        return altimetria;
    }
    
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public void setAltimetria(double altimetria) {
        this.altimetria = altimetria;
    }

    // toString e clone, se for necessário que ainda nao sei
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
            "Altimetria:" + altimetria + '\n' +
            '\n'
        );
    }

    @Override
    public Corrida clone() {
        return new Corrida(getPlanoTreinoId(), getDuracao(), getDataHora(), getRelatedUserId(), getCaloriasQueimadas(), distancia, altimetria);
    }
}