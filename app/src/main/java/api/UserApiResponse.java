package api;

public class UserApiResponse extends ApiResponse{
    private Data data ;
    public Data getData(){
        return data ;
    }

    public static class Data {
        private int user_id;
        private String role ;
        // Getter 方法
        public int getUserId() {
            return user_id;
        }
        public String getRole(){
            return role ;
        }
    }


}
