package Classes;

import java.io.Serializable;

public class Utilizador implements Serializable {
    public enum PermissionLevel {
        USER,
        ADMIN
    }

    public enum TipoUtilizador {
        PRATICANTE_OCASIONAL,
        AMADOR,
        PROFISSIONAL
    }

    private int uid;
    private String nome;
    private String email;
    private String password;
    private String morada;
    public TipoUtilizador tipo;
    private PermissionLevel permissionLevel;
    private double freqCardiacaMedia;

    public Utilizador() {
        this.uid = 0;
        this.nome = "";
        this.email = "";
        this.password = "";
        this.morada = "";
        this.tipo = TipoUtilizador.PRATICANTE_OCASIONAL;
        this.permissionLevel = PermissionLevel.USER;
        this.freqCardiacaMedia = 0.0;
    }

    public Utilizador(int uid, String nome, String email, String password, String morada, TipoUtilizador tipo, PermissionLevel permissionLevel, double freqCardiacaMedia) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.morada = morada;
        this.tipo = tipo;
        this.permissionLevel = permissionLevel;
        this.freqCardiacaMedia = freqCardiacaMedia;
    }

    public Utilizador(Utilizador outroUtilizador) {
        this.uid = outroUtilizador.getUid();
        this.nome = outroUtilizador.getNome();
        this.email = outroUtilizador.getEmail();
        this.password = outroUtilizador.getPassword();
        this.morada = outroUtilizador.getMorada();
        this.tipo = outroUtilizador.getTipo();
        this.permissionLevel = outroUtilizador.getPermissionLevel();
        this.freqCardiacaMedia = outroUtilizador.getFreqCardiacaMedia();
    }

    // Getters
    public int getUid() {
        return uid;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMorada() {
        return morada;
    }

    public TipoUtilizador getTipo() {
        return tipo;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public double getFreqCardiacaMedia() {
        return freqCardiacaMedia;
    }

    // Setters
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setTipo(TipoUtilizador tipo) {
        this.tipo = tipo;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public void setFreqCardiacaMedia(double freqCardiacaMedia) {
        this.freqCardiacaMedia = freqCardiacaMedia;
    }

    public double obterFatorMultiplicativo() { // isto é um valor padrão para depois fazer o calculo de calorias
        return 1.0; 
    }

    public String displayUser() {
        return (
            "Utilizador: " + uid + "\n" +
            "Nome: " + nome + "\n" +
            "Email: " + email + "\n" +
            "Morada: " + morada + "\n" +
            "Tipo de Utilizador: " + tipo + "\n" +
            "Nível de Permissão: " + permissionLevel + "\n" +
            "Frequência Cardíaca Média: " + freqCardiacaMedia + "\n"
        );
    }

    // Metodo toString define depois o output ao exibir os utilizadores registados
    @Override
    public String toString() {
        return (
            "Utilizador: " + uid + "\n" +
            "Nome: " + nome + "\n" +
            "Email: " + email + "\n" +
            "Password: " + password + "\n" +
            "Morada: " + morada + "\n" +
            "Tipo de Utilizador: " + tipo + "\n" +
            "Nível de Permissão: " + permissionLevel + "\n" +
            "Frequência Cardíaca Média: " + freqCardiacaMedia + "\n"
        );
    }

    @Override
    public Utilizador clone() {
        return new Utilizador(this);
    }
}