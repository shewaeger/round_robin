import java.util.Queue;

public interface Algorithm {
    Result run(Logger logger, Queue<Process> processes);
}
