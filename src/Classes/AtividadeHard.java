package Classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// uma atividade hard é uma atividade na qual a quantidade de calorias queimadas é superior à de uma atividade normal (tendo em conta um fator de dificuldade)
public class AtividadeHard extends Atividade {
    private double fatorDificuldade; // fator de dificuldade da atividade

    public AtividadeHard(int planoTreinoId, String nome, double duracao, LocalDateTime dataHora, int user_id, double caloriasQueimadas, double fatorDificuldade) {
        super(planoTreinoId, nome, duracao, dataHora, caloriasQueimadas, user_id);
        this.fatorDificuldade = fatorDificuldade;
    }

    @Override
    public double calcularCalorias(Utilizador utilizador) {
        double fatorMultiplicativo = utilizador.obterFatorMultiplicativo();

        return fatorDificuldade * 30 * getDuracao() * fatorMultiplicativo; 
    }

    // Getters e Setters
    public double getFatorDif() {
        return fatorDificuldade;
    }

    public void setFatorDif(double fatorDificuldade) {
        this.fatorDificuldade = fatorDificuldade;
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
            "Fator de Dificuldade:" + fatorDificuldade + '\n' +
            '\n'
        );
    }

    @Override
    public AtividadeHard clone() {
        return new AtividadeHard(getPlanoTreinoId(), getNome(), getDuracao(), getDataHora(), getRelatedUserId(), getCaloriasQueimadas(), fatorDificuldade);
    }
}