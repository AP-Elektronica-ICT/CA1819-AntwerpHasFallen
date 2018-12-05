

public class SubstitionPuzzles : Puzzles {
<<<<<<< HEAD
    
=======
>>>>>>> master
    string key;

    public string CheckKey(string key){
        string Key = key;
        if (this.key == Key) {
            return ("Key is correct, hiermee kan u verder");
        }
        else
        {
            return("Key is fout, probeer opnieuw");
        }
    }
}

