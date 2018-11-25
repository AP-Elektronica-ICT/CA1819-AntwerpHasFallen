package com.ahf.antwerphasfallen;

public class QuizPuzzles  {
    String questions[] = {
            "Wanneer heeft Antwerpen zijn stadszegel gekregen ?",
            "Waar hangt de Memorial van de 1ste Wereldoorlog?",
            "Wat is de naam van de tijger aan de rechterzuil van de inkom van de zoo?"
    };

    String answers[][]= {
            {"1008" ,"1250","1420"},
            {"Centraal Station","Groenplaats","Stadswaag"},
            {"Tigris Stellis", "Felis Tigris", "Tigro Antwerp"}
    };

    String correct[]  = {"1008", "Centraal Station","Felis Tigris"};

    public String getQuestion(int a) {
        String question = questions[a];
        return question;
    }

    public String getChoice1(int a) {
        String choice0 = answers[a][0];
        return choice0;
    }
    public String getChoice2(int a) {
        String choice1 = answers[a][1];
        return choice1;
    }
    public String getChoice3(int a) {
        String choice2 = answers[a][2];
        return choice2;
    }

    public String getCorrectAnswer(int a){
        String answer = correct[a];
        return answer;
    }



}
