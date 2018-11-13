package com.ahf.antwerphasfallen;

import android.media.Image;

public class SubstitionPuzzles extends Puzzles {
    Image diagram;
    String key;



    public String CheckKey(String key){
        String Key = key;
        if (this.key == Key) {
            return ("Key is correct, hiermee kan u verder");
        }
        else
        {
            return("Key is fout, probeer opnieuw");
        }
    }

    public void setDiagram(Image diagram) {
        this.diagram = diagram;
    }

}

