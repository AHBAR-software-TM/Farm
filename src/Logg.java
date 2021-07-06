import java.io.IOException;
import java.util.logging.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.util.logging.Level.*;

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

        LOGGER.setLevel(ALL);
        manager.addLogger(LOGGER);
        filehandler.setFormatter(simpleformatter);
        LOGGER.addHandler(filehandler);
        LOGGER.setUseParentHandlers(false);

    }


}