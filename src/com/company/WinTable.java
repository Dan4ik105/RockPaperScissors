package com.company;

import com.bethecoder.ascii_table.ASCIITable;

import java.util.ArrayList;
import java.util.Arrays;

public class WinTable {
    WinTable( String [] args){
        this.args = args;
    }
    public String [] args;
    public String [][] date;

    String[][] getDate(){
        return this.date;
    }


    void displayTable(){
        String [] header = new String[this.args.length+1];
        this.date = new String[this.args.length][this.args.length+1];

        header[0] = "PC/User";
        System.arraycopy(this.args, 0, header, 1, this.args.length);
        for (int i = 0 ; i < this.args.length; i++){
            for (int j = 0; j < header.length; j++) {
                if (j == 0) {
                    this.date[i][j] = args[i];
                } else {
                    this.date[i][j] = "Lose";
                }
                if (i == j - 1) {
                    this.date[i][j] = "Draw";
                }
            }
        }
        RulesOfVictories rules = new RulesOfVictories(args);
        for(int[] s : rules.arrayRules()){
            this.date[s[0]][s[1]+1] = "Win";
        }
        ASCIITable.getInstance().printTable(header, this.date);
    }
}
