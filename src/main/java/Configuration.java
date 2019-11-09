import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Configuration extends DefaultHandler {

    private LinkedList<Process> processes;

    private Logger logger;

    private Logger resultLogger;

    private int timeSlot;

    public Configuration(){
        processes = new LinkedList<>();
        this.logger = new Logger();
        this.resultLogger = new Logger();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("Processes")){
            String timeSlotStr = attributes.getValue("timeSlot");
            if(Objects.isNull(timeSlotStr)){
                throw new SAXException("Unable to read timeSlot value");
            }
            this.timeSlot = Integer.parseInt(timeSlotStr);
        }
        if(qName.equals("Process")){
            String name = attributes.getValue("name");
            String ioBlockingStr = attributes.getValue("IOBlocking");
            String cpuTimeStr = attributes.getValue("cpuTime");

            if(Objects.isNull(name) || Objects.isNull(ioBlockingStr) || Objects.isNull(cpuTimeStr))
                throw new SAXException("Unable to read process");
            Process process = new Process(name, Integer.parseInt(cpuTimeStr), new IODevice(Integer.parseInt(ioBlockingStr)));

            this.processes.add(process);
            return;
        }
        if(qName.equals("LogFile")){
            String name = attributes.getValue("name");
            if(Objects.isNull(name))
                throw new SAXException("Unable to read LogFile name");
            try {
                this.logger.addStream(new FileOutputStream(name));
            } catch (FileNotFoundException e) {
                throw new SAXException("Unable to find file with name \""+name +"\"");
            }
        }
        if(qName.equals("ResultFile")){
            String name = attributes.getValue("name");
            if(Objects.isNull(name))
                throw new SAXException("Unable to read LogFile name");
            try {
                this.resultLogger.addStream(new FileOutputStream(name));
            } catch (FileNotFoundException e) {
                throw new SAXException("Unable to find file with name \""+name +"\"");
            }
        }
    }

    public Queue<Process> getProcesses() {
        return processes;
    }

    public Logger getLogger() {
        return logger;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public Logger getResultLogger() {
        return resultLogger;
    }
}
