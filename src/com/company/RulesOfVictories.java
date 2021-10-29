package com.company;

import java.util.ArrayList;

public class RulesOfVictories {
    RulesOfVictories(String [] args){
        this.args = args;
    }
    public String [] args;
    public ArrayList<int []> rules = new ArrayList<>();

    public ArrayList<int []> arrayRules(){
        double middle = Math.ceil(this.args.length / 2.0);
        for (int j=1; j<=middle-1;j++){
            for(int i = 0; i<this.args.length; i++){
                int item = ((i+j)>this.args.length-1) ? i + j - this.args.length : i+j;
                this.rules.add(new int[]{i,item});
            }
        }
        return this.rules;
    }
    public String result(int pc, int user){
        int numberOfStepsForward = 0;
        int numberOfStepsBack=0;
        if (pc==user){
            return "Draw";
        }
        for (int i=user; ; i++){
            numberOfStepsForward+=1;
            if(i>this.args.length){
                i=0;
            }
            if (i==pc){
                break;
            }
        }
        for (int j=user; ; j--){
            numberOfStepsBack+=1;
            if(j==0){
                j=this.args.length;
            }
            if (j==pc){
                break;
            }
        }
        if(numberOfStepsBack<numberOfStepsForward){
            return "You win!";
        }else return "You lose!";
    }


}
