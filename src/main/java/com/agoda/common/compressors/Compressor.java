package com.agoda.common.compressors;

import com.agoda.common.helper.InputHelper;

public abstract class Compressor {

    private String inputDir;
    private String outputDir;
    private Integer maxFileSize;

    public Compressor(String inputDir, String outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    public String getInputDir() {
        return inputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public Integer getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = InputHelper.getIntegerFromString(maxFileSize);
    }

    public abstract void compress();
    public abstract void decompress();

}
