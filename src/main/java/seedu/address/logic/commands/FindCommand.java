package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Finds and lists all persons in address book whose selected field contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose "
            + "fields satisfy certain constraints. Names or tags containing the specified keywords (case-insensitive),"
            + " or enrollment year satisfying the specified constraint, and"
            + " displays them as a list with index numbers.\n"
            + "Parameters: At most one of each of the following: "
            + "[" + PREFIX_NAME + "KEYWORD [MORE_KEYWORDS]...] "
            + "[" + PREFIX_TAG + "KEYWORD [MORE_KEYWORDS]...] "
            + "[" + PREFIX_ENROLL_YEAR + "[OP YEAR]]"
            + "\nExample: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
