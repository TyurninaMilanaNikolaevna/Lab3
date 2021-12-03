package lab3;

import java.io.Serializable;

public class FlightDelaySerializable implements Serializable {
    private boolean isCancelledFlight;
    private float flightDelayTime;

    private float maxFlightDelayTime;
    private float delayAndCancelledFlightPercent;
    private int counter = 1;

    public FlightDelaySerializable(boolean isCancelledFlight, float flightDelayTime) {
        this.isCancelledFlight = isCancelledFlight;
        this.flightDelayTime = flightDelayTime;
    }

    public FlightDelaySerializable() {

    }

    public float getFlightDelayTime() {
        return flightDelayTime;
    }

    public int getCounter() {
        return counter;
    }

    public float getDelayAndCancelledFlightPercent() {
        return delayAndCancelledFlightPercent;
    }

}