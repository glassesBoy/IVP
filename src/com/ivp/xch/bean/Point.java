package com.ivp.xch.bean;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static void main(String[] args) {
        // Math.PI
        double a = Math.toRadians(30 + 180);
        System.out.println("=============== a " + a);
        double b1 = Math.toRadians(30);
        System.out.println("=============== b1 " + b1);
        double b2 = Math.toRadians(180);
        System.out.println("=============== b2 " + b2);
        System.out.println("====================== b1 + b2 " + (b1 + b2));


        // a 3.6651914291880923
        // 3.665191429188092
        // =============== b1 0.5235987755982988
        // =============== b2 3.141592653589793

    }
}
