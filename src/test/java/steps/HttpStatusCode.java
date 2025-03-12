package steps;

public enum HttpStatusCode {

        OK(200),
        OK_post(201),
        Not_found(404);

        private Integer code;


        private HttpStatusCode (Integer code)
        {
            this.code=code;
    }


    public Integer getCode() {
        return code;
    }

       public void setCode(Integer code) {
        this.code = code;
    }
}
