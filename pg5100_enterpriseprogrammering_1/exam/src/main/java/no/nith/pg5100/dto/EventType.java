package no.nith.pg5100.dto;

public enum EventType {
    LECTURE("Lecture"),
    PRACTICE("Practice");

    private final String label;

    EventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
