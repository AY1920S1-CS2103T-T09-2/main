package seedu.exercise.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;
import static seedu.exercise.logic.parser.CliSyntax.getPropertyPrefixesSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.EditCommand.EditExerciseDescriptor;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.Muscle;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private final Logger logger = LogsCenter.getLogger(EditCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Prefix[] commandPrefixes = getPrefixes();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, commandPrefixes);

        if (!argMultimap.arePrefixesPresent(PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()){
            logger.info("Prefix not present or preamble not empty");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

        EditCommand.EditExerciseDescriptor editExerciseDescriptor = new EditExerciseDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editExerciseDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editExerciseDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
            editExerciseDescriptor.setCalories(ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editExerciseDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_UNIT).isPresent()) {
            editExerciseDescriptor.setUnit(ParserUtil.parseUnit(argMultimap.getValue(PREFIX_UNIT).get()));
        }

        parseMusclesForEdit(argMultimap.getAllValues(PREFIX_MUSCLE)).ifPresent(editExerciseDescriptor::setMuscles);


        parseCustomPropertiesForEdit(argMultimap.getAllCustomProperties())
            .ifPresent(editExerciseDescriptor::setCustomProperties);


        if (!editExerciseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editExerciseDescriptor);
    }

    /**
     * Parses {@code Collection<String> muscles} into a {@code Set<Muscle>} if {@code muscles} is non-empty.
     * If {@code muscles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Muscle>} containing zero muscles.
     */
    private Optional<Set<Muscle>> parseMusclesForEdit(Collection<String> muscles) throws ParseException {
        requireNonNull(muscles);

        if (muscles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = muscles.size() == 1 && muscles.contains("") ? Collections.emptySet() : muscles;
        return Optional.of(ParserUtil.parseMuscles(tagSet));
    }

    /**
     * Parses {@code Map<String, String> customProperties} into a {@code Map<String, String>} if
     * {@code customProperties} is non-empty.
     * If {@code customProperties} is empty, a {@code Optional.empty()} is returned instead.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private Optional<Map<String, String>> parseCustomPropertiesForEdit(Map<String, String> customProperties)
        throws ParseException {
        requireNonNull(customProperties);

        if (customProperties.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseCustomProperties(customProperties));
    }

    /**
     * Returns an array of prefixes to parse for.
     */
    private Prefix[] getPrefixes() {
        Set<Prefix> prefixes = new HashSet<>();
        prefixes.addAll(List.of(PREFIX_INDEX, PREFIX_NAME, PREFIX_DATE,
                PREFIX_CALORIES, PREFIX_QUANTITY, PREFIX_UNIT));

        prefixes.addAll(Arrays.asList(getPropertyPrefixesSet()));
        return prefixes.toArray(new Prefix[prefixes.size()]);
    }


}
