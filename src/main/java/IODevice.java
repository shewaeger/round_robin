public class IODevice {
    private int maxTick;
    private int currentTick;

    public IODevice(int maxTick){
        this.maxTick = maxTick;
        this.currentTick = 0;
    }

    public int getMaxTick() {
        return maxTick;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void performQuery(){
        if(this.currentTick == maxTick)
            this.currentTick = 0;
        currentTick++;
    }

    public boolean isBlocked(){
        return this.currentTick == this.maxTick;
    }
}
