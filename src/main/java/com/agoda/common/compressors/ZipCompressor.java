package com.agoda.common.compressors;

import com.agoda.common.helper.CountOutputStream;
import com.agoda.common.helper.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.*;

import static com.agoda.common.helper.FileHelper.*;

/**
 * Class has methods for compress and decompress.
 */
public class ZipCompressor extends Compressor {

    //part number for zip files
    private int partNum = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipCompressor.class);


    public ZipCompressor(String inputDir, String outputDir) {
        super(inputDir, outputDir);
    }

    /**
     * zips files in inputDir and puts in outputDir.
     */
    public void compress() {
        List<File> files = FileHelper.getAllFiles(getInputDir());
        FileOutputStream fout;
        //custom countOutputStream to count stream data read
        CountOutputStream countingOutputStream ;
        try {
            fout = new FileOutputStream(getOutputDir() + getPartName());
            countingOutputStream = new CountOutputStream(fout);
            ZipOutputStream zipOut = new ZipOutputStream(countingOutputStream);

            for (File file : files) {
                FileInputStream fin = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int bytesCount;
                String relativePath = getRelativePath(getInputDir(), file.getPath());
                ZipEntry zipEntry = new ZipEntry(relativePath);
                zipOut.putNextEntry(zipEntry);
                LOGGER.debug("Compressing file : "+ relativePath);
                while ((bytesCount = fin.read(buffer, 0, 1024)) !=-1) {
                    //if read buffer is greater than available maximum size.
                    if ( bytesCount >= (getBytesFromMB(getMaxFileSize()) - countingOutputStream.getCount()) )
                    {
                        zipOut.closeEntry();
                        zipOut.finish();
                        fout = new FileOutputStream(getOutputDir() + getPartName());
                        countingOutputStream.close();
                        countingOutputStream = new CountOutputStream(fout);
                        zipOut = new ZipOutputStream(countingOutputStream);
                        zipEntry = new ZipEntry(getRelativePath(getInputDir(), file.getPath()));
                        zipOut.putNextEntry(zipEntry);
                    }
                    zipOut.write(buffer, 0, bytesCount);
                }
                fin.close();
            }
            zipOut.close();
            countingOutputStream.close();
            fout.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * Decompresses zipFiles and produces original files in given outputDir
     */
    @Override
    public void decompress() {
        List<File> compressedFiles = FileHelper.getAllZipFiles(getInputDir());
        Map<String, FileOutputStream> foutMap = new HashMap<>();

        for(File file : compressedFiles) {
            FileInputStream fin;
            try {
                fin = new FileInputStream(file);
                ZipInputStream zipIn = new ZipInputStream(fin);
                ZipEntry zipEntry;
                FileOutputStream fout;

                while ((zipEntry = zipIn.getNextEntry()) != null) {
                    File filename = new File(getOutputDir() + zipEntry.getName());
                    if (!zipEntry.isDirectory()) {
                        filename.getParentFile().mkdirs();
                        filename.createNewFile();
                        if (foutMap.containsKey(zipEntry.getName())) {
                            fout = foutMap.get(zipEntry.getName());
                        } else {
                            fout = new FileOutputStream(filename);
                            foutMap.put(zipEntry.getName(), fout);
                        }
                        int bytesCount;
                        byte[] buffer = new byte[1024];
                        while ((bytesCount = zipIn.read(buffer, 0, 1024)) !=-1) {
                            fout.write(buffer, 0, bytesCount);
                        }
                    } else {
                       filename.mkdir();
                    }
                }
                zipIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private synchronized String getPartName() {
        return "part_"+(partNum++)+".zip";
    }

}
