package seedu.exercise.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.exercise.model.book.ExerciseBook;
import seedu.exercise.model.book.ReadOnlyResourceBook;
import seedu.exercise.model.book.RegimeBook;
import seedu.exercise.model.book.ScheduleBook;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.UniqueExerciseList;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.regime.Regime;
import seedu.exercise.model.schedule.Schedule;

/**
 * Contains utility methods for populating {@code ExerciseBook} with sample data.
 */
public class SampleDataUtil {
    public static Exercise[] getSampleExercises() {
        return new Exercise[]{
            new Exercise(new Name("Rope Skipping"), new Date("26/09/2019"), new Calories("330"),
                new Quantity("10"), new Unit("counts"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Cycling"), new Date("26/09/2019"), new Calories("284"),
                new Quantity("5"), new Unit("km"),
                getMuscleSet("Legs")),
            new Exercise(new Name("Strength Training"), new Date("26/09/2019"), new Calories("341"),
                new Quantity("20"), new Unit("counts"),
                getMuscleSet("Chest")),
            new Exercise(new Name("Swimming"), new Date("26/09/2019"), new Calories("354"),
                new Quantity("10"), new Unit("laps"),
                getMuscleSet("Calves")),
            new Exercise(new Name("Bench Press"), new Date("26/09/2019"), new Calories("222"),
                new Quantity("30"), new Unit("counts"),
                getMuscleSet("Triceps")),
            new Exercise(new Name("Running"), new Date("26/09/2019"), new Calories("9999999"),
                new Quantity("2.4"), new Unit("km"),
                getMuscleSet("Legs"))
        };
    }

    public static Regime[] getSampleRegimes() {
        UniqueExerciseList list1 = new UniqueExerciseList();
        list1.add(new Exercise(new Name("Rope Skipping"), new Date("26/09/2019"), new Calories("330"),
            new Quantity("10"), new Unit("counts"),
            getMuscleSet("Legs")));
        list1.add(new Exercise(new Name("Bench Press"), new Date("26/09/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps")));


        UniqueExerciseList list2 = new UniqueExerciseList();
        list2.add(new Exercise(new Name("Running"), new Date("26/09/2019"), new Calories("9999999"),
            new Quantity("2.4"), new Unit("km"),
            getMuscleSet("Legs")));
        list2.add(new Exercise(new Name("Bench Press"), new Date("26/09/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps")));
        list2.add(new Exercise(new Name("Swimming"), new Date("26/09/2019"), new Calories("354"),
            new Quantity("10"), new Unit("laps"),
            getMuscleSet("Calves")));

        UniqueExerciseList list3 = new UniqueExerciseList();
        list3.add(new Exercise(new Name("Rope Skipping"), new Date("26/09/2019"), new Calories("330"),
            new Quantity("10"), new Unit("counts"),
            getMuscleSet("Legs")));
        list3.add(new Exercise(new Name("Swimming"), new Date("26/09/2019"), new Calories("354"),
            new Quantity("10"), new Unit("laps"),
            getMuscleSet("Calves")));
        list3.add(new Exercise(new Name("Bench Press"), new Date("26/09/2019"), new Calories("222"),
            new Quantity("30"), new Unit("counts"),
            getMuscleSet("Triceps")));
        list3.add(new Exercise(new Name("Cycling"), new Date("26/09/2019"), new Calories("284"),
            new Quantity("5"), new Unit("km"),
            getMuscleSet("Legs")));
        list3.add(new Exercise(new Name("Strength Training"), new Date("26/09/2019"), new Calories("341"),
            new Quantity("20"), new Unit("counts"),
            getMuscleSet("Chest")));

        return new Regime[]{
            new Regime(new Name("Level 1"), list1),
            new Regime(new Name("Level 2"), list2),
            new Regime(new Name("Level 3"), list3)
        };
    }

    public static Schedule[] getSampleSchedules() {
        Regime[] sampleRegimes = getSampleRegimes();
        return new Schedule[]{
            new Schedule(sampleRegimes[0], new Date("27/09/2019")),
            new Schedule(sampleRegimes[1], new Date("28/09/2019")),
            new Schedule(sampleRegimes[2], new Date("29/09/2018"))
        };
    }

    public static ReadOnlyResourceBook<Exercise> getSampleExerciseBook() {
        ExerciseBook sampleEb = new ExerciseBook();
        for (Exercise sampleExercise : getSampleExercises()) {
            sampleEb.addResource(sampleExercise);
        }
        return sampleEb;
    }

    public static ReadOnlyResourceBook<Schedule> getSampleScheduleBook() {
        ScheduleBook sampleSb = new ScheduleBook();
        for (Schedule sampleSchedule : getSampleSchedules()) {
            sampleSb.addResource(sampleSchedule);
        }
        return sampleSb;
    }

    /**
     * Returns a muscle set containing the list of strings given.
     */
    public static Set<Muscle> getMuscleSet(String... strings) {
        return Arrays.stream(strings)
            .map(Muscle::new)
            .collect(Collectors.toSet());
    }

    public static ReadOnlyResourceBook<Regime> getSampleRegimeBook() {
        RegimeBook sampleRb = new RegimeBook();
        for (Regime r : getSampleRegimes()) {
            sampleRb.addResource(r);
        }
        return sampleRb;
    }
}
