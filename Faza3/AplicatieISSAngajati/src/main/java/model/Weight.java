package model;

public enum Weight {
    URGENT,
    IMPORTANT,
    NORMAL;

    public static Weight StringToWeight(String status)
    {
        return switch (status) {
            case "URGENT" -> URGENT;
            case "IMPORTANT" -> IMPORTANT;
            case "NORMAL" -> NORMAL;
            default -> null;
        };
    }
}
