package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Schedules a regime at a specific date.
 */
public class ScheduleRegimeCommand extends ScheduleCommand {

    public static final String MESSAGE_SUCCESS = "Regime %1$s scheduled on %2$s";
    public static final String MESSAGE_REGIME_NOT_FOUND = "Regime %1$s not in regime book";
    public static final String MESSAGE_CONFLICT = "Regime to be scheduled conflicts with another scheduled regime. "
        + "Opening resolve window...";

    private Regime regime;
    private Date dateToSchedule;

    public ScheduleRegimeCommand(Name regimeName, Date date) {
        this.regime = new Regime(regimeName, new UniqueResourceList<>());
        dateToSchedule = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkExistenceOfRegime(model);
        Schedule toSchedule = getScheduleFromModel(model);

        if (toSchedule == null) {
            return new CommandResult(MESSAGE_CONFLICT, false, false, true);
        }

        schedule(model, toSchedule);

        return new CommandResult(String.format(MESSAGE_SUCCESS, regime.getRegimeName(), dateToSchedule))
                .setShowScheduleList();
    }

    private void checkExistenceOfRegime(Model model) throws CommandException {
        if (!model.hasRegime(regime)) {
            throw new CommandException(String.format(MESSAGE_REGIME_NOT_FOUND, regime.getRegimeName()));
        }
    }

    /**
     * Checks for scheduling conflicts and returns a valid schedule if no conflicts are found.
     * If a conflict is found, returns a null schedule
     */
    private Schedule getScheduleFromModel(Model model) {
        int indexOfRegime = model.getRegimeIndex(regime);
        Regime regimeToSchedule = model.getFilteredRegimeList().get(indexOfRegime);
        Schedule toSchedule = new Schedule(regimeToSchedule, dateToSchedule);

        if (model.hasSchedule(toSchedule)) {
            setConflictState();
            Conflict conflict = buildConflict(model, toSchedule);
            setConflictForModel(model, conflict);
            return null;
        }

        return toSchedule;
    }

    private void schedule(Model model, Schedule toSchedule) {
        model.addSchedule(toSchedule);
    }

    private void setConflictState() {
        MainApp.setState(State.IN_CONFLICT);
    }

    private Conflict buildConflict(Model model, Schedule toSchedule) {
        int indexOfScheduled = model.getAllScheduleData().getResourceIndex(toSchedule);
        Schedule scheduled = model.getFilteredScheduleList().get(indexOfScheduled);
        return new Conflict(scheduled, toSchedule);
    }

    private void setConflictForModel(Model model, Conflict conflict) {
        model.setConflict(conflict);
    }
}
