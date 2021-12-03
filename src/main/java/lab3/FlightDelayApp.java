package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class FlightDelayApp {

    private static final int originAirportID = 11;
    private static final int destAirportID = 14;

    public static final String SPLITTER = ",";

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flightsInformation = sc.textFile("664600583_T_ONTIME_sample.csv");
        JavaRDD<String> airportsInformation = sc.textFile("L_AIRPORT_ID.csv");

        JavaPairRDD<String, Long> f = flightsInformation.mapToPair(
                s -> {
                    String[] flightDescription = value.toString()
                            .replaceAll("\"", "")
                            .split(SPLITTER);
                }
        );
    }
}