package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Sports {
    private String name;
    private String sportsID;
    private int sportFees;
    private Schedule schedule;
    public static class sortByFees implements Comparator<Sports> {

        @Override
        public int compare(Sports o1, Sports o2) {
            return o1.sportFees - o2.sportFees;
        }

        @Override
        public String toString() {
            return "Sort by Sport Fees";
        }
    }
    public static class sortByName implements Comparator<Sports>{

        @Override
        public int compare(Sports o1, Sports o2) {
            return o1.name.compareTo(o2.name);
        }

        @Override
        public String toString(){
            return "Sort by Sport Name";
        }
    }
    public Sports (String sportCenterCode, String[] details){
        name = details[0];
        sportsID = details[1];
        sportFees = Integer.parseInt(details[2]);
        schedule = new Schedule(sportCenterCode,name,details[3].split(","));
    }
    public static String[] getAllAttributes () {
        return new String[]{"Name","Sports ID","Sport Fees"};
    }

    public String toString() {
        return name + "|" + sportsID + "|" + sportFees + "|" + schedule.toString() ;
    }
    public Schedule getSchedule() {
        return schedule;
    }

    public String getSportsID (){return sportsID;}
    public String getName() {return name;}

    public int getSportFees() {
        return sportFees;
    }
}
