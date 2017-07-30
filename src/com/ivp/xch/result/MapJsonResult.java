package com.ivp.xch.result;

import java.util.LinkedList;
import java.util.List;

import com.ivp.xch.helper.JsonHelper;

/**
 * 返回Json 类型的数据
 * 
 * @author hcx
 *         2017-0712
 *
 * @param <T>
 */
public class MapJsonResult<T> extends Result {


    // {"type":"FeatureCollection",
    // "features":[
    // {"type":"Feature","properties":{"id":6,"name":"北京"},
    // "geometry":
    // {"type":"Polygon","coordinates":[[[117.35679568867022,40.25702627244448],[117.31059695758358,40.11047191005065],[117.23907677557281,40.0926177027448],[116.98767093336699,40.03435252494904],[116.82938602104804,40.03667796491612],[116.74422326119463,39.95900828710273],[116.78143029167472,39.88965851542821],[116.92219689257263,39.77359324783018],[116.87563642807868,39.686466783215565],[116.79341922401795,39.60231171281541],[116.60438683538416,39.611174221313405],[116.42827355418126,39.52141225836911],[116.38662234934213,39.44214061100047],[116.24978315616778,39.50580597576527],[116.21040571535138,39.57190013328457],[115.94540897034784,39.575388292335845],[115.75591149282116,39.50895823803222],[115.61845218292183,39.603732815217285],[115.46342288565768,39.643213608821014],[115.43732628837745,39.74930532478098],[115.51091352703781,39.83849884694433],[115.4964441259956,39.92363576927548],[115.42709435432107,39.96257396141925],[115.57762780218127,40.09631256827052],[115.81983524000441,40.151890570893215],[115.9452022638734,40.29371653898704],[115.72816124914243,40.539593003914035],[115.87445722911787,40.610234686781354],[115.99470828685799,40.59597199221372],[116.13035892072776,40.657983710579884],[116.20730512902855,40.75038117095434],[116.29143436100696,40.739890855378746],[116.45886600086527,40.791567287800234],[116.43819542897586,40.8997260613088],[116.65265262312016,41.0310100369837],[116.67580366370811,40.94013703089922],[116.91041466670356,40.74921845097086],[117.0580025569725,40.67720734344428],[117.19918256991969,40.690023098087465],[117.42852257715526,40.66904246693639],[117.41177941298974,40.597367255294614],[117.24310753788268,40.570159613974965],[117.1841447289961,40.49727000640564],[117.2379398949102,40.4501927761757],[117.25054894397863,40.33782237410338],[117.35679568867022,40.25702627244448]]]}}
    // ] }


    public static class D3Propertie {
        private String id;
        private String name;

        public D3Propertie(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


    // public class D3Coordinate{
    // private String id;
    //
    // }


    public static class D3Geometry {
        private String type = "Polygon";
        // private List<D3Coordinate> coordinates;
        private Double[][][] coordinates;

        public D3Geometry(Double[][][] co) {
            this.coordinates = co;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Double[][][] getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Double[][][] coordinates) {
            this.coordinates = coordinates;
        }

    }



    public static class D3Feature {
        private String type = "Feature";
        private D3Propertie properties;
        private D3Geometry geometry;

        public D3Feature(D3Propertie properties, D3Geometry geometry) {
            this.properties = properties;
            this.geometry = geometry;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public D3Propertie getProperties() {
            return properties;
        }

        public void setProperties(D3Propertie properties) {
            this.properties = properties;
        }

        public D3Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(D3Geometry geometry) {
            this.geometry = geometry;
        }


    }


    private String type = "FeatureCollection";  // 附加数据
    private List<D3Feature> features;
    private int total;


    // MapJsonResult(boolean success, List<D3Feature> features, int total, String msg) {
    // this.success = success;
    // this.features = features;
    // this.total = total;
    // this.message = msg;
    // }


    MapJsonResult(boolean success, Double[][][] getCoordinates, int total, String msg) {
        this.success = success;
        // this.features = features;
        this.total = total;
        this.message = msg;

        // D3Geometry d3G = new D3Geometry(getCoordinates);

        D3Feature d3F = new D3Feature(new D3Propertie("1", "坐标测试"), new D3Geometry(getCoordinates));
        this.features = new LinkedList<D3Feature>();
        this.features.add(d3F);
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<D3Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<D3Feature> features) {
        this.features = features;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public static <T> MapJsonResult<T> getSuccessResult(boolean success, Double[][][] getCoordinates, int total, String msg) {
        return new MapJsonResult<T>(success, getCoordinates, total, msg);
    }

    public static <T> MapJsonResult<T> getSuccessResult(Double[][][] getCoordinates, String msg) {
        return new MapJsonResult<T>(true, getCoordinates, getCoordinates.length, msg);
    }


    public static void main(String[] args) {

        Double[][][] aa = {
            {
                {
                    1234d, 2345d
            }, {
                3456d, 45d
            }, {
                67d, 89d
            }, {
                1d
            }
            }
        };

        D3Feature df = new D3Feature(new D3Propertie("1", "城市"), new D3Geometry(aa));
        // df.setProperties(new D3Propertie("1", "城市"));
        // df.setGeometry(new D3Geometry(aa));

        String js = JsonHelper.toJson(df);
        System.out.println("============== js \n" + js);

    }

}
