package Classes;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock {
    private LocalDateTime dataHora;

    public Clock() {
        this.dataHora = LocalDateTime.now();
    }

    public void avancarTempo(Scanner scanner, Utilizador user) {
        System.out.print(Terminal.ANSI_CLEAR);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataHoraFormatada = this.dataHora.format(formatador);
        System.out.println(Terminal.ANSI_YELLOW + "Hora atual: " + Terminal.ANSI_RESET + dataHoraFormatada);
        System.out.print(Terminal.ANSI_YELLOW + "Quantas horas deseja avançar? (0 para sair) " + Terminal.ANSI_RESET);
        int horas = scanner.nextInt();
        dataHora = dataHora.plusHours(horas);
        dataHoraFormatada = this.dataHora.format(formatador);
        System.out.println(Terminal.ANSI_YELLOW + "Hora atual avançada para: " + Terminal.ANSI_RESET + dataHoraFormatada);
        scanner.nextLine(); // make a pause here
    }

    public LocalDateTime getSystemDataHora() {
        return dataHora;
    }

    public LocalDateTime setSystemDataHora(LocalDateTime dataHora) {
        return this.dataHora = dataHora;
    }
}
