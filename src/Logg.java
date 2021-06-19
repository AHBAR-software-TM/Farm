import java.io.IOException;
import java.util.logging.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class Logg {


    public static Logger LOGGER = Logger.getLogger("logger");
    public static LogManager manager = LogManager.getLogManager();
    public static SimpleFormatter simpleformatter = new SimpleFormatter();
    public static FileHandler filehandler;

    static {
        try {
            filehandler = new FileHandler("Log.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void log() throws IOException , InterruptedException {

        LOGGER.setLevel(Level.ALL);
        manager.addLogger(LOGGER);
        filehandler.setFormatter(simpleformatter);
        LOGGER.addHandler(filehandler);

        //file
        //LOGGER.fine("This message is logged in the file");
        //LOGGER.config("hello");
        //LOGGER.finer("buy");

        //both console and file
        //LOGGER.info("buy");
        //LOGGER.log(Level.WARNING, "Warning message");

    }
}