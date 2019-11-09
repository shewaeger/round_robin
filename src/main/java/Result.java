import java.util.LinkedList;
import java.util.List;

public class Result {

    private LinkedList<Process> processes;
    private int cpuTime;

    public Result(){
        this.processes = new LinkedList<>();
    }

    public void pushProcess(Process process) {
        processes.add(process);
    }

    public LinkedList<Process> getProcesses() {
        return processes;
    }

    public int getCpuTime() {
        return cpuTime;
    }
}
