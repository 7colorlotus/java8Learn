package com.lotus.java8Learn.lambdaTest;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Point {
    public static Comparator<Point> compareByXAndThenY = (p1,p2) -> {
        if(p1.getX() == p2.getX()){
            return p1.getY() - p2.getY();
        }else{
            return p1.getX() - p2.getX();
        }
    };

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



    public Point moveRightBy(int x) {
        return new Point(this.x + x, this.y);
    }

    public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
        return points.stream()
                .map(p -> new Point(p.getX() + x, p.getY()))
                .collect(toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}