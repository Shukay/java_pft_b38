package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DecimalFormat;

public class PointTests {

    DecimalFormat decimalFormat = new DecimalFormat( "#.###" );

    @Test
    public void testDistance1() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,0);
        Assert.assertEquals(p1.distance(p2), 0);
    }

    @Test
    public void testDistance2() {
        Point p1 = new Point(-1,-1);
        Point p2 = new Point(1,1);
        Assert.assertEquals(decimalFormat.format(p1.distance(p2)), "2,828");
    }

    @Test
    public void testDistance3() {
        Point p1 = new Point(10,10);
        Point p2 = new Point(15,15);
        Assert.assertEquals(decimalFormat.format(p1.distance(p2)), "7,071");
    }
}
