package com.ivp.xch.bean;

import java.awt.geom.Line2D;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.ivp.xch.db.DBEM;
import com.ivp.xch.map.FieldGPS;

public class IntersectsLine {


    // private double x = 31.592068 , y 120.7723044;
    // 31.5920622,120.7727444

    private static double y1 = 31.5919733d, x1 = 120.7725163d;
    private static double y2 = 31.5922907d, x2 = 120.7732452d;



    private List<FieldGPS> gpsList = null;
    private static IntersectsLine lin = null;

    private IntersectsLine() {
        // 这里先查询出来所有的 点
        EntityManager em = DBEM.em();
        gpsList = em.createQuery("  select f from  FieldGPS  f where f.section = 'L0' ", FieldGPS.class).getResultList();
        em.close();
    }


    public IntersectsLine getLineHelper() {
        if (lin == null) {
            lin = new IntersectsLine();
        }
        return lin;
    }

    public boolean IntersectsLine(Point p) {
        return IntersectsLine(p.getX(), p.getY());
    }

    /**
     * 小车中心点
     * 
     * @param x1
     * @param y1
     * @return
     */
    public boolean IntersectsLine(double x1, double y1) {

        return false;
    }



    public static void main(String[] args) {

        // Point p1 = new Point(0, 5);
        // Point p2 = new Point(0, 0);
        // Point p3 = new Point(5, 0);
        // Point p4 = new Point(5, 5);
        //
        //
        // boolean flag = Line2D.linesIntersect(0d, 5d, 0d, 0d, 5d, 0d, 0d, 1d);
        // System.out.println("=============== flag " + flag);

        Rect rect = new Rect(3, 1);
        // 获取长方形 四个线段

        long dt0 = new Date().getTime();
        // System.out.println("================ dt0 "+dt0);

        EntityManager em = DBEM.em();
        List<FieldGPS> gpsList = em.createQuery("  select f from  FieldGPS  f where f.section = 'L0' ", FieldGPS.class).getResultList();
        em.close();


        // 计算开始时间

        long dt1 = new Date().getTime();
        // System.out.println("================ dt1 "+dt1);
        // System.out.println("======================== diff dt1 - dt0 "+(dt1 - dt0));

        for (int i = 0; i < gpsList.size() - 1; i++) {
            FieldGPS gps = gpsList.get(i);
            FieldGPS gps1 = gpsList.get(i + 1);

            double angle = gps.getHeading();

            Point[] pts = rect.getPoints(angle, new Point(x1, y1));

            boolean flag0 = Line2D.linesIntersect(pts[0].getX(), pts[0].getY(), pts[2].getX(), pts[2].getY(), gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(),
                    gps1.getLatitude());
            /// System.out.println("=============== p0 p2 flag0 " + flag0);

            boolean flag1 = Line2D.linesIntersect(pts[0].getX(), pts[0].getY(), pts[3].getX(), pts[3].getY(), gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(),
                    gps1.getLatitude());
            // System.out.println("=============== p0 p3 flag1 " + flag1);
            boolean flag2 = Line2D.linesIntersect(pts[2].getX(), pts[2].getY(), pts[1].getX(), pts[1].getY(), gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(),
                    gps1.getLatitude());
            // System.out.println("=============== p2 p1 flag2 " + flag2);
            boolean flag3 = Line2D.linesIntersect(pts[2].getX(), pts[2].getY(), pts[3].getX(), pts[3].getY(), gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(),
                    gps1.getLatitude());
                    // System.out.println("=============== p2 p3 flag3 " + flag3);


            /* for (Point point : pts) {
                double x = point.getX();
                double y = point.getY();
            
                boolean flag = Line2D.linesIntersect(x, y, x2, y2, gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(), gps1.getLatitude());
                System.out.println("=============== flag  " + flag);
            }*/

            // boolean flag = Line2D.linesIntersect(x1, y1, x2, y2, gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(), gps1.getLatitude());
            // System.out.println("=============== flag " + flag);

        }

        long dt2 = new Date().getTime();
        System.out.println("================  dt2  " + dt2);

        System.out.println("======================== diff dt2 - dt1 " + (dt2 - dt1));
        System.out.println("======================== diff dt2 - dt0  " + (dt2 - dt0));


    }
}
