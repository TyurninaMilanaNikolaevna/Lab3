package lab3;

import java.io.Serializable;

public class FlightDelaySerializable implements Serializable {
    private float flightDelayTime;

    private float maxFlightDelayTime;
    private float delayAndCancelledFlightPercent;
    private int counter = 1;

    public FlightDelaySerializable(boolean isCancelledFlight, float flightDelayTime) {
        this.flightDelayTime = flightDelayTime;

        if (flightDelayTime > 0 || isCancelledFlight) {
            this.delayAndCancelledFlightPercent = 100.0F;
        }
        else this.delayAndCancelledFlightPercent = 0.0F;
    }

    public FlightDelaySerializable(float maxFlightDelayTime, float delayAndCancelledFlightPercent, int counter) {
        this.maxFlightDelayTime = maxFlightDelayTime;
        this.delayAndCancelledFlightPercent = delayAndCancelledFlightPercent;
        this.counter = counter;
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

    public float getMaxFlightDelayTime() {
        return maxFlightDelayTime;
    }

    @Override
    public String toString() {
        return 
    }
}