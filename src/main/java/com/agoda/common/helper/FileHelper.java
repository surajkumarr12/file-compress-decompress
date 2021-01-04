package com.agoda.common.helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper methods related to files
 */
public class FileHelper {

    public static final int KB = 1000;
    public static final int MB = 1000 * KB;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    public static boolean isDir(String dir) {

        return Files.isDirectory(Paths.get(dir));
    }

    public static void createDirIfNotPresent(String dir) {
        try {
            Files.createDirectory(Paths.get(dir));
        } catch (IOException e) {
            LOGGER.error("Unable to create directory at "+dir+ "Error : "+e.getMessage());
        }
    }

    public static List<File> getAllFiles(String dirPath) {
        try {
            return Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Unable to get all files  "+dirPath+ "Error : "+e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param dirPath path for zip files
     * @return list of zip files sorted by creation date
     */
    public static List<File> getAllZipFiles(String dirPath) {
       File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles((dir, filename) -> filename.endsWith(".zip"));
        Comparator<File> comparator = Comparator.comparing(file -> {
            try {
                return Files.readAttributes(Paths.get(file.toURI()), BasicFileAttributes.class).creationTime();
            } catch (IOException e) {
                return null;
            }
        });

        Arrays.sort(files, comparator);
        return Arrays.asList(files);
    }

    public static int getBytesFromMB(Integer size) {
        return  (MB * size);
    }

    /**
     *
     * Useful while putting to zip files
     */
    public static String getRelativePath(String basePath, String absolutePath) {
        return Paths.get(basePath).relativize(Paths.get(absolutePath)).toString();

    }

    public static int getMBFromBytes(long size) {
        return (int)size/MB;
    }


}
