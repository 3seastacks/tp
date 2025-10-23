package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Consolidate all the student's phone number.
 */
public class ConsolidateCommand extends Command {

    public static final String COMMAND_WORD = "consolidate:phone";

    public static final String MESSAGE_SUCCESS = "Consolidate all distinct students' phone:";

    public static final String ALTERNATE_MESSAGE_SUCCESS = "Nothing to consolidate so far, "
            + "since no student's data has been entered and stored in CCAmper.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": consolidate all distinct students' phone numbers currently in address book. \n"
            + "If two students have same phone number, phone number only appears once.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        HashSet<String> container = new HashSet<>();
        int counter = 0;

        for (Person person : model.getAddressBook().getPersonList()) {
            container.add(person.getPhone().toString());
            counter = counter + 1;
        }

        assert counter >= 0 : "The number of contacts cannot be a negative value.";
        if (counter == 0) {
            return new CommandResult(ALTERNATE_MESSAGE_SUCCESS);
        }

        ArrayList<String> list = new ArrayList<>(container);
        Collections.sort(list);
        String compilation = "";

        for (String s : list) {
            compilation += "\n" + s;
        }

        return new CommandResult(MESSAGE_SUCCESS + compilation);
    }
}
