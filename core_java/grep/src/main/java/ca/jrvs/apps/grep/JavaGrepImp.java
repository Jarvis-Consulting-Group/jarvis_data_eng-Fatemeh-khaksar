package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaGrepImp implements JavaGrep{
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public static void main(String[] args) {
        if(args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        }catch (Exception ex){
            javaGrepImp.logger.error("Error: Unable to process ", ex);
        }
    }

    @Override
    public void process() throws IOException {
        List<File> files = listFiles(getRootPath());
        List<String> matchedLines = new ArrayList<>();
        for (File file: files) {
            for (String line: readLines(file)) {
                if (containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) throws IOException {
        List<File> files = new ArrayList<>();
        File dir = new File(rootDir);
        File[] allFiles = dir.listFiles();
        int i = 0;
        for(File file : allFiles){
            if(file.isDirectory()){
                files.addAll(listFiles(file.getAbsolutePath()));
            }
            else{
                files.add(file);
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
            bufferedReader.close();
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsPattern(String line) {
        if(line.matches(getRegex())){
            return true;
        }
        return false;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile, true));
            for (String line: lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}