package thermalconduction03;

import static thermalconduction03.Constants.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author mat
 */
public class FileIO {
    public static List<String> readLines(String fname) {
        try {
            Path path = Paths.get(PATH + fname + ".txt");
            File file = path.toFile();
            List<String> lines = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.toList());
            return lines;

        } catch (IOException ex) {
            Logger.getLogger(ThermalConduction03.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void writeLines(List<String> lines, String fname) {
        try {
            Files.write(Paths.get(PATH, fname + ".txt"), lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            Logger.getLogger(ThermalConduction03.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
