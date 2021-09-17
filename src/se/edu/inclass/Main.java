package se.edu.inclass;

import se.edu.inclass.data.DataManager;
import se.edu.inclass.task.Deadline;
import se.edu.inclass.task.Task;
import se.edu.inclass.task.TaskNameComparator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    private TaskNameComparator taskNameComparator;

    public static void main(String[] args) {
        DataManager dm = new DataManager("./data/data.txt");
        ArrayList<Task> tasksData = dm.loadData();

//        System.out.println("Printing deadlines");
//        printDeadlines(tasksData);
        printDeadlinesUsingStreams(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));
        printDeadlinesUsingStreams(tasksData);
        ArrayList<Task> filteredList = filterTasksByString(tasksData, "11");
        printData(filteredList);
        System.out.println("Total number of deadlines (using streams): " + countDeadlinesUsingStreams(tasksData));
//         printData(tasksData);
        printDataWithStreams(tasksData);
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesUsingStreams(ArrayList<Task> taskData) {
        return (int) taskData.stream()
                .filter((t) -> t instanceof Deadline) //filtering using lambda
                .count();
    }

    public static void printData(ArrayList<Task> tasksData) {
        System.out.println("Printing data by looping");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataWithStreams(ArrayList<Task> taskData) {
        System.out.println("Printing data using streams");
        taskData.stream() //convert data to stream
                .forEach(System.out::println); //terminal operator
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStreams(ArrayList<Task> tasksData) {
        tasksData.stream()
                .filter((t) -> t instanceof Deadline)
                .sorted((a, b) -> a.getDescription().toLowerCase().compareTo(b.getDescription().toLowerCase()))
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasksData, String filterString) {
        return (ArrayList<Task>) tasksData.stream()
                .filter((t) -> t.getDescription().contains(filterString))
                .collect(Collectors.toList());
    }
}
