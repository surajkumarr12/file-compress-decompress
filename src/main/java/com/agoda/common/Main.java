package com.agoda.common;

import com.agoda.common.compressors.Compressor;
import com.agoda.common.compressors.ZipCompressor;
import com.agoda.common.enums.Action;
import com.agoda.common.enums.CompressAlgo;
import com.agoda.common.helper.InputHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Compressor compressor = null;
        //validate input
        InputHelper.validateInput(args);
        //getting compression algo
        CompressAlgo compressAlgo = getCompressMethod();

        if(CompressAlgo.ZIP.equals(compressAlgo)) {
            LOGGER.debug("ZIP file format is selected");
            //Create object of type zip.
            compressor = new ZipCompressor(args[1], args[2]);
        }
        else {
            throw new UnsupportedOperationException("Only ZIP supported as of now");
        }

        if (Action.COMPRESS.name().equalsIgnoreCase(args[0])) {
            compressor.setMaxFileSize(args[3]);
            LOGGER.debug("Compression started");
            long startTime = Instant.now().toEpochMilli();
            compressor.compress();
            long endTime = Instant.now().toEpochMilli();
            long timeElapsed = endTime - startTime;
            LOGGER.debug("Compression finished");
            LOGGER.debug("Total time elapsed  : "+timeElapsed+"ms");
        } else {
            LOGGER.debug("Decompression started");
            long startTime = Instant.now().toEpochMilli();
            compressor.decompress();
            long endTime = Instant.now().toEpochMilli();
            long timeElapsed = endTime - startTime;
            LOGGER.debug("Decompression finished");
            LOGGER.debug("Total time elapsed  : "+timeElapsed+"ms");
        }

    }

    //considering only ZIP for now but can be extendable to different algorithms.
    private static CompressAlgo getCompressMethod() {
        return CompressAlgo.ZIP;
    }
}
