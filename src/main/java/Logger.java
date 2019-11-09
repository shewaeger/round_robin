import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<PrintStream> streams;

    public Logger(){
        streams = new ArrayList<>();
        streams.add(System.out);
    }

    public void addStream(OutputStream stream){
        PrintStream printStream = new PrintStream(stream);
        streams.add(printStream);
    }

    public void log(String message){
        for (PrintStream stream : streams) {
            stream.println(message);
        }
    }

    public void processFinish(Process process, int cpuTime){
        log(String.format("Process %s finished (%d %d %d %d)", process.getName(), cpuTime, process.getCurrentTime(), process.getCpuTime(), process.getIOBlocking()));
    }

    public void deviceBlocked(Process process, int cpuTime) {
        log(String.format("Process %s I/O blocked (%d %d %d %d)", process.getName(), cpuTime, process.getCurrentTime(), process.getCpuTime(), process.getIOBlocking()));
    }

    public void processStarted(Process process, int cpuTime){
        log(String.format("Process %s started (%d %d %d %d)", process.getName(), cpuTime, process.getCurrentTime(), process.getCpuTime(), process.getIOBlocking()));
    }
}
