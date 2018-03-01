package de.dhbw.mosbach.inf16.se.kplxt.primenumberspiral;

public class PrimeTester {
    public static boolean isPrime(int l) {
        if(l == 2)
            return true;
        if((l % 2) == 0)
            return false;
        if(l == 3)
            return true;
        if((l % 3) == 0)
            return false;

        for(long i = 5; i*i <= l; i+=2) {
            if((l % i) == 0)
                return false;
        }
        return true;
    }
}
