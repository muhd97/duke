import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    private List<Task> tasks;

    private void print(String s) {
        System.out.println(s);
    }

    private void printLine() {
        print("____________________________________________________________");
    }

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
            throw new DukeInvalidCommandException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        tasks.add(task);
        print("Got it. I've added this task:");
        print("  " + task);
        print("Now you have " + tasks.size() + " task(s) in the list.");
        printLine();
        return tasks;
    }

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
