package duke.commands;

import duke.DukeException;
import duke.TaskList;
import duke.Todo;

/**
 * Class that represents the command to add a todo task.
 */
public class TodoCommand extends Command {
    private String description;
    private Todo newTask;

    /**
     * Constructor for TodoCommand.
     * @param input The user input.
     * @throws DukeException If the user input is in an invalid format.
     */
    public TodoCommand(String input) throws DukeException {
        description = getDescription(input);
        newTask = new Todo(description);
    }

    private String getDescription(String input) throws DukeException {
        int inputLen = input.length();
        if (inputLen < 2) {
            throw new DukeException("OOPS!! The description of a todo"
                    + " cannot be empty :(");
        } else {
            return input.substring(5).strip();
        }
    }

    /**
     * Executes the command.
     * @param taskList The list of tasks to add the todo task to.
     * @return Response to be displayed in the GUI.
     */
    @Override
    public String execute(TaskList taskList) {
        taskList.add(newTask);
        return "Just added:\n" + newTask.toString()
                + "\nYou currently have " + taskList.getLength() + " tasks in the list.";
    }
}