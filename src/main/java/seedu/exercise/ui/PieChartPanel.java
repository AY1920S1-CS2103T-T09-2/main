package seedu.exercise.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import seedu.exercise.logic.commands.statistic.Statistic;

/**
 * An UI for pie chart.
 */
public class PieChartPanel extends UiPart<Region> {
    private static final String FXML = "PieChartPanel.fxml";

    private Statistic statistic;

    @FXML
    private PieChart pieChart;

    public PieChartPanel(Statistic statistic) {
        super(FXML);
        this.statistic = statistic;
        display();
    }

    /**
     * Set data for pie chart to be displayed.
     */
    private void display() {
        String category = statistic.getCategory();
        String startDate = statistic.getStartDate().toString();
        String endDate = statistic.getEndDate().toString();
        ArrayList<String> properties = statistic.getProperties();
        ArrayList<Double> values = statistic.getValues();

        int size = properties.size();
        if (size == 0) {
            PieChart.Data slice = new PieChart.Data("No exercise data found", 1);
            pieChart.getData().add(slice);
        }

        for (int i = 0; i < size; i++) {
            double percentage = Statistic.percentage(values.get(i), statistic.getTotal());
            String property = ChartTextUtil.propertyFormatter(properties.get(i));
            String propertyWithPercentage = ChartTextUtil.percentageFormatter(property, percentage);
            PieChart.Data slice = new PieChart.Data(propertyWithPercentage, values.get(i));
            pieChart.getData().add(slice);
        }

        pieChart.getData().stream().forEach(data -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(data.getName());
            Tooltip.install(data.getNode(), tooltip);
        });

        pieChart.setLegendVisible(false);
        pieChart.setTitle(ChartTextUtil.pieChartTitleFormatter(category, startDate, endDate));
    }
}
