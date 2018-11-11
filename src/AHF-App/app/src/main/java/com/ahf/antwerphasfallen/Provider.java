package com.ahf.antwerphasfallen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorren on 27/10/2018.
 */

public class Provider {

    //private static Provider provider;

    private static boolean Different;
    private static List<Subscriber> isDifferentSubscribers = new ArrayList<>();

    /*public static Provider GetProvider(){
        if(provider == null)
            provider = new Provider();
        return provider;
    }*/

    public static boolean isDifferent() {
        return Different;
    }

    public static void setIsDifferent(boolean isDifferent) {
        Provider.Different = isDifferent;
        for (Subscriber s : isDifferentSubscribers)
            s.Update();
    }

    public static boolean subscribeIsDifferent(Subscriber subscriber){
        return isDifferentSubscribers.add(subscriber);
    }

    public static boolean unsubscribeIsDifferent(Subscriber subscriber){
        return isDifferentSubscribers.remove(subscriber);
    }
}
