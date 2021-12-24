package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ResponseData {
    private String result;
    private Object data;
    private String message;
    private HttpStatus code;

    public ResponseData( String result, String message) {
        this.result = result;
        this.message = message;
        this.code = HttpStatus.BAD_REQUEST;
    }

    public ResponseData(Object data) {
        this.data = data;
        this.result = "success";
        this.code = HttpStatus.OK;
    }

    public JSONObject toJson(){
        return new JSONObject(this);
    }
}
