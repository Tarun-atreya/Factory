import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Collections;

public class FactorySimulator {
    private ArrayList<Worker> workers;
    private Queue<Integer> orders;
    private ArrayList<Integer> cogs;
    private PriorityQueue<Worker> available;
    private static ArrayList<String> workerNames;
    private static int runcounter;

    public FactorySimulator(ArrayList<Worker> workers, ArrayList<Integer> c) {
        this.workers = workers;
        this.cogs = c;
        runcounter = 0;
        reset();
        available = new PriorityQueue<Worker>();
        for (Worker w : workers) {
            available.add(w);
        }
    }

    public void reset() {
        orders = new LinkedList<Integer>();
        for (Integer i : cogs)
            orders.add(i);
    }

    public void run() {
        int hours = 0;

        while (!(orders.isEmpty() && done())) {
            assign();
            work();
            hours++;
            ready();
            printWorkerStats();
        }
    }

    public void ready() {
        for (Worker w : workers) {
            if (!w.isBusy())
                available.add(w);
        }
    }

    // public void assign() {
    // PriorityQueue<Integer> temp = new
    // PriorityQueue<Integer>(Collections.reverseOrder());
    // System.out.println(orders.size());
    // temp = Algo();
    // try {
    // while (available.size() > 0) {
    // Worker w = available.poll();
    // w.assign(temp.poll());
    // System.out.println(w.getName() + " " + w.getInProcess() + " " + w.getcph());
    // }
    // } catch (Exception e) {
    // }
    // }

    // private PriorityQueue<Integer> Algo() {
    // PriorityQueue<Integer> temp = new
    // PriorityQueue<Integer>(Collections.reverseOrder());
    // if (orders.size() > available.size()) {
    // for (int i = 0; i < available.size(); i++) {
    // temp.add(orders.poll());
    // }
    // } else {
    // for (int i = 0; i < orders.size(); i++) {
    // temp.add(orders.poll());
    // }
    // }
    // return temp;
    // }

    public void assign() {
        PriorityQueue<Integer> newOrders = new PriorityQueue<Integer>(Collections.reverseOrder());
        if (orders.size() > available.size()) {
            for (int i = 0; i < available.size(); i++) {
                newOrders.add(orders.poll());
            }
        } else {
            for (int i = 0; i < orders.size(); i++) {
                newOrders.add(orders.poll());
            }
        }
        Worker[] temp = new Worker[available.size()];
        temp = available.toArray(temp);
        int pos = 0;
        int leftover = 0;
        while (!newOrders.isEmpty()) {
            for (int i = 0; i < available.size(); i++) {
                if (temp[i].getcph() < newOrders.peek()) {
                    if (newOrders.peek() % temp[i].getcph() < leftover) {
                        leftover = newOrders.peek() % temp[i].getcph();
                        pos = i;
                    }
                }
            }
            if (orders.isEmpty()) {
                break;
            }
            temp[pos].assign(orders.poll());
        }

    }

    public void work() {
        for (Worker w : workers) {
            w.work1hr();
            runcounter++;
        }
    }

    public void printWorkerStats() {
        File file = new File("output" + ".txt");
        int totalwaste = 0;
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (Worker worker : workers) {
                totalwaste += worker.getWaste();
                fileWriter.write("Hour: " + runcounter + "\n");
                fileWriter.write("Worker: " + worker.getName() + "\n");
                fileWriter.write("CPH: " + worker.getcph() + "\n");
                fileWriter.write("Total Cogs Produced: " + worker.getTotal() + "\n");
                fileWriter.write("Wasted Productivity: " + worker.getWaste() + "\n");
                fileWriter.write("Total/Waste : " + ((float) worker.getTotal() / (float) worker.getWaste()) + "\n");
                fileWriter.write("Total Waste: " + totalwaste / workers.size() + "\n");
                fileWriter.write("\n");
                fileWriter.flush();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public boolean done() {
        for (Worker w : workers) {
            if (w.isBusy()) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<String> loadNames() {
        ArrayList<String> names = new ArrayList<String>();
        File file = new File("names.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                names.add(sc.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return names;
    }
}