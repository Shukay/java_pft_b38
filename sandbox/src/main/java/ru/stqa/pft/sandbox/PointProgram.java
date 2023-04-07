package ru.stqa.pft.sandbox;

import java.text.DecimalFormat;

public class PointProgram {
    public static void main(String[] args) {

        Point p1 = new Point(2, 2);
        Point p2 = new Point(5,2);

        Point p3 = new Point(1, 1);
        Point p4 = new Point(0,0);

        Point p5 = new Point(-13, 17);
        Point p6 = new Point(7,-1);

        DecimalFormat decimalFormat = new DecimalFormat( "#.###" );


        System.out.println("Расстояние между точками " + p1 + " и " + p2 + " равно " + decimalFormat.format(Point.distance(p1, p2)));

        System.out.println("Расстояние между точками " + p3 + " и " + p4 + " равно " + decimalFormat.format(Point.distance(p3, p4)));

        System.out.println("Расстояние между точками " + p5 + " и " + p6 + " равно " + decimalFormat.format(Point.distance(p5, p6)));

    }
}
