package no.nith.pg5100.dto;

public enum UserType {
    STUDENT("Student"),
    TEACHER("Teacher");

    private final String label;

    UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
