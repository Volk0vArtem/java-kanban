import managers.TaskManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    protected void initTasks(){
        task = new Task("TaskName", "TaskDescription");
        epic = new Epic("EpicName", "EpicDescription");
        subtask = new Subtask("SubtaskName", "SubtaskDescription", epic);
        taskManager.addObjective(task, TaskType.TASK);
        taskManager.addObjective(epic, TaskType.EPIC);
        taskManager.addObjective(subtask, TaskType.SUBTASK);
    }


    @BeforeEach
    public void setUp(){

    }

    @Test
    void addNewEpic() {
        Epic newEpic = new Epic("name", "description");
        taskManager.addObjective(newEpic, TaskType.EPIC);
        assertEquals(newEpic, taskManager.getById(3));
    }

    @Test
    void addNewSubtask() {
        Subtask newSubtask = new Subtask("name", "description", epic);
        taskManager.addObjective(newSubtask, TaskType.SUBTASK);
        assertEquals(newSubtask, taskManager.getById(3));
    }

    @Test
    void addNewTask() {
        Task task = new Task("name", "id3");
        taskManager.addObjective(task, TaskType.TASK);
        assertEquals(task, taskManager.getById(3, TaskType.TASK));
    }

    @Test
    void deleteEpic() {
        taskManager.deleteById(1, TaskType.EPIC);
        assertEquals(0, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubtasks().size());
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void deleteSubtask() {
        taskManager.deleteById(2, TaskType.SUBTASK);
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubtasks().size());
        assertEquals(1, taskManager.getTasks().size());
    }



    @Test
    void deleteTask() {
        taskManager.deleteById(0, TaskType.TASK);
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(1, taskManager.getSubtasks().size());
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void epicWithDoneSubtasks() {
        taskManager.update(subtask.changeStatus(Status.DONE), 2);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void epicWithEmptySubtasks() {
        taskManager.addObjective(new Epic("newEpic", "emptySubtasks"), TaskType.EPIC);
        assertEquals(Status.NEW, taskManager.getById(3).getStatus());
    }

    @Test
    void epicWithInProgressNewSubtasks() {
        taskManager.addObjective(new Subtask("newSubtask", "new", epic), TaskType.SUBTASK);
        taskManager.update(subtask.changeStatus(Status.IN_PROGRESS), 2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void epicWithNewSubtasks() {
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    void getEpic() {
        assertEquals(epic, taskManager.getById(1));
    }

    @Test
    void getEpics() {
        Epic newEpic = new Epic("newEpic", "-");
        taskManager.addObjective(newEpic, TaskType.EPIC);
        HashMap<Integer, Epic> epics = new HashMap<>();
        epics.put(1,epic);
        epics.put(3, newEpic);
        assertTrue(epics.equals(taskManager.getEpics()));
    }

    @Test
    void getSubtasksByEpic() {
        Subtask newSubtask = new Subtask("newSubtask", "-", epic);
        taskManager.addObjective(newSubtask, TaskType.SUBTASK);
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask);
        subtasks.add(newSubtask);
        assertTrue(subtasks.equals(taskManager.getSubtasksByEpic(1)));
    }

    @Test
    void getHistory() {
        taskManager.getById(0, TaskType.TASK);
        taskManager.getById(1, TaskType.EPIC);
        taskManager.getById(2, TaskType.SUBTASK);

        ArrayList<AbstractTask> history = new ArrayList<>();
        history.add(subtask);
        history.add(epic);
        history.add(task);
        assertTrue(history.equals(taskManager.getHistory()));
    }



    // TODO: 19.01.2024 LocalDateTime getLastEndTime ,  void getPrioritizedTasks


    @Test
    void getSubtask() {
        assertEquals(subtask, taskManager.getById(2));
    }

    @Test
    void getSubtasks() {
        Subtask newSubtask = new Subtask("newSubtask", "-", epic);
        taskManager.addObjective(newSubtask, TaskType.SUBTASK);
        HashMap<Integer, Subtask> subtasks = new HashMap<>();
        subtasks.put(2, subtask);
        subtasks.put(3, newSubtask);
        assertTrue(subtasks.equals(taskManager.getSubtasks()));
    }

    @Test
    void getTask() {
        assertEquals(task, taskManager.getById(0));
    }

    @Test
    void getTasks() {
        Task newTask = new Task("newTask", "-");
        taskManager.addObjective(newTask, TaskType.TASK);
        HashMap<Integer, Task> tasks = new HashMap<>();
        tasks.put(0, task);
        tasks.put(3, newTask);
        assertTrue(tasks.equals(taskManager.getTasks()));
    }

    @Test
    void updateEpic() {
        Epic updatedEpic = new Epic("newEpic", "updatedEpic");
        taskManager.update(updatedEpic, 1);
        assertEquals(updatedEpic, taskManager.getById(1));
    }

    @Test
    void updateSubtask() {
        Subtask updatedSubtask = new Subtask("newSubtask", "updatedSubtask", epic);
        taskManager.addObjective(updatedSubtask, TaskType.SUBTASK);
        assertEquals(updatedSubtask, taskManager.getById(3));
    }

    @Test
    void updateTask() {
        Task updatedTask = new Task("newTask", "updatedTask");
        taskManager.addObjective(updatedTask, TaskType.TASK);
        assertEquals(updatedTask, taskManager.getById(3));
    }

}