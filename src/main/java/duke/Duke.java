package duke;

import duke.commands.Command;

/**
 * The Duke class that runs the Duke program.
 */
public class Duke {
    private static TaskList list;
    private static FileManager fm;

    /**
     * Constructor for Duke.
     */
    public Duke() {
        fm = new FileManager();
        try {
            list = fm.getListFromFile();
        } catch (DukeException e) {
            list = new TaskList();
        }
    }

    public boolean isError(String input) {
        try {
            Parser parser = new Parser(input);
            Command command = parser.parse();
            String response = command.execute(list);
            return false;
        } catch (DukeException e) {
            return true;
        }
    }

    /**
     * A method that gets the response from Duke to be displayed in the GUI.
     *
     * @param input User input to be responded to.
     * @return Response to be displayed.
     */
    public String getResponse(String input) {
        try {
            Parser parser = new Parser(input);
            assert list instanceof TaskList : "Task list cannot be found";
            Command command = parser.parse();
            String response = command.execute(list);
            fm.writeToFile(list);
            return response;
        } catch (DukeException e) {
            return e.getMessage();
        }
    }
}
