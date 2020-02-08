import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskList class defines the task management behaviors.
 */
public class TaskList {

    /**
     * Constructs a TaskList instance.
     *
     * @param tasks The List of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    private List<Task> tasks;

    private void print(String s) {
        System.out.println(s);
    }

    private void printLine() {
        print("____________________________________________________");
    }

    /**
     * Deletes the task from the tasks list,
     * then return the updated list.
     *
     * @param command The string containing the index of the task in the list to be deleted.
     * @return The updated task list after the task deletion.
     * @throws DukeInvalidTaskException If the task doesn't exist in the list.
     */
    public List<Task> deleteTask(String command) throws DukeInvalidTaskException {
        int i = Integer.parseInt(command.split(" ")[1]) - 1;
        if (i >= tasks.size() || i < 0) {
            throw new DukeInvalidTaskException("There is no such task in the list!");
        } else {
            print("Noted. I've removed this task: ");
            print("  " + tasks.get(i));
            tasks.remove(tasks.get(i));
            print("Now you have " + tasks.size() + " task(s) in the list.");
            printLine();
            return tasks;
        }
    }

    /**
     * Finds the tasks in the tasks list matching the entered keyword,
     * then return the list containing those tasks.
     *
     * @param keyword The string containing the description of the task in the list to be found.
     * @return The list of tasks with the matching keyword description.
     * @throws DukeInvalidTaskException If the task doesn't exist in the list.
     */
    public List<Task> findTask(String keyword) throws DukeInvalidTaskException {
        List<Task> temp = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                temp.add(task);
            }
        }
        if (temp.size() == 0) {
            throw new DukeInvalidTaskException("There are no matching tasks in your list");
        }
        return temp;
    }

    /**
     * Adds the task with the corresponding type,
     * and the description provided by the client.
     *
     * @param command The string containing the description of the task to be added in the list.
     * @return The updated task list after the addition of the given task.
     * @throws DateTimeParseException      If the date provided in the task description is in an invalid format.
     * @throws DukeInvalidCommandException If the description of the command entered is invalid.
     */
    public List<Task> addTask(String command) throws DateTimeParseException, DukeInvalidCommandException {
        Task task = null;
        String taskType = command.split(" ")[0];
        command = command.substring(command.indexOf(" "));
        switch (taskType) {
        case "todo":
            task = new ToDo(command.trim());
            break;
        case "event":
            task = new Event(command.split("/at ")[0].trim(),
                    LocalDate.parse(command.split("/at ")[1].trim().split(" ")[0]),
                    command.split("/at ")[1].split(" ")[1]);
            break;
        case "deadline":
            task = new Deadline(command.split("/by ")[0].trim(),
                    LocalDate.parse(command.split("/by ")[1].trim().split(" ")[0]),
                    command.split("/by ")[1].split(" ")[1].trim());
            break;
        default:
            throw new DukeInvalidCommandException(":( OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        assert task != null : "task can't be null"; //redundant?
        tasks.add(task);
        print("Got it. I've added this task:");
        print("  " + task);
        print("Now you have " + tasks.size() + " task(s) in the list.");
        printLine();
        return tasks;
    }

    /**
     * Marks the task as done in the tasks list,
     * then return the updated list.
     *
     * @param command The string containing the index of the task in the list to be marked as done.
     * @return The updated task list after marking the task as done.
     * @throws DukeInvalidTaskException If the task doesn't exist in the list.
     */
    public List<Task> markAsDone(String command) throws DukeInvalidTaskException {
        int i = Integer.parseInt(command.split(" ")[1]) - 1;
        if (i >= tasks.size() || i < 0) {
            throw new DukeInvalidTaskException("There is no such task in the list!");
        } else {
            tasks.get(i).markAsDone();
            print("Nice! I've marked this task as done: ");
            print(tasks.get(i).toString());
            printLine();
            return tasks;
        }
    }

}
