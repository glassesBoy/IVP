package com.ivp.xch.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ivp.xch.bean.Car;
import com.ivp.xch.bean.CarTrack;
import com.ivp.xch.db.DBEM;

/**
 * 小车轨迹
 * 
 * @author hcx
 *
 */
@Entity
@Table(name = "TLINE_STRACK")
public class LineStrack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, precision = 10, scale = 0)
    protected Long id;

    // 小车ID
    private Long carID;

    /**
     * HPD消息协议头
     * 现在有两种类型的 GPS数据
     * GPHPD 现在使用这个，数据比较多，全面
     * GPGGA
     */
    private String gpsDataType;



    /**
     * 自1980-1-6至当前的星期数（接收机时间）
     * 例如1451
     */
    private int GPSWeek;

    // numeric 368123.30 星期内的毫秒数（接收机时间）
    @Column(precision = 15, scale = 10)
    private Double GPSTime;

    // numeric 90.01 度 偏航角0~360
    @Column(precision = 15, scale = 10)
    private Double heading;

    // numeric 0.132 度 俯仰角-90～90
    @Column(precision = 15, scale = 10)
    private Double pitch;

    // numeric 90.11 度 地速相对真北方向的夹角（0-359.99）
    @Column(precision = 15, scale = 10)
    private Double track;

    // numeric 34.1966004 度 纬度（WGS84）
    @Column(precision = 15, scale = 10)
    private Double latitude;

    // numeric 108.8551924 度 经度（WGS84）
    @Column(precision = 15, scale = 10)
    private Double longitude;

    // numeric 394.98 米 高度（WGS84）
    @Column(precision = 15, scale = 10)
    private Double altitude;

    // numeric -0.157 米/秒 东向速度
    @Column(precision = 15, scale = 10)
    private Double ve;

    // numeric 0.019 米/秒 北向速度
    @Column(precision = 15, scale = 10)
    private Double vn;

    // numeric -0.345 米/秒 天向速度
    @Column(precision = 15, scale = 10)
    private Double vu;

    // numeric -0.273 米/秒 两次测量值之间的东向速度差
    @Column(precision = 15, scale = 10)
    private Double ae;

    // numeric 0.166 米/秒 两次测量值之间的北向速度差
    @Column(precision = 15, scale = 10)
    private Double an;

    // numeric -0.248 米/秒 两次测量值之间的天向速度差
    @Column(precision = 15, scale = 10)
    private Double au;

    // numeric 3.898 米 基线长度
    @Column(precision = 15, scale = 10)
    private Double baseline;

    // numeric 6 前天线可用星数
    // @Column(columnDefinition="double(10,10)")
    private Float NSV1;

    // numeric 6 后天线可用星数
    // @Column(columnDefinition="double(10,10)")
    private Float NSV2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }


    public String getGpsDataType() {
        return gpsDataType;
    }

    public void setGpsDataType(String gpsDataType) {
        this.gpsDataType = gpsDataType;
    }

    public int getGPSWeek() {
        return GPSWeek;
    }

    public void setGPSWeek(int gPSWeek) {
        GPSWeek = gPSWeek;
    }

    public Double getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(Double gPSTime) {
        GPSTime = gPSTime;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getTrack() {
        return track;
    }

    public void setTrack(Double track) {
        this.track = track;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getVe() {
        return ve;
    }

    public void setVe(Double ve) {
        this.ve = ve;
    }

    public Double getVn() {
        return vn;
    }

    public void setVn(Double vn) {
        this.vn = vn;
    }

    public Double getVu() {
        return vu;
    }

    public void setVu(Double vu) {
        this.vu = vu;
    }

    public Double getAe() {
        return ae;
    }

    public void setAe(Double ae) {
        this.ae = ae;
    }

    public Double getAn() {
        return an;
    }

    public void setAn(Double an) {
        this.an = an;
    }

    public Double getAu() {
        return au;
    }

    public void setAu(Double au) {
        this.au = au;
    }

    public Double getBaseline() {
        return baseline;
    }

    public void setBaseline(Double baseline) {
        this.baseline = baseline;
    }


    // private Double cs hexadecimal *0B 效验


    public Float getNSV1() {
        return NSV1;
    }

    public void setNSV1(Float nSV1) {
        NSV1 = nSV1;
    }

    public Float getNSV2() {
        return NSV2;
    }

    public void setNSV2(Float nSV2) {
        NSV2 = nSV2;
    }

    public static void importFile(File file) {
        String encoding = "GBK";
        InputStreamReader read = null;
        try {
            read = new InputStreamReader(
                    new FileInputStream(file), encoding);
            // 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            // StringBuilder sbuilder = new StringBuilder();

            EntityManager em = DBEM.em();
            em.getTransaction().begin();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                System.out.println("===== " + lineTxt);
                // sbuilder +=;
                // 如果 开头是 $GPHPD
                if (lineTxt.startsWith("$GPHPD")) {
                    String[] gpsData = lineTxt.split(",");
                    LineStrack f = new LineStrack();
                    f.setCarID(1l);
                    f.setGpsDataType("GPHPD");
                    f.setGPSWeek(Integer.valueOf(gpsData[1]));
                    f.setGPSTime(Double.valueOf(gpsData[2]));
                    f.setHeading(Double.valueOf(gpsData[3]));
                    f.setPitch(Double.valueOf(gpsData[4]));
                    f.setTrack(Double.valueOf(gpsData[5]));
                    f.setLatitude(Double.valueOf(gpsData[6]));
                    f.setLongitude(Double.valueOf(gpsData[7]));
                    f.setAltitude(Double.valueOf(gpsData[8]));
                    f.setVe(Double.valueOf(gpsData[9]));
                    f.setVn(Double.valueOf(gpsData[10]));
                    f.setVu(Double.valueOf(gpsData[11]));
                    f.setAe(Double.valueOf(gpsData[12]));
                    f.setAn(Double.valueOf(gpsData[13]));
                    f.setBaseline(Double.valueOf(gpsData[14]));
                    f.setNSV1(Float.valueOf(gpsData[15]));
                    // f.setSection(file.getName().split("\\.")[0]);

                    em.persist(f);

                }

                // 如果 开头是 $GPGGA
            }
            em.getTransaction().commit();
            em.close();
            read.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // String path = "D:\\Gitsts\\IVP\\WebContent\\mapRaw\\CESHI.DAT";
        // File file = new File(path);
        // importFile(file);
        // System.out.println("=====================*********************** Over ");



        Car car = new Car(20, 10);
        CarTrack ctrack = CarTrack.getLineHelper(car);

        EntityManager em = DBEM.em();
        List<LineStrack> pList = em.createQuery("  select f from  LineStrack  f where f.carID = 1  ", LineStrack.class).getResultList();
        em.close();

        int bb = 0;
        for (int i = 0; i < pList.size(); i++) {
            LineStrack ltrack = pList.get(i);

            boolean aa = ctrack.isIntersect(ltrack.getLongitude(), ltrack.getLatitude(), ltrack.getHeading());
            if (aa) {
                bb++;
            }
        }
        System.out.println("================= 压线次数  " + bb);



    }

}
