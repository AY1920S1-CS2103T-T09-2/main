package seedu.exercise.logic.commands.statistic;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;

/**
 * A class to generate Statistic depending on the chart type.
 */
public class StatsFactory {

    private ObservableList<Exercise> exercises;
    private String chart;
    private String category;
    private Date startDate;
    private Date endDate;

    /**
     * Generates a StatsFactory object that can generate Statistic.
     */
    public StatsFactory(ReadOnlyResourceBook<Exercise> exercises, String chart, String category,
                        Date startDate, Date endDate) {
        this.exercises = exercises.getResourceList();
        this.chart = chart;
        this.category = category;
        if (startDate == null && endDate == null) {
            this.startDate = Date.getOneWeekBeforeToday();
            this.endDate = Date.getToday();
        } else {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    /**
     * Generates and returns statistic for different chart type.
     */
    public Statistic generateStatistic() {
        HashMap<String, Double> data;
        if (chart.equals("linechart")) {

            return generateLineChartStatistic();

        } else if (chart.equals("piechart")) {

            return generatePieChartStatistic();

        } else { //barchart

            return generateBarChartStatistic();

        }
    }

    /**
     * Generate statistic for line chart.
     */
    private Statistic generateLineChartStatistic() {
        ArrayList<Date> dates = Date.getListOfDates(startDate, endDate);
        ArrayList<Double> values;

        if (category.equals("exercise")) {
            values = exerciseCountByDate(getFilteredExercise(), dates);
        } else {
            values = caloriesByDate(getFilteredExercise(), dates);
        }

        return new Statistic(category, chart, startDate, endDate, datesToString(dates), values);
    }

    /**
     * Generate statistic for bar chart.
     */
    private Statistic generateBarChartStatistic() {
        HashMap<String, Double> data;
        if (category.equals("exercise")) {
            data = getTotalExerciseCount();
        } else { //calories
            data = getTotalCaloriesData();
        }

        ArrayList<String> names = hashMapNameToList(data);
        ArrayList<Double> values = hashMapDoubleToList(data, names);

        return new Statistic(category, chart, startDate, endDate, names, values);
    }

    /**
     * Generate statistic for pie chart.
     */
    private Statistic generatePieChartStatistic() {
        HashMap<String, Double> data;
        if (category.equals("exercise")) {
            data = getTotalExerciseQuantity();
        } else { //calories
            data = getTotalCaloriesData();
        }

        ArrayList<String> names = hashMapNameToList(data);
        ArrayList<Double> values = hashMapDoubleToList(data, names);

        return new Statistic(category, chart, startDate, endDate, names, values);
    }

    /**
     * Compute exercise count with filtered exercises list.
     */
    private HashMap<String, Double> getTotalExerciseCount() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        HashMap<String, Double> data = new HashMap<>();

        for (Exercise e : filteredExercise) {
            Name name = e.getName();
            Unit unit = e.getUnit();
            String nameWithUnit = name.toString() + " (" + unit.toString() + ")";
            double count = 1;

            if (data.containsKey(nameWithUnit)) {
                count = data.get(nameWithUnit) + 1;
            }

            data.put(nameWithUnit, count);
        }

        return data;
    }

    /**
     * Compute exercise quantity with filtered exercises list.
     */
    private HashMap<String, Double> getTotalExerciseQuantity() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        HashMap<String, Double> data = new HashMap<>();

        for (Exercise e : filteredExercise) {
            Name name = e.getName();
            Unit unit = e.getUnit();
            String nameWithUnit = name.toString() + " (" + unit.toString() + ")";
            double quantity = 1;

            if (data.containsKey(nameWithUnit)) {
                quantity = data.get(nameWithUnit) + 1;
            }

            data.put(nameWithUnit, quantity);
        }

        return data;
    }

    /**
     * Compute calories with filtered exercises list.
     */
    private HashMap<String, Double> getTotalCaloriesData() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        HashMap<String, Double> data = new HashMap<>();

        for (Exercise e : filteredExercise) {
            String name = e.getName().toString();
            double calories = Double.parseDouble(e.getCalories().toString());

            if (data.containsKey(name)) {
                double temp = data.get(name);
                calories += temp;
            }

            data.put(name, calories);
        }

        return data;
    }

    /**
     * Get all exercises between start and end date.
     */
    private ArrayList<Exercise> getFilteredExercise() {
        ArrayList<Exercise> filteredExercise = new ArrayList<>();
        for (Exercise e : exercises) {
            Date date = e.getDate();
            if (Date.isBetweenStartAndEndDate(date, startDate, endDate)) {
                filteredExercise.add(e);
            }
        }

        return filteredExercise;
    }

    /**
     * Get list of names from data computed.
     */
    private ArrayList<String> hashMapNameToList(HashMap<String, Double> data) {
        return new ArrayList<>(data.keySet());
    }

    /**
     * Get list of values from data computed.
     */
    private ArrayList<Double> hashMapDoubleToList(HashMap<String, Double> data, ArrayList<String> names) {
        ArrayList<Double> values = new ArrayList<>();

        for (String n : names) {
            values.add(data.get(n));
        }

        return values;
    }

    /**
     * Convert list of dates to list of Strings.
     */
    private ArrayList<String> datesToString(ArrayList<Date> dates) {
        ArrayList<String> list = new ArrayList<>();
        for (Date d : dates) {
            list.add(d.toString());
        }
        return list;
    }

    /**
     * Generate a list of given size with all zeroes.
     */
    private ArrayList<Double> listWithZeroes(int listSize) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(0.0);
        }
        return list;
    }

    /**
     * Compute exercise count by dates with filtered exercises list.
     */
    private ArrayList<Double> exerciseCountByDate(ArrayList<Exercise> exercises, ArrayList<Date> dates) {

        int size = dates.size();
        ArrayList<Double> values = listWithZeroes(size);

        for (Exercise e : exercises) {
            Date date = e.getDate();
            int index = dates.indexOf(date);
            double quantity = values.get(index) + 1;
            values.set(index, quantity);
        }

        return values;
    }

    /**
     * Compute calories by date with filtered exercises list.
     */
    private ArrayList<Double> caloriesByDate(ArrayList<Exercise> exercises, ArrayList<Date> dates) {

        int size = dates.size();
        ArrayList<Double> values = listWithZeroes(size);

        for (Exercise e : exercises) {
            Date date = e.getDate();
            int index = dates.indexOf(date);
            double calories = Double.parseDouble(e.getCalories().toString()) + values.get(index);
            values.set(index, calories);
        }

        return values;
    }

    /**
     * Returns line chart of calories of the most recent 7 days.
     */
    public Statistic getDefaultStatistic() {
        ArrayList<Exercise> filteredExercise = getFilteredExercise();
        ArrayList<Date> dates = Date.getListOfDates(startDate, endDate);
        ArrayList<String> properties = datesToString(dates);
        ArrayList<Double> values = caloriesByDate(filteredExercise, dates);
        return new Statistic("calories", "linechart", startDate, endDate, properties, values);
    }
}
