import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\dany0\\Downloads\\startup-profit.csv"; // Ruta del archivo CSV
        String line;
        String csvSplitBy = ","; // Delimitador de las columnas en el archivo CSV

        List<Double> profits = new ArrayList<>(); // Lista para almacenar los valores de Profit
        List<Double> rdSpends = new ArrayList<>(); // Lista para almacenar los valores de R&D Spend
        List<Double> marketingSpends = new ArrayList<>(); // Lista para almacenar los valores de Marketing Spend

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            // Omitir la línea de encabezado
            br.readLine();

            // Leer el archivo línea por línea
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy); // Dividir la línea en valores

                // Asumir que las columnas están en el orden: R&D Spend, Administration, Marketing Spend, State, Profit
                double rdSpend = Double.parseDouble(values[0]); // Obtener el valor de R&D Spend
                double marketingSpend = Double.parseDouble(values[2]); // Obtener el valor de Marketing Spend
                double profit = Double.parseDouble(values[4]); // Obtener el valor de Profit

                // Agregar los valores a las listas correspondientes
                rdSpends.add(rdSpend);
                marketingSpends.add(marketingSpend);
                profits.add(profit);
            }

            // Calcular la correlación entre Profit y R&D Spend
            double correlationRD = calculatePearsonCorrelation(profits, rdSpends);
            // Calcular la correlación entre Profit y Marketing Spend
            double correlationMarketing = calculatePearsonCorrelation(profits, marketingSpends);

            // Imprimir los resultados de las correlaciones
            System.out.printf("Coeficiente de correlación entre Profit y R&D Spend: %.2f%n", correlationRD);
            System.out.printf("Coeficiente de correlación entre Profit y Marketing Spend: %.2f%n", correlationMarketing);

        } catch (IOException e) {
            // Manejar posibles errores al leer el archivo
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Método para calcular la correlación de Pearson
    private static double calculatePearsonCorrelation(List<Double> x, List<Double> y) {
        int n = x.size();
        if (n != y.size() || n == 0) {
            throw new IllegalArgumentException("Las listas deben tener el mismo tamaño y no estar vacías.");
        }

        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

        // Calcular las sumas necesarias para la fórmula de correlación
        for (int i = 0; i < n; i++) {
            double xi = x.get(i);
            double yi = y.get(i);
            sumX += xi;
            sumY += yi;
            sumXY += xi * yi;
            sumX2 += xi * xi;
            sumY2 += yi * yi;
        }

        // Calcular el numerador y el denominador de la fórmula de correlación
        double numerator = (n * sumXY) - (sumX * sumY);
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        // Retornar la correlación de Pearson, evitando división por cero
        if (denominator == 0) return 0; // Evitar división por cero
        return numerator / denominator;
    }
}
