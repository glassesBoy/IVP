package com.ivp.xch.map.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.map.FieldGPS;
import com.ivp.xch.sample.BaiduPoint;


/**
 * 路线controller
 * 
 * @author hcx
 *
 */
@WebServlet(name = "TestController", urlPatterns = {
    "/c/m/track"
})
public class TestController extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 1269983896806005248L;



    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/track")
    public void testFieldGPS() {

        // 线段的起点和重点
        double lat1 = 31.5936816, lng1 = 120.7764291;
        // 31.5926768 120.7775058
        double lat2 = 1.5926768, lng2 = 120.7775058;



    }



    /**
     * 计算某个经纬度的周围某段距离的正方形的四个点
     * 地球半径，平均半径为6371km
     * 
     * @param lng float 经度
     * @param lat float 纬度
     * @param distance float 该点所在圆的半径，该圆与此正方形内切，默认值为0.5千米
     * @return array 正方形的四个点的经纬度坐标
     */
    // public returnSquarePoint(lng, lat,distance){
    //
    // $dlng = 2 * asin(sin($distance / (2 * 6371)) / cos(deg2rad($lat)));
    // $dlng = rad2deg($dlng);
    //
    // $dlat = $distance/6371;
    // $dlat = rad2deg($dlat);
    //
    // return array(
    // 'left-top'=>array('lat'=>$lat + $dlat,'lng'=>$lng-$dlng),
    // 'right-top'=>array('lat'=>$lat + $dlat, 'lng'=>$lng + $dlng),
    // 'left-bottom'=>array('lat'=>$lat - $dlat, 'lng'=>$lng - $dlng),
    // 'right-bottom'=>array('lat'=>$lat - $dlat, 'lng'=>$lng + $dlng)
    // );
    // }



    // 将角度转换为弧度
    private static double deg2rad(double degree) {
        return degree * Math.PI / 180.0;
    }

    // 将弧度转换为角度
    static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }

    /**
     * 
     * @param center 中心坐标
     * @param distace 到中心的距离(正方形内切圆的半径）单位*米
     * @return 左上 右上 左下 右下 4个GPS坐标点
     */
    // public static List<Coord> get4limit(Coord center, double distace_m) {
    // double distace = distace_m / 1000;// 换成千米
    // List<Coord> coords = new ArrayList<Coord>();
    // double x = center.getX();
    // double y = center.getY();
    //
    // double dx = 2 * Math.asin(Math.sin(distace / (2 * 6371))
    // / Math.cos(deg2rad(y)));
    // dx = rad2deg(dx);
    //
    // double dy = distace / 6371;
    // dy = rad2deg(dy);
    //
    // Coord left_top = new Coord(x - dx, y + dy);
    // Coord right_top = new Coord(x + dx, y + dy);
    // Coord left_bottom = new Coord(x - dx, y - dy);
    // Coord right_bottom = new Coord(x + dx, y - dy);
    // coords.add(left_top);
    // coords.add(right_top);
    // coords.add(left_bottom);
    // coords.add(right_bottom);
    // return coords;
    // }



}
