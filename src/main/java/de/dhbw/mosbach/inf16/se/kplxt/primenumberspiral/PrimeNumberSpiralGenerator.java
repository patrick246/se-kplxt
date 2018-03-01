package de.dhbw.mosbach.inf16.se.kplxt.primenumberspiral;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrimeNumberSpiralGenerator {

    private List<Integer> primeList = new ArrayList<>();

    public List<Point> generate(int limit) {
        final int[] lastX = {0};
        final int[] lastY = {0};
        final int[] directionX = {0};
        final int[] directionY = {1};
        final int[] lastPrime = {1};

        generateNumbers(limit);

        return primeList
                .stream()
                .limit(limit)
                .map(number -> {
                    lastX[0] += directionX[0] * (number - lastPrime[0]);
                    lastY[0] += directionY[0] * (number - lastPrime[0]);
                    Point point = new Point(lastX[0], lastY[0]);
                    int newXDir = nextXDirection(directionX[0], directionY[0]);
                    int newYDir = nextYDirection(directionX[0], directionY[0]);
                    directionX[0] = newXDir;
                    directionY[0] = newYDir;
                    lastPrime[0] = number;
                    return point;
                })
                .collect(Collectors.toList());
    }

    private int nextXDirection(int dirX, int dirY) {
        if(dirX == 1 && dirY == 0) {
            return 0;
        } else if(dirX == 0 && dirY == -1) {
            return -1;
        } else if(dirX == -1 && dirY == 0) {
            return 0;
        } else if(dirX == 0 && dirY == 1) {
            return 1;
        }

        throw new IllegalStateException("wrong direction state, " + dirX + "," + dirY);
    }

    private int nextYDirection(int dirX, int dirY) {
        if(dirX == 1 && dirY == 0) {
            return -1;
        } else if(dirX == 0 && dirY == -1) {
            return 0;
        } else if(dirX == -1 && dirY == 0) {
            return 1;
        } else if(dirX == 0 && dirY == 1) {
            return 0;
        }
        throw new IllegalStateException("wrong direction state, " + dirX + "," + dirY);
    }

    private void generateNumbers(long n) {
        int start = 1;

        if(primeList.size() >= n)
            return;

        if(!primeList.isEmpty()) {
            start = primeList.get(primeList.size() - 1) + 1;
        }
        primeList.addAll(Stream.iterate(start, i -> i + 1)
                .limit(n)
                .parallel()
                .filter(PrimeTester::isPrime)
                .collect(Collectors.toList()));
    }
}
