import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    private static final TaskList list = new TaskList();

    private static String[] parseInput(String[] arr) throws DukeException {
        String command = arr[0];
        if (arr.length == 1) {
            //user did not specify desc
            throw new DukeException("OOPS!! The description of a " + command + " cannot be empty :(");
        }
        String desc = "";
        String time = "";
        boolean slash = false;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].length() == 0) {
                continue;
            } else if (arr[i].charAt(0) == '/') {
                slash = true;
                continue;
            }
            if (slash) {
                time += arr[i] + " ";
            } else {
                desc += arr[i] + " ";
            }
        }
        if (slash) {
            return new String[] {desc.strip(), time.strip()};
        } else {
            return new String[] {desc.strip()};
        }
    }

    private static boolean isAddingNewTask(String str) {
        return str.startsWith("todo") || str.startsWith("deadline") || str.startsWith("event");
    }

    private static boolean isEditingTask(String str) {
        return str.startsWith("delete") || str.startsWith("done");
    }

    private static void addTask(String command, String[] args) throws DukeException {
        try {
            switch (command) {
                case "todo":
                    list.add(new Todo(args[0]));
                    break;
                case "deadline":
                    list.add(new Deadline(args[0], args[1]));
                    break;
                case "event":
                    list.add(new Event(args[0], args[1]));
                    break;
                default:
                    break;
            }
        } catch (DukeException e) {
           e.print();  
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _\n"
                + "|  _ \\ _   _| | _____\n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        System.out.println("Hello! I'm Duke\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().strip();

            String[] split = input.split(" ");

            if (input.equals("bye")) {
                //exit the program
                break;
            } else if (input.equals("list")) {
                //print list of tasks
                System.out.println(list.toString());
            } else if (isEditingTask(input)) {
                //mark task as done
                try {
                    list.editTask(split);
                } catch (DukeException e) {
                    e.print();
                }
            } else if (isAddingNewTask(input)) {
                //user is adding a new task
                try {
                    String command = split[0];
                    String[] taskArgs = parseInput(split);
                    addTask(command, taskArgs);
                } catch (DukeException e) {
                    e.print();
                }
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :(");
            }
        }
        sc.close();
        System.out.println("Byebye");
    }
}
