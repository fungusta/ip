package duke;

import duke.command.*;

public class Parser {
    public static Command parseInput(String input) throws DukeException {
        if (!input.contains(" ")) {
            try {
                switch (Instructions.valueOf(input)) {
                case bye:
                    return ExitCommand.of();
                case list:
                    return ListCommand.of();
                case mark:
                case unmark:
                    throw new DukeException(String.format("Choose which index to %s.", input));
                case todo:
                case deadline:
                case event:
                    throw new DukeException(String.format("The description of a %s cannot be empty.", input));
                case delete:
                    throw new DukeException("Choose which index to delete.");
                case find:
                    throw new DukeException("Input a keyword to find.");
                }
            } catch (IllegalArgumentException e) {
                throw new DukeException("Sorry I do not understand what that means :(");
            }
        }
        String[] split = input.split(" ", 2);
        String instruction = split[0];
        String info = split[1];
        switch (Instructions.valueOf(instruction)) {
        case delete:
            try {
                return new DeleteCommand(Integer.parseInt(info) - 1);
            } catch (NumberFormatException e) {
                throw new DukeException("Deleting requires an integer as index");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException(String.format("Index %s does not exist on the list.", info));
            }
        case mark:
            try {
                return new MarkCommand(Integer.parseInt(info) - 1);
            } catch (NumberFormatException e) {
                throw new DukeException("Marking requires an integer as index");
            }
        case unmark:
            try {
                return new UnMarkCommand(Integer.parseInt(info) - 1);
            } catch (NumberFormatException e) {
                throw new DukeException("Unmarking requires an integer as index");
            }
        case todo:
            return new AddUserCommand(info);
        case deadline:
            if (info.contains(" /by ")) {
                String[] taskAndDeadline = info.split(" /by ", 2);
                String task1 = taskAndDeadline[0];
                String timing1 = taskAndDeadline[1];
                return new AddUserCommand(task1, Instructions.deadline, timing1);
            } else {
                throw new DukeException("Deadline does not have proper format.");
            }
        case event:
            if (info.contains(" /at ")) {
                String[] taskAndTiming = info.split(" /at ", 2);
                String task2 = taskAndTiming[0];
                String timing2 = taskAndTiming[1];
                return new AddUserCommand(task2, Instructions.event, timing2);
            } else {
                throw new DukeException("Event does not have proper format.");
            }
        case find:
            return new FindCommand(info);
        }
        throw new DukeException("Unknown Error");
    }

    public static Command parseSavedInput(String input) throws DukeException {
        String[] inputSplit = input.split(" ", 2);
        String instruction = inputSplit[0];
        String information = inputSplit[1];
        String[] temp = information.split(" ", 2);
        boolean done = temp[0].equals("1");
        String task = temp[1];
        switch (Instructions.valueOf(instruction)) {
        case todo:
            return new AddSavedInputCommand(task, done);
        case deadline:
            String[] taskAndBy = task.split(" ", 2);
            String task1 = taskAndBy[0];
            String deadline = taskAndBy[1];
            return new AddSavedInputCommand(task1, Instructions.deadline, deadline, done);
        case event:
            String[] taskAndAt = temp[1].split(" ", 2);
            String task2 = taskAndAt[0];
            String timing = taskAndAt[1];
            return new AddSavedInputCommand(task2, Instructions.event, timing, done);
        }
        throw new DukeException("Saved file input format incorrect");
    }

}
