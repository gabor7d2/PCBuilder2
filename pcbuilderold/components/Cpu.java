package net.gabor7d2.pcbuilderold.components;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Cpu extends Component {

    private final String socket;
    private final String generation;
    private final boolean unlocked;
    private final int cores;
    private final int threads;
    private final int baseFrequencyMhz;
    private final int turboFrequencyMhz;

    private final double[] cachesMB;

    private final int ramMaxMB;
    private final int tdpW;

    //private final IGpu IGpu;

    @JsonCreator
    public Cpu(String brand, String modelNumber/*, String imagePath*/, String productSite, String priceSite, String shopSite, double price,
               String socket, String generation, boolean unlocked, int cores, int threads, int baseFrequencyMhz, int turboFrequencyMhz,
               double[] cachesMB, int ramMaxMB, int tdpW/*, IGpu IGpu*/) {
        super(brand, modelNumber/*, imagePath*/, productSite, priceSite, shopSite, price);
        this.socket = socket;
        this.generation = generation;
        this.unlocked = unlocked;
        this.cores = cores;
        this.threads = threads;
        this.baseFrequencyMhz = baseFrequencyMhz;
        this.turboFrequencyMhz = turboFrequencyMhz;
        this.cachesMB = cachesMB;
        this.ramMaxMB = ramMaxMB;
        this.tdpW = tdpW;
        //this.IGpu = IGpu;
    }

    public String getSocket() {
        return socket;
    }

    public String getGeneration() {
        return generation;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getCores() {
        return cores;
    }

    public int getThreads() {
        return threads;
    }

    public int getBaseFrequencyMhz() {
        return baseFrequencyMhz;
    }

    public int getTurboFrequencyMhz() {
        return turboFrequencyMhz;
    }

    public double[] getCachesMB() {
        return cachesMB;
    }

    public int getRamMaxMB() {
        return ramMaxMB;
    }

    public int getTdpW() {
        return tdpW;
    }

    /*public IGpu getIGpu() {
        return IGpu;
    }*/

    @Override
    public String toString() {
        return "Cpu " + getBrand() + " " + getModelNumber();
    }
}
