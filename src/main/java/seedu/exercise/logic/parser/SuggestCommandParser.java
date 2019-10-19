package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CUSTOM_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST;
import static seedu.exercise.logic.parser.CliSyntax.combinePrefixes;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import seedu.exercise.logic.commands.SuggestBasicCommand;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;

/**
 * Parses input arguments and creates a new SuggestCommand object
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestCommand
     * and returns a SuggestCommand object for execution.
     *
     * @throws ParseException if the user does not conform to the expected format
     */
    public SuggestCommand parse(String args) throws ParseException {
        //add all the customProperties' prefixes into allPrefixes

        Prefix[] allPrefixes = combinePrefixes(PREFIX_SUGGEST);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUGGEST, PREFIX_MUSCLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUGGEST) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
        }

        String suggestType = ParserUtil.parseSuggestType(argMultimap.getValue(PREFIX_SUGGEST).get());

        if (suggestType.equals("basic")) {
            return new SuggestBasicCommand();
        }

        if (suggestType.equals("possible")) {
            return parsePossible(argMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static SuggestCommand parsePossible(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SuggestCommand.MESSAGE_USAGE));
        }
        Set<Muscle> muscles = ParserUtil.parseMuscles(argMultimap.getAllValues(PREFIX_MUSCLE));
        return new SuggestPossibleCommand(muscles);
    }

}
