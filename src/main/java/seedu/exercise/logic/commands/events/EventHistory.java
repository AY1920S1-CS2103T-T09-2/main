package seedu.exercise.logic.commands.events;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;

/**
 * A singleton class that tracks a single history of undoable events.
 */
public class EventHistory {

    private static final Logger logger = LogsCenter.getLogger(EventHistory.class);
    private Stack<Event> undoStack;
    private Stack<Event> redoStack;
    private static EventHistory eventHistory;

    /**
     * Initializes both undo and redo history if no undo history exists.
     */
    private EventHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Returns an EventHistory object that tracks the history of undoable events.
     *
     * @return an instance of EventHistory that can be used to access the undo and redo history.
     */
    public static EventHistory getInstance() {
        if (eventHistory == null) {
            eventHistory = new EventHistory();
        }
        return eventHistory;
    }

    /**
     * Stores a command as an event in the EventHistory.
     *
     * @param command an undoable command to be stored in history
     */
    public void addCommandToUndoStack(UndoableCommand command) {
        Event event;
        try {
            event = EventFactory.commandToEvent(command);
        } catch (CommandException e) {
            logger.info(e.getMessage());
            return;
        }
        undoStack.add(event);
        redoStack.clear();
    }

    /**
     * Returns the next event to undo.
     *
     * @param model {@code Model} which the command should operate on.
     * @return undoable event
     */
    public Event undo(Model model) {
        assert(!undoStack.isEmpty());
        Event actionToUndo = undoStack.pop();
        actionToUndo.undo(model);
        redoStack.push(actionToUndo);
        return actionToUndo;
    }

    /**
     * Returns the next event to redo.
     *
     * @param model {@code Model} which the command should operate on.
     * @return undoable event
     */
    public Event redo(Model model) {
        assert(!redoStack.isEmpty());
        Event actionToRedo = redoStack.pop();
        actionToRedo.redo(model);
        undoStack.push(actionToRedo);
        return actionToRedo;
    }

    /**
     * Checks if the undo history is empty.
     *
     * @return true if undo stack is empty, false otherwise
     */
    public boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    /**
     * Checks if the redo history is empty.
     *
     * @return true if redo stack is empty, false otherwise
     */
    public boolean isRedoStackEmpty() {
        return redoStack.isEmpty();
    }
}
