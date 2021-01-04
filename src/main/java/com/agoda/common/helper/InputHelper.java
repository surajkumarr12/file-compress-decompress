package com.agoda.common.helper;

import com.agoda.common.enums.Action;
import com.agoda.common.enums.InputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class InputHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputFormat.class);

    public static void validateInput(String[] inputArgs) {
        
        if(inputArgs == null || inputArgs.length < 1) {
            LOGGER.error("Invalid input arguments ");
            throw new IllegalArgumentException("Usage is wrong, Expected : "+ InputFormat.COMPRESS.format +" or "+ InputFormat.DECOMPRESS.format);
        }

        if (Action.COMPRESS.name().equalsIgnoreCase(inputArgs[0])) {
            if (inputArgs.length != 4) {
                LOGGER.error("Invalid input arguments " + Arrays.asList(inputArgs));
                throw new IllegalArgumentException("COMPRESS expects 4 arguments, please check your input arguments. Usage : "+ InputFormat.COMPRESS.format);
            }
            checkInputAndOutputDir(inputArgs[1], inputArgs[2]);
            
            if(!ifInteger(inputArgs[3])) {
                LOGGER.error("Invalid input arguments " + Arrays.asList(inputArgs));
                throw new NumberFormatException("Expected 3rd argument as number  invalid: "+inputArgs[3 ]+" Usage : "+ InputFormat.COMPRESS.format);
            }
          
        } else if (Action.DECOMPRESS.name().equalsIgnoreCase(inputArgs[0])) {
            if (inputArgs.length != 3) {
                LOGGER.error("Invalid input arguments " + Arrays.asList(inputArgs));
                throw new IllegalArgumentException("DECOMPRESS expects 3 arguments, please check your input arguments. Usage : "+ InputFormat.DECOMPRESS.format);
            }
            checkInputAndOutputDir(inputArgs[1], inputArgs[2]);

        } else {
            LOGGER.error("Invalid input arguments " + Arrays.asList(inputArgs));
            throw new IllegalArgumentException("Usage is wrong, Expected : "+ InputFormat.COMPRESS.format +" or "+ InputFormat.DECOMPRESS.format);
        }
    }

    private static void checkInputAndOutputDir(String inputDir, String outputDir) {
        if (!FileHelper.isDir(inputDir)) {
            LOGGER.error("Invalid input directory " + inputDir);
            throw new IllegalArgumentException("Input directory : "+inputDir+" is not valid. ");
        }
        //emptying output dir if present or creating new if not present
        if (FileHelper.isDir(outputDir)) {
            deleteDirectory(outputDir);
        }
        FileHelper.createDirIfNotPresent(outputDir);
    }

    private static boolean ifInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid maxFileSize is given : " + input);
            return false;
        }
    }

    public static void deleteDirectory(String  dir) {
        File dirFile = new File(dir);
        File[] contents = dirFile.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDirectory(f.getPath());
                }
            }
        }
        dirFile.delete();
    }

    public static Integer getIntegerFromString(String input) {
            return Integer.parseInt(input);
    }
}
