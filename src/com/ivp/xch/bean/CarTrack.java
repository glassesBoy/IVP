package com.ivp.xch.bean;

import java.awt.geom.Line2D;
import java.util.List;

import javax.persistence.EntityManager;

import com.ivp.xch.db.DBEM;
import com.ivp.xch.map.FieldGPS;
import com.ivp.xch.map.LineSegment;

/**
 * 车辆跟踪类
 * 
 * @author hcx
 *
 */
public class CarTrack {

    // private List<FieldGPS> gpsList = null;
    private static CarTrack lin = null;
    private Car car;

    private List<LineSegment> lineList = null;


    private CarTrack(Car car) {
        this.car = car;
        // 这里先查询出来所有的 点
        EntityManager em = DBEM.em();
        /// gpsList = em.createQuery(" select f from FieldGPS f where f.section = 'L0' ", FieldGPS.class).getResultList();
        lineList = em.createQuery("  select f from  LineSegment  f   ", LineSegment.class).getResultList();
        em.close();
    }


    public static CarTrack getLineHelper(Car car) {
        if (lin == null) {
            lin = new CarTrack(car);
        }
        return lin;
    }

    public boolean isIntersect(Point p, double angle) {
        return isIntersect(p.getX(), p.getY(), angle);
    }

    /**
     * 小车中心点
     * 
     * @param x1
     * @param y1
     * @return
     */
    public boolean isIntersect(double x1, double y1, double angle) {

        for (int i = 0; i < lineList.size() - 1; i++) {

            LineSegment lineSeg = lineList.get(i);

            // double angle = gps.getHeading();
            // System.out.println("===============Car 11111111111111 坐标 X:" + x1 + ", Y: " + y1 + " 航向角 " + angle);
            Point[] pts = car.getPoints(angle, new Point(x1, y1));

            // for (Point point : pts) {
            // System.out.println("================= "+pts[0].getX() + pts[0].getY()+ pts[2].getX()+ pts[2].getY());
            // }

            boolean flag0 = Line2D.linesIntersect(pts[0].getX(), pts[0].getY(), pts[2].getX(), pts[2].getY(), lineSeg.getSx(), lineSeg.getSy(), lineSeg.getEx(),
                    lineSeg.getEy());
            if (flag0) {
                // System.out.println("===============Car 11111111111111 坐标 X:" + x1 + ", Y: " + y1 + " 航向角 " + angle);
                // System.out.println("===============线段 sX:" + lineSeg.getSx() + ", sY: " + lineSeg.getSy() + " eX " + lineSeg.getEx() + " eY " + lineSeg.getEy());
                return true;
            }

            boolean flag1 = Line2D.linesIntersect(pts[0].getX(), pts[0].getY(), pts[3].getX(), pts[3].getY(), lineSeg.getSx(), lineSeg.getSy(), lineSeg.getEx(),
                    lineSeg.getEy());
            // System.out.println("=============== p0 p3 flag1 " + flag1);
            if (flag1) {
                // System.out.println("===============Car 2222222222222 坐标 X:" + x1 + ", Y: " + y1 + " 航向角 " + angle);
                // System.out.println("===============线段 sX:" + lineSeg.getSx() + ", sY: " + lineSeg.getSy() + " eX " + lineSeg.getEx() + " eY " + lineSeg.getEy());

                return true;
            }
            boolean flag2 = Line2D.linesIntersect(pts[2].getX(), pts[2].getY(), pts[1].getX(), pts[1].getY(), lineSeg.getSx(), lineSeg.getSy(), lineSeg.getEx(),
                    lineSeg.getEy());
            // System.out.println("=============== p2 p1 flag2 " + flag2);
            if (flag2) {
                // System.out.println("===============Car 33333333333333 坐标 X:" + x1 + ", Y: " + y1 + " 航向角 " + angle);
                // System.out.println("===============线段 sX:" + lineSeg.getSx() + ", sY: " + lineSeg.getSy() + " eX " + lineSeg.getEx() + " eY " + lineSeg.getEy());

                return true;
            }
            boolean flag3 = Line2D.linesIntersect(pts[3].getX(), pts[3].getY(), pts[1].getX(), pts[1].getY(), lineSeg.getSx(), lineSeg.getSy(), lineSeg.getEx(),
                    lineSeg.getEy());
            // System.out.println("=============== p2 p3 flag3 " + flag3);
            if (flag3) {
                // System.out.println("===============Car 44444444444 坐标 X:" + x1 + ", Y: " + y1 + " 航向角 " + angle);
                // System.out.println("===============线段 sX:" + lineSeg.getSx() + ", sY: " + lineSeg.getSy() + " eX " + lineSeg.getEx() + " eY " + lineSeg.getEy());
                return true;
            }

            /* for (Point point : pts) {
                double x = point.getX();
                double y = point.getY();
            
                boolean flag = Line2D.linesIntersect(x, y, x2, y2, gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(), gps1.getLatitude());
                System.out.println("=============== flag  " + flag);
            }*/

            // boolean flag = Line2D.linesIntersect(x1, y1, x2, y2, gps.getLongitude(), gps.getLatitude(), gps1.getLongitude(), gps1.getLatitude());
            // System.out.println("=============== flag " + flag);

        }
        return false;
    }

}
