package com.ahf.antwerphasfallen;

public class Puzzles {

String question;
String solution;

public String Checksolution(String sol){
    String solution = sol;
    if (this.solution == solution) {
        return ("Antwoord is correct");
    }
    else
        {
           return("Antwoord is fout, probeer opnieuw voor -100 euro");
        }
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}


