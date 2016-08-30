package com.thisisswitch.otrolly.driver.events;

/**
 * Created by Office on 4/10/2016.
 */
public class TripStatusCodes {

    public static int created = 1000;
    public static int requestAccepted = 1002; //lets the customer app know that a trip has been accepted.
    public static int allRequestsIgnored = 1003;
    public static int allDropOffPointsAdded = 1004;
    public static int reachedPickupLocation = 1005;//lets the customer app know that the driver has reached the pickup location.
    public static int customerCancellation = 1007;
    public static int driverCancellation = 1008;
    public static int complete = 1009;
    public static int EnrouteToPickupLocation = 1010;
}
