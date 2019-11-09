import java.util.Queue;

public class RoundRobin implements Algorithm {
    private int cpuTime;
    private int timeSlot;
    private int currentTick;

    public RoundRobin(int timeSlot){
        cpuTime = 0;
        currentTick = 0;
        this.timeSlot = timeSlot;
    }

    @Override
    public Result run(Logger logger, Queue<Process> processes) {
        cpuTime = 0;
        currentTick = 0;
        Result result = new Result();

        while(processes.size() != 0){
            Process process = processes.element();

            if(currentTick == 0)
                logger.processStarted(process, cpuTime);

            process.tick();

            cpuTime++;
            currentTick++;

            if(process.isFinished()){
                logger.processFinish(process, cpuTime);
                result.pushProcess(processes.remove());
                currentTick = 0;
            }
            else if(process.isDeviceBlocked() || timeSlot == currentTick){

                if(process.isDeviceBlocked())
                    logger.deviceBlocked(process, cpuTime);

                processes.add(processes.remove());
                currentTick = 0;
            }
        }
        return result;
    }
}
