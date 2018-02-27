package de.dhbw.mosbach.inf16.se.kplxt.primenumberspiral;

public class NumberHolder {

    public NumberHolder(Long l) {
        this.number = l;
        this.isPrime = isPrime(l);
    }

    private boolean isPrime(long l) {
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

    public final Long number;
    public final boolean isPrime;
}
