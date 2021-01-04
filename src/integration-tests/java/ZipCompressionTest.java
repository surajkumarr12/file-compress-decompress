import com.agoda.common.Main;
import com.agoda.common.helper.FileHelper;
import com.agoda.common.helper.InputHelper;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ZipCompressionTest {
    @Test
    public void compareZipCompressionAndDecompression() throws IOException {
        String inputDir = "src/test/resources/testFiles/";
        String zipDir = "src/test/resources/zip/";
        String unzipDir = "src/test/resources/unzip/";
        InputHelper.deleteDirectory(zipDir);
        String[] args = new String[]{"compress",inputDir, zipDir, "1"};
        Main.main(args);
        InputHelper.deleteDirectory(unzipDir);
        args = new String[]{"decompress",zipDir, unzipDir};
        Main.main(args);

        assertTrue(checkInputOutPutFileMatching(inputDir, unzipDir));
        assertTrue(allFilesLessThanMaxSize(zipDir, FileHelper.getBytesFromMB(InputHelper.getIntegerFromString("1"))));

    }

    private boolean allFilesLessThanMaxSize(String dir, long maxSize) {
        List<File> files = FileHelper.getAllFiles(dir);
        for (File file : files) {
            int fileLength = FileHelper.getMBFromBytes(file.length());
            if (fileLength > maxSize) {
                return false;
            }
        }
        return true;
    }

    private boolean checkInputOutPutFileMatching(String dir1, String dir2) {
        List<File> files1 = FileHelper.getAllFiles(dir1);
        List<File> files2 = FileHelper.getAllFiles(dir2);
        if (files1.size() != files2.size()) {
            return false;
        }
        for (int i = 0; i < files1.size(); i++) {
            if (files1.get(i).length() != files2.get(i).length()) {
                return false;
            }
        }
        return true;
    }


}
