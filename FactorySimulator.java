import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class FactorySimulator {
    private ArrayList<Worker> workers;
    private Queue<Integer> orders;
    private ArrayList<Integer> cogs;
    private PriorityQueue<Worker> available;
    private static ArrayList<String> workerNames;

    public FactorySimulator(ArrayList<Worker> workers, ArrayList<Integer> c) {
        // namepool
        this.workers = workers;
        this.cogs = c;
        reset();
        available = new PriorityQueue<Worker>();
        for (Worker w : workers) {
            available.add(w);
        }
    }

    public void reset() {
        orders = new LinkedList<Integer>();
        for (Integer i : cogs) {
            orders.add(i);
        }
    }

    public void run() {
        int hours = 0;

        while (!(orders.isEmpty() && done())) {
            try {
                for (Worker w : available) {
                    w.assign(orders.poll());
                }
            } catch (Exception e) {
            }
            for (Worker w : workers) {
                w.work1hr();
            }
            hours++;
            for (Worker w : workers) {
                if (!w.isBusy()) {
                    available.add(w);
                }
            }
            printWorkerStats();
        }

    }

    public void printWorkerStats() {
        File file = new File("output" + ".txt");
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (Worker worker : workers) {
                fileWriter.write("Worker: " + worker.getName() + "\n");
                fileWriter.write("CPH: " + worker.getcph() + "\n");
                fileWriter.write("Total Cogs Produced: " + worker.getTotal() + "\n");
                fileWriter.write("Wasted Productivity: " + worker.getWaste() + "\n");
                fileWriter.write("Total/Waste : " + ((float) worker.getTotal() / (float) worker.getWaste()) + "\n");
                fileWriter.write("\n");
                fileWriter.flush();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static ArrayList<Worker> getWorkers() {
        ArrayList<Worker> workers = new ArrayList<Worker>();
        int temp = (int) (Math.random() * 7) + 3;
        for (int i = 0; i < temp; i++) {
            int cph = (int) (Math.random() * 41) + 15;
            Worker some = new Worker(getName(), cph);
            workers.add(some);
        }
        return workers;
    }

    public static ArrayList<Integer> getOrders() {
        ArrayList<Integer> orders = new ArrayList<Integer>();
        for (int i = 0; i < 97; i++) {
            orders.add((int) (Math.random() * 81) + 20);
        }
        for (int i = 0; i < 3; i++) {
            orders.add((int) (Math.random() * 11) + 100);
        }
        return orders;
    }

    public static String getName() {
        String temp = workerNames.get((int) (Math.random() * workerNames.size()));
        workerNames.remove(temp);
        return temp;
    }

    public boolean done() {
        for (Worker w : workers) {
            if (w.isBusy()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        workerNames = new ArrayList<String>();
        workerNames = loadNames();
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Integer> cogs = new ArrayList<Integer>();
        cogs = getOrders();
        workers = getWorkers();
        FactorySimulator fs = new FactorySimulator(workers, cogs);
        fs.run();

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
