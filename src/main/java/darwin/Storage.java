package darwin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import exception.DarwinException;
import exception.ErrorMessage;
import task.Task;

/**
 * Class that handles the loading and saving of tasks.
 */
public class Storage {
    private final String filePath;

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
            assert !tasks.isEmpty() : "Tasks should exist";
            System.out.print(tasks.size());
            if (tasks.size() == 1) {
                System.out.println(" task loaded.");
            } else {
                System.out.println(" tasks loaded.");
            }
            return tasks;
        } catch (FileNotFoundException e) {
            throw new DarwinException(ErrorMessage.NO_SAVED_TASKS.getMessage());
        } catch (ClassNotFoundException e) {
            throw new DarwinException(e.getMessage());
        } catch (IOException e) {
            throw new DarwinException(ErrorMessage.LOAD_FAIL.getMessage());
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
        if (!dirs.isEmpty() && new File(dirs).mkdirs()) {
            System.out.println("New directory created.");
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
            System.out.print(tasks.size());
            if (tasks.size() == 1) {
                System.out.println(" task saved.");
            } else {
                System.out.println(" tasks saved.");
            }
        } catch (IOException e) {
            throw new DarwinException("An error occurred saving current tasks.");
        }
    }
}
