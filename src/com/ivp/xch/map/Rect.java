package com.ivp.xch.map;

public class Rect {

    /**
     * 点
     * 
     * @author
     *
     */
    public static class Point {
        public float x, y;

        public Point(float xx, float yy) {
            x = xx;
            y = yy;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

    }


    /**
     * 线段/线
     * 
     * @author
     *
     */
    public static class Line {
        // 线段顶端 两个点
        public Line(Point pp1, Point pp2) {
            p1 = pp1;
            p2 = pp2;
        }

        public Point p1;
        public Point p2;

        public Point getP1() {
            return p1;
        }

        public void setP1(Point p1) {
            this.p1 = p1;
        }

        public Point getP2() {
            return p2;
        }

        public void setP2(Point p2) {
            this.p2 = p2;
        }

    }


    public static class Rectangle {
        public Rectangle(Point pp1, Point pp2) {
            p1 = pp1;
            p2 = pp2;
        }

        public Point p1;
        public Point p2;

        public float minX() {
            return p1.x < p2.x ? p1.x : p2.x;
        }

        public float maxX() {
            return p1.x > p2.x ? p1.x : p2.x;
        }

        public float minY() {
            return p1.y < p2.y ? p1.y : p2.y;
        }

        public float maxY() {
            return p1.y > p2.y ? p1.y : p2.y;
        }

        public Point getP1() {
            return p1;
        }

        public void setP1(Point p1) {
            this.p1 = p1;
        }

        public Point getP2() {
            return p2;
        }

        public void setP2(Point p2) {
            this.p2 = p2;
        }

    }

    /**
     * 线段是否在 矩形内
     * 
     * @param point
     * @param rect
     * @return
     */
    public static boolean PointInsideRectangle(Point point, Rectangle rect) {
        // 如果 大于最小点 小于最大点 那么就 在 矩形内
        if (point.x >= rect.minX() && point.x <= rect.maxX()) {
            if (point.y >= rect.minY() && point.y <= rect.maxY()) {
                return true;
            }
        }
        return false;
    }


    // -1 点位于线段的左侧，0 点位于线段上面 ，1 点位于线段右侧
    public static int PointAtLineLeftRight(Point point, Line line) {
        // 线段 的两个点
        Point vect1 = new Point((line.p1.x - point.x), (line.p1.y - point.y));
        Point vect2 = new Point((line.p2.x - point.x), (line.p2.y - point.y));

        float nRet = vect1.x * vect2.y - vect1.y * vect2.x;
        if (nRet == 0) {
            return 0;
        } else if (nRet > 0) {
            return 1;
        } else if (nRet < 0) {
            return -1;
        }
        return 0;
    }

    /**
     * 两线相交
     * 
     * @param line1
     * @param line2
     * @return
     */
    public static boolean IsTwoLineIntersect(Line line1, Line line2) {
        int a = PointAtLineLeftRight(line1.p1, line2);
        int b = PointAtLineLeftRight(line1.p2, line2);
        if (a * b > 0)
            return false;

        a = PointAtLineLeftRight(line2.p1, line1);
        b = PointAtLineLeftRight(line2.p2, line1);
        if (a * b > 0)
            return false;
        return true;
    }

    /**
     * 线段 跟 长方形
     * 
     * @param line
     * @param rect
     * @return
     */
    public static boolean IsLineIntersectRect(Line line, Rectangle rect) {
        if (PointInsideRectangle(line.p1, rect)
                || PointInsideRectangle(line.p2, rect)) {
            return true;
        }

        if (PointInsideRectangle(new Point(line.p1.x, line.p2.y), rect)
                || PointInsideRectangle(new Point(line.p2.x, line.p1.y), rect)) {
            return true;
        }
        if (IsTwoLineIntersect(line, new Line(rect.p1, new Point(rect.p1.x, rect.p2.y)))) {
            return true;
        }

        if (IsTwoLineIntersect(line, new Line(rect.p1, new Point(rect.p2.x, rect.p1.y)))) {
            return true;
        }

        if (IsTwoLineIntersect(line, new Line(rect.p2, new Point(rect.p1.x, rect.p2.y)))) {
            return true;
        }

        if (IsTwoLineIntersect(line, new Line(rect.p2, new Point(rect.p2.x, rect.p1.y)))) {
            return true;
        }

        return false;
    }

    public static boolean LineConverttoRectangle(Line line, Rectangle rect) {
        if (PointInsideRectangle(line.p1, rect)
                || PointInsideRectangle(line.p2, rect))
            return true;
        if (!IsLineIntersectRect(line, rect))
            return false;
        int b1 = PointAtLineLeftRight(rect.p1, line);
        int b2 = PointAtLineLeftRight(rect.p2, line);
        int b3 = PointAtLineLeftRight(new Point(rect.p1.x, rect.p2.y), line);
        int b4 = PointAtLineLeftRight(new Point(rect.p2.x, rect.p1.y), line);
        if (b1 + b2 + b3 + b4 == 4)
            return false;
        if (b1 + b2 + b3 + b4 == -4)
            return false;
        return true;
    }

    public static void AAmain(String[] args) {
        Line line = new Line(new Point(5.0f, 1.8f),
                new Point(5.0f, -15f));

        Rectangle rect = new Rectangle(new Point(4.7f, -0.7f), new Point(6.7f, 1.4f));

        boolean isCross = LineConverttoRectangle(line, rect);
        System.out.println("================ isCross " + isCross);
        // System.out.println(
        // LineConverttoRectangle(
        // new Line(new Point(5.0f, 1.8f),
        // new Point(5.0f, -15f)),
        // new Rectangle(new Point(4.7f, -0.7f), new Point(6.7f, 1.4f))));
    }



    public void rect() {
        // 线段的起点和重点
        double lat1 = 31.5936816, lng1 = 120.7764291;
        // 31.5926768 120.7775058
        double lat2 = 1.5926768, lng2 = 120.7775058;



    }



    public static void main(String[] args) {
       
        // 东西走向
        // 起点的两个点
        double x1 = 31.5936816d, y1 = 120.7764291d;
        double x12 = 31.5936818, y12 = 120.7764301d;

        // 终点的两个点
        double x2 = 31.5926771d, y2 = 120.7775057d;
        double x22 = 31.5926768d, y22 = 120.7775058d;

        //测试点 p 
      //  double x0 = 31.5933736d ,y0= 120.7768807d;
//        myY1 120.77488909999452
//        myY2 120.77727353334708
        //double  x0 =  31.592032 ,    y0= 120.7722143d;
                                //  myY1 120.76818109997069
                                //  myY2 120.7777207333206
        
       // double  x0 =  31.5935907 ,    y0=  120.7767043;
//       myY1 120.77597459999839
//       myY2 120.7772011666847
        
        double  x0 = 31.597290419334,    y0= 120.78769951048;
//                                           120.79447319673412
//        ================= myY2             120.77596792697973

      // 东西走向  取y的值
       double myY1 = ((x0 - x1)/(x12 - x1))*(y12 - y1) + y1;
       
       double myY2 = ((x0 - x2)/(x22 - x2))*(y22 - y2) + y2;
       
       System.out.println("================= myY1 "+myY1);
       System.out.println("================= myY2 "+myY2);
      // 南北走向  取x的值
       
       // 拐弯先不考虑

       
//        120.7722143
     //   59257  31.592032 120.7722143
       
    }
}
