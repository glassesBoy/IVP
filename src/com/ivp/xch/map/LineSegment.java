package com.ivp.xch.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ivp.xch.db.DBEM;

/**
 * 所有线段
 * 
 * @author hcx
 *
 */
@Entity
@Table(name = "TFIELD_LINE_SEGMENT")
public class LineSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, precision = 10, scale = 0)
    protected Long id;

    // 起点 x
    private double sx;

    // 起点 y
    private double sy;

    // 终点 x
    private double ex;

    // 终点 y
    private double ey;


    private String section;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSx() {
        return sx;
    }

    public void setSx(double sx) {
        this.sx = sx;
    }

    public double getSy() {
        return sy;
    }

    public void setSy(double sy) {
        this.sy = sy;
    }

    public double getEx() {
        return ex;
    }

    public void setEx(double ex) {
        this.ex = ex;
    }

    public double getEy() {
        return ey;
    }

    public void setEy(double ey) {
        this.ey = ey;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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


            LineSegment f = new LineSegment();
            // 取前三条
            boolean startPoint = false;
            for (int i = 0; i < 3; i++) {
                // 只采集第一个点
                if (startPoint == true) {
                    break;
                }

                if ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.startsWith("$GPHPD")) {
                        String[] gpsData = lineTxt.split(",");
                        f.setSx(Double.valueOf(gpsData[7]));
                        f.setSy(Double.valueOf(gpsData[6]));
                        if(file.getName().indexOf("L") > 0){
                            f.setSection( file.getName().split("-")[1].split("\\.")[0]);
                        }else {
                            f.setSection("L"+file.getName().split("\\.")[0]);
                        }
                    }
                }
            }

            double x = 0;
            double y = 0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                System.out.println("===== " + lineTxt);
                // sbuilder +=;
                // 如果 开头是 $GPHPD
                if (lineTxt.startsWith("$GPHPD")) {
                    String[] gpsData = lineTxt.split(",");
                    x = Double.valueOf(gpsData[7]);
                    y = Double.valueOf(gpsData[6]);
                }
                // 如果 开头是 $GPGGA
            }

            f.setEx(x);
            f.setEy(y);

            em.persist(f);

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


        String path = "D:\\Gitsts\\IVP\\WebContent\\mapRaw";
        File file = new File(path);
        File[] tempList = file.listFiles();
        System.out.println("该目录下对象个数：" + tempList.length);
        // String encoding = "GBK";
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文     件：" + tempList[i].getName());
                importFile(tempList[i]);
            } else {
                // if (tempList[i].isDirectory()) {
                // System.out.println("文件夹：" + tempList[i]);

                // importFolder(tempList[i]);
            }
        }
        System.out.println("=====================***********************    Over ");


    }

}
