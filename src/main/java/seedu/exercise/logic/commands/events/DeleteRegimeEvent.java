package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.DeleteRegimeCommand;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Regime;

/**
 * Represents a particular delete regime event that can be redone or undone.
 */
public class DeleteRegimeEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Delete regime: %1$s\n%2$s";

    private final Regime regimeToDelete;

    /**
     * Creates a DeleteRegimeEvent to store the particular event of a regime being deleted from
     * the regime book.
     *
     * @param eventPayload a wrapper class that stores the essential information for undo and redo
     */
    DeleteRegimeEvent(EventPayload<Regime> eventPayload) {
        this.regimeToDelete = eventPayload.get(DeleteRegimeCommand.KEY_REGIME_TO_DELETE);
    }

    @Override
    public void undo(Model model) {
        model.addRegime(regimeToDelete);
    }

    @Override
    public void redo(Model model) {
        model.deleteRegime(regimeToDelete);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                regimeToDelete.getRegimeName(),
                regimeToDelete);
    }
}
