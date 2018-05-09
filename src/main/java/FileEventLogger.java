import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {
    private File file;
    private String filename;

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

//    @Autowired  // Почему это необходимо тут?
//    @PostConstruct
    public void init() throws IOException {
        this.file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file " + filename);
        } else if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't create file", e);
            }
        }

    }

    @Override
    public void logEvent(Event event) {
        final String newLine = System.getProperty("line.separator");
        try {
            FileUtils.writeStringToFile(file, event.toString() + newLine, true); // не работает перенос на новую строку "\n"
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
