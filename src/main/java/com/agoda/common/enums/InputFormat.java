package com.agoda.common.enums;

/**
 * Inputformat expected from commandline
 */
public enum InputFormat {

    COMPRESS("COMPRESS inputDir outputDir maxCompressSizeInMB"),
    DECOMPRESS("DECOMPRESS inputDir outputDir");


    public final String format;

    InputFormat(String format) {
        this.format = format;
    }
}
