package fact.it.frontend.Request;

import java.time.LocalDate;

public class RideRequest {

    private String name;
    private String type;
    private int capacity;
    private int duration;

    // Constructor
    public RideRequest(String name, String type, int capacity, int duration) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

