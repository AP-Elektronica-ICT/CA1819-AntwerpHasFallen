package com.ahf.antwerphasfallen.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorren on 27/10/2018.
 */

public class Provider {

    //private static Provider provider;

    private static boolean Different;
    private static List<Subscriber> isDifferentSubscribers = new ArrayList<>();

    private static boolean BlackoutRunning;
    private static List<Subscriber> isBlackoutRunningSubscribers = new ArrayList<>();

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

    public static boolean subscribeIsDifferent(Subscriber subscriber) {
        return isDifferentSubscribers.add(subscriber);
    }

    public static boolean unsubscribeIsDifferent(Subscriber subscriber) {
        return isDifferentSubscribers.remove(subscriber);
    }

    public static boolean isBlackoutRunning() {
        return BlackoutRunning;
    }

    public static void setIsBlackoutRunning(boolean isBlackoutRunning) {
        Provider.BlackoutRunning = isBlackoutRunning;
        for (Subscriber s : isBlackoutRunningSubscribers)
            s.Update();
    }

    public static boolean subscribeIsBlackoutRunning(Subscriber subscriber) {
        return isBlackoutRunningSubscribers.add(subscriber);
    }

    public static boolean unsubscribeIsBlackoutRunning(Subscriber subscriber) {
        return isBlackoutRunningSubscribers.remove(subscriber);
    }
}
