package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class FlightDelayApp {

    private static final int ORIGIN_AIRPORT_ID_POSITION = 11;
    private static final int DEST_AIRPORT_ID_POSITION = 14;
    private static final int ARR_DELAY_POSITION = 18;

    public static final String DELETE_SYMBOL = "\"";
    public static final String SPLITTER = ",";

    public static String[] removeAndSplit(String s) {
        return s.replaceAll(DELETE_SYMBOL, "").split(SPLITTER);
    }

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flightsTextFile = sc.textFile("664600583_T_ONTIME_sample.csv");
        JavaRDD<String> airportsTextFile = sc.textFile("L_AIRPORT_ID.csv");

        JavaPairRDD<String, Long> f = flightsTextFile.mapToPair(
                value -> {
                    String[] flightDescription = removeAndSplit(value);

                    int originAirportID = Integer.parseInt(flightDescription[ORIGIN_AIRPORT_ID_POSITION]);
                    int destAirportID = Integer.parseInt(flightDescription[DEST_AIRPORT_ID_POSITION]);

                    float flightDelayTime = Float.parseFloat(flightDescription[ARR_DELAY_POSITION]);
                    if (flightDescription[ARR_DELAY_POSITION].isEmpty()) {
                        flightDelayTime = 0;
                    }

                    

                    // return ;
                }
        );
    }
}