package com.company;

import java.time.LocalDate;
import java.util.Comparator;

public class Coach {
    private String coachID;
    private String name;
    private int hourlyRate;
    private LocalDate dateJoined;
    private LocalDate dateTerminated;
    private String phone;
    private String address;
    private final String sportsCenterID;
    private final String sportsCode;
    private final String sportsName;
    private int rating;
    private int totalRates;

    /*----------Class Constructor----------*/

    public Coach (String[] coachDetails){
        name = coachDetails[0];
        coachID = coachDetails[1];
        dateJoined = LocalDate.parse(coachDetails[2]);
        if (coachDetails[3].equals("null"))
            dateTerminated = null;
        else
            dateTerminated = LocalDate.parse(coachDetails[3]);
        hourlyRate = Integer.parseInt(coachDetails[4]);
        phone = coachDetails[5];
        address = coachDetails[6];
        sportsCenterID = coachDetails[7];
        sportsCode = coachDetails[8];
        rating = Integer.parseInt(coachDetails[9]);
        totalRates = Integer.parseInt(coachDetails[10]);
        sportsName = coachDetails[11];
    }

    /*----------Sorting methods----------*/

    public static class sortByRating implements Comparator<Coach> {

        @Override
        public int compare(Coach coach1, Coach coach2) {
            return (int)((coach1.rating-coach2.rating)*10);
        }

        @Override
        public String toString() {
            return "Sort by Rating";
        }

    }
    public static class sortByPay implements Comparator<Coach>{
        @Override
        public int compare(Coach coach1, Coach coach2) {
            return (int) ((coach1.hourlyRate-coach2.hourlyRate)*10);
        }

        @Override
        public String toString() {
            return "Sort by Hourly Rate";
        }

    }
    public static class sortByID implements Comparator<Coach>{
        @Override
        public int compare(Coach coach1, Coach coach2) {
            return coach1.coachID.compareTo(coach2.coachID);
        }

        @Override
        public String toString () {
            return "Sort by Coach ID";
        }

    }

    public static String[] getAllAttributes () {
        return new String[] {"Coach ID","Name","Date Joined","Date Terminated","Hourly Rate","Contact Number","Address","Sports Center ID","Sports Code","Sports Name","Rating","Total Feedback"};
    }

    /*----------Getters and Setters----------*/

    public String getCoachID() {
        return coachID;
    }

    public String getName() {
        return name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public LocalDate getDateTerminated() {
        return dateTerminated;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getSportsCenterID() {
        return sportsCenterID;
    }

    public String getSportsCode() {
        return sportsCode;
    }

    public int getRating() {
        return rating;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public int getTotalRates() {
        return totalRates;
    }

    public void setTotalRates(int totalRates) {
        this.totalRates = totalRates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setDateTerminated(LocalDate dateTerminated) {
        this.dateTerminated = dateTerminated;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    /*----------To String methods----------*/

    @Override
    public String toString () {

        return name + "|"+ coachID + "|" + dateJoined + "|" + dateTerminated + "|" + hourlyRate + "|"
                + phone + "|" + address + "|" + sportsCenterID + "|"+ sportsCode + "|" + rating + "|" + totalRates + "|" + sportsName;
    }

    public String getDisplayString() {
        int displayRating = 0;
        try{
            displayRating = rating/totalRates;
        }catch (ArithmeticException ignored){}
        return name + "|"+ coachID + "|" + dateJoined + "|" + dateTerminated + "|" + hourlyRate + "|"
                + phone + "|" + address + "|" + sportsCenterID + "|"+ sportsCode + "|" + sportsName+ "|"+ displayRating + "|" + totalRates;
    }
}
