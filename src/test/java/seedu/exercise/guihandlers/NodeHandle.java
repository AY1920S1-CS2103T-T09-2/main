package seedu.exercise.guihandlers;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import org.testfx.framework.junit5.ApplicationExtension;

import javafx.scene.Node;
import seedu.exercise.guihandlers.exceptions.NodeNotFoundException;

/**
 * Provides access to a node in a JavaFx application for GUI testing purposes.
 */
public abstract class NodeHandle<T extends Node> extends ApplicationExtension {
    protected final GuiRobot guiRobot = new GuiRobot();

    private final T rootNode;

    protected NodeHandle(T rootNode) {
        this.rootNode = requireNonNull(rootNode);
    }

    protected T getRootNode() {
        return rootNode;
    }

    /**
     * Retrieves the {@code query} node owned by the {@code rootNode}.
     *
     * @param query name of the CSS selector for the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <Q extends Node> Q getChildNode(String query) {
        Optional<Q> node = guiRobot.from(rootNode).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }

    /**
     * Clicks on the root node (i.e. itself).
     */
    public void click() {
        guiRobot.clickOn(rootNode);
    }
}
