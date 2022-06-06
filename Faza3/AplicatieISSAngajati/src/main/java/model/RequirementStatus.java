package model;

public enum RequirementStatus {
    ACTIVE,
    SOLVED;

    public static RequirementStatus StringToStatus(String status)
    {
        return switch (status) {
            case "ACTIVE" -> ACTIVE;
            case "SOLVED" -> SOLVED;
            default -> null;
        };
    }
}
