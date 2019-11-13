package com.unisa.ptd.parser;

import com.unisa.ptd.lexer.Lexer;
import com.unisa.ptd.sd.Token;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecDesParser {

    private List<Token> tokenList;
    private Lexer lexer;
    private int index;
    private InputStream fr;

    public RecDesParser(String fileName) throws IOException {
        tokenList = new ArrayList<>();
        lexer = new Lexer();
        fr = new BufferedInputStream(new FileInputStream(fileName));
        index = 0;
    }

    public boolean program() throws IOException {
        boolean tr = false;
        if(stmt()){
            tr = (program1())?true:false;
        }
        fr.close();
        return  tr;
    }

    public boolean program1() throws IOException {
        boolean tr = false;
        int back = index;
        this.tokenList.add(lexer.nextToken(fr));
        if(tokenList.get(index).getLessema().equals("SEMICOL")){
            index++;
            if(stmt()){
                if(program1()){
                    tr = true;
                }else index = back; //backtrack
            }else index = back;
        }else tr = true;//epsilon
        return  tr;
    }

    public boolean stmt() throws IOException{
        boolean tr = false;
        int back = index;
        this.tokenList.add(lexer.nextToken(fr));
        if(this.tokenList.get(index).getClasse().equals("IF")){
            index++;
            if(expr()){
                this.tokenList.add(lexer.nextToken(fr));
                if(this.tokenList.get(index).getClasse().equals("THEN")){
                    index++;
                    if(stmt()){
                        tr = true;
                    }else index = back;
                }else index = back;
            }else index = back;
        }else{
            if(tokenList.get(index).getClasse().contains("ID:")){
                index++;
                this.tokenList.add(lexer.nextToken(fr));
                if(this.tokenList.get(index).getLessema().equals("ASSIGN")){
                    index++;
                    if(expr()){
                        tr = true;
                    }else index = back;
                }else index = back;
            } else index = back;
        }
        return  tr;
    }

    public boolean expr() throws IOException {
        boolean tr = false;
        int back = index;
        if(term()){
            if(expr1()){
                tr = true;
            }else index = back;
        }else index = back;
        return  tr;
    }

    public boolean expr1() throws IOException {
        boolean tr = false;
        int back = index;
        this.tokenList.add(lexer.nextToken(fr));
        if(tokenList.get(index).getClasse().equals("RELOP")){
            index++;
            if(term()){
                tr = true;
            }else index = back;
        }else tr = true; //epsilon
        return  tr;
    }

    public boolean term() throws IOException{
        boolean tr = false;
        this.tokenList.add(lexer.nextToken(fr));
        String classe = tokenList.get(index).getClasse();
        if(classe.contains("ID:") ||
           classe.contains("CONST") ||
           classe.equals("TRUE") ||
           classe.equals("FALSE")) {
            index++;
            tr = true;
        }
        return  tr;
    }

}
