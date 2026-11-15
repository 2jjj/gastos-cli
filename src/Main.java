import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Path FILE = Path.of("gastos.csv");

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("gastos - digite 'ajuda' para ver os comandos");
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) break;
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equals("sair")) break;
            handle(line);
        }
    }

    private static void handle(String line) throws IOException {
        String[] parts = line.split("\\s+", 4);
        switch (parts[0]) {
            case "add" -> {
                if (parts.length < 4) {
                    System.out.println("uso: add <categoria> <valor> <descricao>");
                    return;
                }
                double value;
                try {
                    value = Double.parseDouble(parts[2].replace(',', '.'));
                } catch (NumberFormatException e) {
                    System.out.println("valor invalido");
                    return;
                }
                String row = parts[1] + "," + String.format(Locale.US, "%.2f", value) + "," + parts[3];
                Files.writeString(FILE, row + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                System.out.println("ok");
            }
            case "list" -> {
                for (String row : rows()) System.out.println(row);
            }
            case "resumo" -> {
                Map<String, Double> totals = new LinkedHashMap<>();
                for (String row : rows()) {
                    String[] cols = row.split(",", 3);
                    if (cols.length < 3) continue;
                    totals.merge(cols[0], Double.parseDouble(cols[1]), Double::sum);
                }
                totals.forEach((category, total) -> System.out.printf("%-14s R$ %.2f%n", category, total));
                double sum = totals.values().stream().mapToDouble(Double::doubleValue).sum();
                System.out.printf("%-14s R$ %.2f%n", "total", sum);
            }
            case "ajuda" -> System.out.println("add <categoria> <valor> <descricao> | list | resumo | sair");
            default -> System.out.println("comando desconhecido");
        }
    }

    private static List<String> rows() throws IOException {
        if (!Files.exists(FILE)) return new ArrayList<>();
        return Files.readAllLines(FILE);
    }
}
