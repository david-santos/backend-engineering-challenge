package pt.dcs.unbabel.bec;

import java.io.File;

public class Inputs {

    private File inFile;
    private File outFile;
    private int windowSize;

    Inputs(File inFile, File outFile, int windowSize) {
        this.inFile = inFile;
        this.outFile = outFile;
        this.windowSize = windowSize;
    }

    public File getInFile() {
        return inFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public int getWindowSize() {
        return windowSize;
    }
}
