package api;

import java.util.List;

public class StsApiResponse extends ApiResponse{

    private List<StatisticsPojo> records;  // JSON 数组映射为 List

    public List<StatisticsPojo> getRecords() {
        return records;
    }

    public void setRecords(List<StatisticsPojo> records) {
        this.records = records;
    }

}
