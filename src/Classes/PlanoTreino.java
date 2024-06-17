package Classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanoTreino {
    private int codigo;
    private int user_id;
    private LocalDateTime dataInicio;
    private String nomePlano;
    private int vezesPorSemana;
    private List<Atividade> atividades;

    public PlanoTreino(int codigo, int user_id, LocalDateTime dataInicio, String nomePlano, int vezesPorSemana) {
        this.codigo = codigo;
        this.user_id = user_id;
        this.dataInicio = dataInicio;
        this.nomePlano = nomePlano;
        this.vezesPorSemana = vezesPorSemana;
        this.atividades = new ArrayList<>();
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public int getRelatedUserId() {
        return user_id;
    }

    public void setRelatedUserId(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public int getVezesPorSemana() {
        return vezesPorSemana;
    }

    public void setVezesPorSemana(int vezesPorSemana) {
        this.vezesPorSemana = vezesPorSemana;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public void adicionarAtividade(Atividade atividade) {
        this.atividades.add(atividade);
    }

    @Override
    public String toString() {
        return (
            "Codigo do Treino:" + codigo + '\n' +
            "Utilizador:" + user_id + '\n' +
            "Data Início:" + dataInicio + '\n' +
            "Nome do Plano:" + nomePlano + '\n' +
            "Repetições Por Semana:" + vezesPorSemana + '\n' +
            '\n' +
            "Atividades:" + '\n' + atividades + '\n'
        );
    }

    public PlanoTreino clone() {
        PlanoTreino clone = new PlanoTreino(this.codigo, this.user_id, this.dataInicio, this.nomePlano, this.vezesPorSemana);
        clone.setAtividades(new ArrayList<>(this.atividades));
        return clone;
    }
}