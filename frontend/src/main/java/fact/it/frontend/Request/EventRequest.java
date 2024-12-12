package fact.it.frontend.Request;

import java.time.LocalDate;

public class EventRequest {

    private String name;
    private LocalDate date;
    private String location;

    // Constructor
    public EventRequest(String name, LocalDate date, String location) {
        this.name = name;
        this.date = date;
        this.location = location;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Optional: Override toString() for easy debugging
    @Override
    public String toString() {
        return "EventRequest{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                '}';
    }
}

