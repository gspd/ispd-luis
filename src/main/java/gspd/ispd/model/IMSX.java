package gspd.ispd.model;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * IMSX file extension loader, and IMSX annotation processor
 *
 * @author Luís Baldissera
 */
public class IMSX {
    private Properties metadata;
    private List<User> users;
    private List<Hardware> hardware;
    private List<VM> vms;
    private Workload workload;

    public static IMSX load(File file) {
        return null;
    }
    public static IMSX load(String filename) {
        return load(new File(filename));
    }
}
