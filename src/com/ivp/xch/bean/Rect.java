package com.ivp.xch.bean;


public class Rect {

    private double width;
    private double length;

    // 对角线 长度
    private double diagonal;

    // 对角线夹角 （锐角） 角度制
    private double includedAngle;

    public Rect(double length, double width) {
        this.width = width;
        this.length = length;
        // 勾股定理
        this.diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(length, 2));
        // 计算两个 对角线的夹角 的一半
        // Math.tan( (width / 2) / (length / 2) )
        this.includedAngle = Math.toDegrees(Math.atan(width / length));

    }


    public double getWidth() {
        return width;
    }


    public void setWidth(double width) {
        this.width = width;
    }


    public double getLength() {
        return length;
    }


    public void setLength(double length) {
        this.length = length;
    }


    public double getDiagonal() {
        return diagonal;
    }


    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }


    public double getIncludedAngle() {
        return includedAngle;
    }


    public void setIncludedAngle(double includedAngle) {
        this.includedAngle = includedAngle;
    }


    /**
     * 
     * @param angle 偏向角
     * @param centerPoint 对角线 交叉点（外接圆圆心）
     */
    public Point[] getPoints(double angle, Point centerPoint) {
        double R = this.diagonal;
        double inAngle = this.includedAngle;

        double angle1 = angle - inAngle;
        double angle11 = angle - inAngle + 180;

        double angle2 = angle + inAngle;
        double angle21 = angle + inAngle + 180;

        double x = centerPoint.getX();
        double y = centerPoint.getY();

        double x1 = 0;
        double y1 = 0;

        double x2 = 0;
        double y2 = 0;

        double x3 = 0;
        double y3 = 0;

        double x4 = 0;
        double y4 = 0;

        // 角度是 0 cos 0 = 1 sin 0 = 0
        if (angle1 == 0 || angle1 == 360) {
            x1 = x;
            y1 = R + y;

            x2 = x;
            y2 = y - R;
        } else if (angle1 == 90) {
            // 角度是 90 cos = 0 sin =1
            x1 = R + x;
            y1 = y;

            x2 = x - R;
            y2 = y;
        } else if (angle1 == 180) {
            // 角度是 180 cos 180 = -1 sin 180 = 0
            x1 = x;
            y1 = y - R;

            x2 = x;
            y2 = R + y;
        } else if (angle1 == 270) {
            // 角度是 cos270 = 0 sin270 = -1
            x1 = x - R;
            y1 = y;

            x2 = R + x;
            y2 = y;
        } else {
            x1 = R * Math.sin(Math.toRadians(angle1)) + x;
            y1 = R * Math.cos(Math.toRadians(angle1)) + y;

            x2 = R * Math.sin(Math.toRadians(angle11)) + x;
            y2 = R * Math.cos(Math.toRadians(angle11)) + y;
        }

        if (angle2 == 0 || angle2 == 360) {
            x3 = x;
            y3 = R + y;

            x4 = x;
            y4 = y - R;
        } else if (angle2 == 90) {
            // 角度是 90 cos = 0 sin =1
            x3 = R + x;
            y3 = y;

            x4 = x - R;
            y4 = y;
        } else if (angle2 == 180) {
            // 角度是 180 cos 180 = -1 sin 180 = 0
            x3 = x;
            y3 = y - R;

            x4 = x;
            y4 = R + y;
        } else if (angle2 == 270) {
            // 角度是 cos270 = 0 sin270 = -1
            x3 = x - R;
            y3 = y;

            x4 = R + x;
            y4 = y;
        } else {
            x3 = R * Math.sin(Math.toRadians(angle2)) + x;
            y3 = R * Math.cos(Math.toRadians(angle2)) + y;

            x4 = R * Math.sin(Math.toRadians(angle21)) + x;
            y4 = R * Math.cos(Math.toRadians(angle21)) + y;
        }

         System.out.print("============== x1 " + x1);
         System.out.println(" y1 " + y1);
        
         System.out.print("============== x2 " + x2);
         System.out.println(" y2 " + y2);
        
         System.out.print("============== x3 " + x3);
         System.out.println(" y3 " + y3);
        
         System.out.print("============== x4 " + x4);
         System.out.println(" y4 " + y4);


        Point[] pts = new Point[4];
        pts[0] = new Point(x1, y1);
        pts[1] = new Point(x2, y2);
        pts[2] = new Point(x3, y3);
        pts[3] = new Point(x4, y4);
        return pts;
    }


    public static void main(String[] args) {
        // double an = Math.toDegrees(Math.sin(Math.toRadians(30)));
        // System.out.println("================ an " + an);
        //
        //
        // double aa2 = Math.toRadians(30);
        // System.out.println("================ aa2 " + aa2);
        //
        // double an2 = Math.asin(Math.sin(Math.toRadians(30)));
        // System.out.println("================ an2 " + an2);
        //
        // double a3 = Math.asin(0.5);
        // System.out.println("================ a3 " + a3);
        //
        // double a4 = Math.toDegrees(a3);
        // System.out.println("================ a4 " + a4);


        Rect rect = new Rect(4, 2);
        System.out.println("=================== getWidth " + rect.getWidth());
        System.out.println("=================== getLength " + rect.getLength());
        System.out.println("=================== getDiagonal " + rect.getDiagonal());
        System.out.println("=================== getAngle " + rect.getIncludedAngle());

        rect.getPoints(180, new Point(0, 0));



    }
}
