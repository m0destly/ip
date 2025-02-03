package darwin;

import exception.DarwinException;
import task.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the saved tasks and returns an ArrayList of tasks.
     *
     * @return ArrayList of tasks.
     * @throws DarwinException If there is an error in the loading process.
     */
    public ArrayList<Task> load() throws DarwinException {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Task> tasks = (ArrayList<Task>) ois.readObject();
            ois.close();
            System.out.println(tasks.size() + (tasks.size() > 1 ? " tasks loaded." : " task loaded."));
            return tasks;
        } catch (FileNotFoundException e) {
            throw new DarwinException("No saved tasks found.");
        } catch (ClassNotFoundException e) {
            throw new DarwinException(e.getMessage());
        } catch (IOException e) {
            throw new DarwinException("An error occurred loading saved tasks.");
        }
    }

    /**
     * Saves the current tasks in the tasklist.
     *
     * @param taskList Tasklist containing the tasks.
     * @throws DarwinException If there is an error saving the tasks.
     */
    public void save(TaskList taskList) throws DarwinException {
        ArrayList<Task> tasks = taskList.getTasks();
        String dirs = "";
        for (int i = filePath.length() - 1; i >= 0; i--) {
            if (filePath.charAt(i) == '/') {
                dirs = filePath.substring(0, i);
            }
        }
        if (!dirs.isEmpty()) {
            if (new File(dirs).mkdirs()) {
                System.out.println("New directory created.");
            }
        }
        if (tasks.isEmpty()) {
            System.out.println("No tasks to save.");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tasks);
            oos.close();
            System.out.println(tasks.size() + (tasks.size() > 1 ? " tasks saved." : " task saved."));
        } catch (IOException e) {
            throw new DarwinException("An error occurred saving current tasks.");
        }
    }
}
