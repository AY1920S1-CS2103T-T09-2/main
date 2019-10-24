package seedu.exercise.logic.commands.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.exercise.model.property.Date;

/**
 * Represents a Statistic with data needed to generate chart.
 */
public class Statistic {
    private String category;
    private String chart;
    private Date startDate;
    private Date endDate;
    private ArrayList<String> properties;
    private ArrayList<Double> values;

    /**
     * Every field must be present and not null.
     */
    public Statistic(String category, String chart, Date startDate, Date endDate,
                     ArrayList<String> properties, ArrayList<Double> values) {
        requireAllNonNull(category, chart, startDate, endDate, properties, values);
        this.category = category;
        this.chart = chart;
        this.startDate = startDate;
        this.endDate = endDate;
        this.properties = properties;
        this.values = values;
    }

    /**
     * Resets the existing data of this {@code Statistic} with {@code newStatistic}.
     */
    public void resetData(Statistic newStatistic) {
        requireNonNull(newStatistic);
        setCategory(newStatistic.getCategory());
        setChart(newStatistic.getChart());
        setStartDate(newStatistic.getStartDate());
        setEndDate(newStatistic.getEndDate());
        setProperties(newStatistic.getProperties());
        setValues(newStatistic.getValues());
    }

    private void setCategory(String category) {
        requireNonNull(category);
        this.category = category;
    }

    private void setChart(String chart) {
        requireNonNull(chart);
        this.chart = chart;
    }

    private void setStartDate(Date startDate) {
        requireNonNull(startDate);
        this.startDate = startDate;
    }

    private void setEndDate(Date endDate) {
        requireNonNull(endDate);
        this.endDate = endDate;
    }

    private void setProperties(ArrayList<String> properties) {
        requireNonNull(properties);
        this.properties = properties;
    }

    private void setValues(ArrayList<Double> values) {
        requireNonNull(values);
        this.values = values;
    }

    public String getCategory() {
        return category;
    }

    public String getChart() {
        return chart;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public ArrayList<Double> getValues() {
        return values;
    }
}
