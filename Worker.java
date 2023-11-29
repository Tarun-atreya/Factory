public class Worker implements Comparable<Worker>{
    private String name;
    private int cph;
    private int cogs;
    private int waste; 
    private int inprocess;

    public Worker(String name, int cmp)
    {
        this.name = name;
        this.cph = cmp;
        cogs = 0;
        waste = 0; 
        inprocess = 0;
    }
    public void work1hr()
    {
        int possible = cph;
        while (possible > 0)
        {
            if (isBusy())
            {
                inprocess--;
                cogs++;
            }
            else
            {
                waste++;
            }
            possible--;
        }
    }
    public boolean isBusy()
    {
        return inprocess > 0;
    }
    public void assign(int cogs)
    {
        inprocess = cogs;
    }
    public void reset()
    {
        cogs = 0;
        waste = 0; 
        inprocess = 0;
    }
    public String getName()
    {
        return name;
    }
    public int getcph()
    {
        return cph;
    }
    public int getTotal()
    {
        return cogs;
    }
    public int getWaste()
    {
        return waste;
    }
    public int getInProcess()
    {
        return inprocess;
    }
    public String toString()
    {
        return name + ":\n" + "Total: " + cogs + "\nWaste: " + waste + "\nRate: " + cph + "\nIn Process: " + inprocess;
    }
    @Override
    public int compareTo(Worker arg0) {
        if(this.cph > arg0.cph)
        {
            return 1;
        }
        else if(this.cph < arg0.cph)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}