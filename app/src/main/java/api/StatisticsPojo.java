package api;

public class StatisticsPojo {

    private int  id;
    private String user_id;
    private double total_hours;
    private int month;
    private int ranking;
    private int year;
    private String username;

    public double getTotal_hours(){
        return total_hours ;
    }


    public String getUsername() {
        return username;
    }

    public double getTotalHours() {
        return total_hours ;
    }

    public int getRanking(){

        return ranking ;
    }

}
