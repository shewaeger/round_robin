public class Process {
    private String name;
    private int cpuTime;
    private IODevice device;
    private int currentTime;

    public Process(String name, int cpuTime, IODevice device){
        this.name = name;
        this.cpuTime = cpuTime;
        this.device = device;
        this.currentTime = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getCpuTime(){
        return this.cpuTime;
    }

    public int getCurrentTime(){
        return this.currentTime;
    }

    public int getIOBlocking(){
        return device.getMaxTick();
    }

    public void tick(){
        if(currentTime == cpuTime)
            throw new RuntimeException("Process already finished");
        device.performQuery();
        this.currentTime++;
    }

    public boolean isFinished(){
        return this.currentTime == cpuTime;
    }

    public boolean isDeviceBlocked(){
        return this.device.isBlocked();
    }
}
