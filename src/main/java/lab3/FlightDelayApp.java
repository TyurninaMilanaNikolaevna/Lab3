package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

public class FlightDelayApp {

    private static final float ZERO = 0.0F;

    private static final int AIRPORT_CODE_POSITION = 0;
    private static final int AIRPORT_DESCRIPTION_POSITION = 1;
    private static final int ORIGIN_AIRPORT_ID_POSITION = 11;
    private static final int DEST_AIRPORT_ID_POSITION = 14;
    private static final int ARR_DELAY_POSITION = 18;
    private static final int CANCELLED_POSITION = 19;

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

        

        JavaPairRDD<Tuple2, FlightDelaySerializable> flightInformation = flightsTextFile.mapToPair(
                value -> {
                    String[] flightDescription = removeAndSplit(value);

                    int originAirportID = Integer.parseInt(flightDescription[ORIGIN_AIRPORT_ID_POSITION]);
                    int destAirportID = Integer.parseInt(flightDescription[DEST_AIRPORT_ID_POSITION]);

                    boolean isCancelledFlight;
                    float flightDelayTime;

                    isCancelledFlight = Float.parseFloat(flightDescription[CANCELLED_POSITION]) != ZERO;

                    if (isCancelledFlight) {
                        flightDelayTime = 0;
                    }
                    else flightDelayTime = Float.parseFloat(flightDescription[ARR_DELAY_POSITION]);

                    return new Tuple2<>(new Tuple2(originAirportID, destAirportID),
                            new FlightDelaySerializable(isCancelledFlight, flightDelayTime));
                }
        );

        JavaPairRDD<Tuple2, FlightDelaySerializable> resultInformation = flightInformation.reduceByKey(
                (x, y) -> {
                    float maxDelayTime = Math.max(x.getFlightDelayTime(), y.getFlightDelayTime());
                    int sumCounter = x.getCounter() + y.getCounter();
                    float delayAndCancelledPercent = (x.getDelayAndCancelledFlightPercent() * x.getCounter() +
                            y.getDelayAndCancelledFlightPercent() * y.getCounter()) / sumCounter;

                    return new FlightDelaySerializable(maxDelayTime, delayAndCancelledPercent, sumCounter);
                }
        );

        Map<Integer, String> airportInformation = airportsTextFile.mapToPair(
                value -> {
                    String[] airportCodeAndDescription = removeAndSplit(value);
                    int airportCode = Integer.parseInt(airportCodeAndDescription[AIRPORT_CODE_POSITION]);
                    String airportDescription = airportCodeAndDescription[AIRPORT_DESCRIPTION_POSITION];

                    return new Tuple2<Integer, String>(airportCode, airportDescription);
                }
        ).collectAsMap();

        final Broadcast<Map<Integer, String>> airportsBroadcasted = sc.broadcast(airportInformation);


        JavaRDD<String> res = resultInformation.map(
            value -> {
                return "\nORIGIN AIRPORT ID: " + airportsBroadcasted.value().get(value._1._1) +
                        "\nDEST AIRPORT ID: " + airportsBroadcasted.value().get(value._1._2) +
                        "\nMAX FLIGHT DELAY TIME: " + value._2.getMaxFlightDelayTime() +
                        "\nDELAY AND CANCELLED FLIGHT PERCENT: " + value._2.getDelayAndCancelledFlightPercent();
            }
        );
        res.saveAsTextFile("resultLab3.txt");
    }
}