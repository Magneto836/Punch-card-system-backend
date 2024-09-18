package api;
import api.CompanyPojo;
import java.util.List;

public class CompanyApiResponse extends ApiResponse{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private CompanyPojo records;  // 对应 JSON 中的 "records"

        public CompanyPojo getRecords() {
            return records;
        }

        public void setRecords(CompanyPojo records) {
            this.records = records;
        }
    }









}
