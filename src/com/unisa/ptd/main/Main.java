package com.unisa.ptd.main;

import com.unisa.ptd.parser.RecDesParser;

import java.io.IOException;

public class Main {
    private final static String FAILPATH = "src/com/unisa/ptd/testFile/fail";
    private final static String SUCCPATH = "src/com/unisa/ptd/testFile/succ";

    public static void main(String[] args) {
        try{
            System.out.println(parseTestFile(FAILPATH, 7)+""
                              +parseTestFile(SUCCPATH, 6));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String parseTestFile(String path, int numFile) throws IOException {
        String toPrint = "";
        for(int i = 0; i < numFile; i++){
            String name = path+(i+1)+".txt";
            RecDesParser parser = new RecDesParser(name);
            String result = (parser.program())?"Accepted":"Rejected";
            toPrint+="Outcome "+name+": "+result+"\n";
        }
        return toPrint;
    }
}
