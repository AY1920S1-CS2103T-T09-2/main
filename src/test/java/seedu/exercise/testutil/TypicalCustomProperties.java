package seedu.exercise.testutil;

import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_ENDDATE;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_ENDDATE;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_SHORT_NAME_ENDDATE;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_SHORT_NAME_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_SHORT_NAME_REMARK;

import seedu.exercise.model.exercise.CustomProperty;

/**
 * A utility class containing a list of {@code CustomProperty} objects to be used in tests.
 */
public class TypicalCustomProperties {

    // Manually added
    public static final CustomProperty PRIORITY = new CustomPropertyBuilder().withPrefix("d")
            .withFullName("Priority").withParameterType("Number").build();
    public static final CustomProperty INSTRUCTIONS = new CustomPropertyBuilder().withPrefix("e")
            .withFullName("Instructions").withParameterType("Text").build();
    public static final CustomProperty EXPECTEDRECOVERY = new CustomPropertyBuilder().withPrefix("f")
            .withFullName("Recovery").withParameterType("Date").build();

    // Manually added - Custom Property's details found in {@code CommandTestUtil}
    public static final CustomProperty RATING = new CustomPropertyBuilder().withPrefix(VALID_SHORT_NAME_RATING)
            .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
    public static final CustomProperty REMARK = new CustomPropertyBuilder().withPrefix(VALID_SHORT_NAME_REMARK)
            .withFullName(VALID_FULL_NAME_REMARK).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
    public static final CustomProperty ENDDATE = new CustomPropertyBuilder().withPrefix(VALID_SHORT_NAME_ENDDATE)
            .withFullName(VALID_FULL_NAME_ENDDATE).withParameterType(VALID_PARAMETER_TYPE_ENDDATE).build();

}
