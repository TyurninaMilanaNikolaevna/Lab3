package lab3;

import java.io.Serializable;

public class FlightDelaySerializable implements Serializable {
    private boolean isCancelledFlight;
    private float flightDelayTime;

    public FlightDelaySerializable(boolean isCancelledFlight, float flightDelayTime) {
        this.isCancelledFlight = isCancelledFlight;
        this.flightDelayTime = flightDelayTime;
    }
}