package com.ivp.xch.bean;

/**
 * 
 * @author
 *
 */
public class PointAndRect {

    // 计算 |p1 p2| X |p1 p|
    static double GetCross(Point p1, Point p2, Point p) {
        return (p2.getX() - p1.getX()) * (p.getY() - p1.getY()) - (p.getX() - p1.getX()) * (p2.getY() - p1.getY());
    }

    // 判断点是否在5X5 以原点为左下角的正方形内（便于测试）
    static boolean IsPointInMatrix(Point p) {
        Point p1 = new Point(0, 5);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(5, 0);
        Point p4 = new Point(5, 5);

        return GetCross(p1, p2, p) * GetCross(p3, p4, p) >= 0 && GetCross(p2, p3, p) * GetCross(p4, p1, p) >= 0;
        // return false;
    }

    public static void main(String[] args) {

        while (true) {
            Point testPoint = new Point(0, 0);
            System.out.println("enter  the point :"); // << endl;

            System.out.println(testPoint.getX());

            System.out.println(testPoint.getY());

            System.out.println("the point is  : ");
            System.out.println(testPoint.getX());
            System.out.println(testPoint.getX());

            System.out.println("the point is ");
            System.out.println(IsPointInMatrix(testPoint) ? "in the Matrix ." : "not in the matrix .");
        }

        // return 0;
    }
}
