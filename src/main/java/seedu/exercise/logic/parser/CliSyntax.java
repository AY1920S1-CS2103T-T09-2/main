package seedu.exercise.logic.parser;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains Command Line Interface (CLI) syntax definitions and methods common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for default commands and properties */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_CALORIES = new Prefix("c/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_MUSCLE = new Prefix("m/");
    public static final Prefix PREFIX_UNIT = new Prefix("u/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("t/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_CONFLICT_INDEX = new Prefix("r/");
    public static final Prefix PREFIX_CUSTOM_NAME = new Prefix("s/");
    public static final Prefix PREFIX_FULL_NAME = new Prefix("f/");
    public static final Prefix PREFIX_PARAMETER_TYPE = new Prefix("p/");
    public static final Prefix PREFIX_SUGGEST = new Prefix("g/");
    public static final Prefix PREFIX_CHART = new Prefix("h/");
    public static final Prefix PREFIX_STARTDATE = new Prefix("s/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("e/");

    /* A set consisting of property prefix definitions for add and edit commands */
    public static final Set<Prefix> PREFIXES_SET = new HashSet<>();

    public static void setPrefixesSet(Set<Prefix> prefixes) {
        PREFIXES_SET.addAll(prefixes);
    }

    /**
     * Returns an array that contains the prefixes in {@code PREFIXES_SET} and {@code otherPrefixes}.
     */
    public static Prefix[] combinePrefixes(Prefix... otherPrefixes) {
        int setSize = PREFIXES_SET.size();
        int numOfOtherPrefixes = otherPrefixes.length;
        Prefix[] prefixArray = PREFIXES_SET.toArray(new Prefix[setSize + numOfOtherPrefixes]);
        System.arraycopy(otherPrefixes, 0, prefixArray, setSize, numOfOtherPrefixes);
        return prefixArray;
    }
}
