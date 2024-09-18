package api;

public class CompanyPojo {
    private int id;
    private double locationX;
    private double locationY;
    private String startTime;
    private String endTime;

    // 生成 Getters 和 Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public CompanyPojo(CompanyPojo pojo1){
        id = pojo1.getId();

        locationX = pojo1.getLocationX();
        locationY = pojo1.getLocationY();
        startTime = pojo1.getStartTime();
        endTime = pojo1.getEndTime();

    }

    public CompanyPojo(){


    }


}
