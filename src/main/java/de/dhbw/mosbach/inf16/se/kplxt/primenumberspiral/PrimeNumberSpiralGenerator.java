package de.dhbw.mosbach.inf16.se.kplxt.primenumberspiral;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrimeNumberSpiralGenerator {

    private List<NumberHolder> numberList = new ArrayList<>();

    public List<Point> generate(long limit) {
        final int[] lastX = {0};
        final int[] lastY = {0};
        final int[] directionX = {1};
        final int[] directionY = {0};

        generateNumbers(limit);

        return numberList
                .stream()
                .limit(limit)
                .map(n -> {
                    Point point = new Point(lastX[0], lastY[0], n.number);

                    if(n.isPrime) {
                        int newXDir = nextXDirection(directionX[0], directionY[0]);
                        int newYDir = nextYDirection(directionX[0], directionY[0]);
                        directionX[0] = newXDir;
                        directionY[0] = newYDir;
                    }
                    lastX[0] += directionX[0];
                    lastY[0] += directionY[0];
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
        long start = 1L;

        if(numberList.size() >= n)
            return;

        if(!numberList.isEmpty()) {
            start = numberList.get(numberList.size() - 1).number + 1;
        }
        numberList.addAll(Stream.iterate(start, i -> i + 1)
                .limit(n)
                .parallel()
                .map(NumberHolder::new)
                .collect(Collectors.toList()));
    }
}
