package com.ahf.antwerphasfallen.Model;

public class QuizPuzzles  {

    String question;
    String answers;
    String correctAnswer;
    String difficulty;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correct) {
        this.correctAnswer = correct;
    }

    public String getDifficulty(){
        return difficulty;
    }
}
