package model;

public enum ArrivalTimeStatus {
    LOGGEDIN,
    LOGGEDOUT;

    public static ArrivalTimeStatus StringToStatus(String status)
    {
        return switch (status) {
            case "LOGGEDIN" -> LOGGEDIN;
            case "LOGGEDOUT" -> LOGGEDOUT;
            default -> null;
        };
    }
}
