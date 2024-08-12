import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args) throws IOException {
        String csvFile = "C:\\Users\\dany0\\Downloads\\startup-profit.csv";
        String line;
        String csvSplitBy = ",";

        List<Double> profits = new ArrayList<>();
        List<Double> rdSpends = new ArrayList<>();
        List<Double> marketingSpends = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        // Skip header line
        br.readLine();

        // Read the file line by line
        while ((line = br.readLine()) != null) {
            String[] values = line.split(csvSplitBy);

            // Assuming columns are in the order: R&D Spend, Marketing Spend, Profit
            double rdSpend = Double.parseDouble(values[0]);
            double marketingSpend = Double.parseDouble(values[2]);
            double profit = Double.parseDouble(values[4]);

            rdSpends.add(rdSpend);
            marketingSpends.add(marketingSpend);
            profits.add(profit);
        }

        br.close(); // Close the BufferedReader

        double correlationRD = calculatePearsonCorrelation(profits, rdSpends);
        double correlationMarketing = calculatePearsonCorrelation(profits, marketingSpends);

        System.out.printf("Coeficiente de correlación entre profit y R&D Spend: %.2f%n", correlationRD);
        System.out.printf("Coeficiente de correlación entre profit y Marketing Spend: %.2f%n", correlationMarketing);
    }

    private static double calculatePearsonCorrelation(List<Double> x, List<Double> y) {
        int n = x.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

        for (int i = 0; i < n; i++) {
            double xi = x.get(i);
            double yi = y.get(i);
            sumX += xi;
            sumY += yi;
            sumXY += xi * yi;
            sumX2 += xi * xi;
            sumY2 += yi * yi;
        }

        double numerator = (n * sumXY) - (sumX * sumY);
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        if (denominator == 0) return 0; // Avoid division by zero
        return numerator / denominator;
    }
}
