import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Driver {
    public static ArrayList<String> workerNames = new ArrayList<String>();

    public static void main(String[] args) {
        File file = new File("output" + ".txt");
        file.delete();
        workerNames = FactorySimulator.loadNames();
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Integer> cogs = new ArrayList<Integer>();
        try{
            file.createNewFile();
            //write to file
            for(int i = 1; i <= 10000; i++)
            {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                fw.write("Simulation " + i + "\n");
                fw.flush();
                fw.close();
                workerNames = FactorySimulator.loadNames();
                cogs = getOrders();
                workers = getWorkers();
                FactorySimulator fs = new FactorySimulator(workers, cogs);
                fs.run();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
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
}
