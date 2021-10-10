package GraduationWorkSalesProject.graduation.com.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultResponse {

    private int status;
    private String code;
    private String message;

    public ResultResponse(ResultCode resultCode) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
