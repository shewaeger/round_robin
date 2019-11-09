import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Schedule {


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        if (args.length != 1) {
            System.out.println("Usage: java Schedule <CONFIG_FILE>");
            return;
        }
        FileInputStream config;
        try {
            config = new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Configuration not found");
            return;
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        Configuration configuration = new Configuration();
        parser.parse(config, configuration);

        Algorithm roundRobin = new RoundRobin(configuration.getTimeSlot());

        // список процессов передается по ссылке, поэтому будет изменен при обработке. Что бы сохранить первоначальный порядок
        // необходимо создать новый список.
        Result result = roundRobin.run(configuration.getLogger(), new LinkedList<>(configuration.getProcesses()));

        Queue<Process> processes = configuration.getProcesses();

        Logger resultLogger = configuration.getResultLogger();

        resultLogger.log("Scheduling Name: Round Robin ");
        resultLogger.log("Simulation time (ticks): "+ result.getCpuTime());
        resultLogger.log("Before:");
        resultLogger.log("Name\tCPU Time\tIOBlocking");
        for (Process process : processes) {
            resultLogger.log(String.format("%s\t%s\t%s",  process.getName(), process.getCpuTime(), process.getIOBlocking()));
        }
        resultLogger.log("After:");

        for (Process process : result.getProcesses()) {
            resultLogger.log(String.format("%s\t%s\t%s", process.getName(), process.getCpuTime(), process.getIOBlocking()));
        }
    }
}
