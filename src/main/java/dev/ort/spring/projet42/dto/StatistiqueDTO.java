package dev.ort.spring.projet42.dto;

public class StatistiqueDTO {
    private Long numberOfEvents;  // Changed to Long
    private Double totalDistance;

    public Long getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(Long numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }
}
