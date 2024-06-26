package managers;

import exceptions.TimeIntersectException;
import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Subtask> getSubtasks();

    HashMap<Integer, Epic> getEpics();

    void clearTasks();

    void clearEpics();

    void clearSubtasks();

    void addObjective(AbstractTask abstractTask, TaskType taskType);

    AbstractTask getById(int id, TaskType type);

    void update(AbstractTask abstractTask, int id);

    void deleteById(int id, TaskType taskType);

    ArrayList<Subtask> getSubtasksByEpic(int epicId);
    List<AbstractTask> getHistory();

    void addToPrioritizedTasksList(AbstractTask task) throws TimeIntersectException;

    ArrayList<AbstractTask> getPrioritizedTasks();
    void removeFromPrioritizedList(int id);
}
