

public class Puzzles {

string question;
string solution;

public string Checksolution(string sol){
    string solution = sol;
    if (this.solution == solution) {
        return ("Antwoord is correct");
    }
    else
        {
           return("Antwoord is fout, probeer opnieuw voor -100 euro");
        }
    }

    public void setQuestion(string question) {
        this.question = question;
    }

    public void setSolution(string solution) {
        this.solution = solution;
    }
}


