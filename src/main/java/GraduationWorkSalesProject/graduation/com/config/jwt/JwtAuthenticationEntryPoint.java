package GraduationWorkSalesProject.graduation.com.config.jwt;

import GraduationWorkSalesProject.graduation.com.dto.error.ErrorCode;
import GraduationWorkSalesProject.graduation.com.dto.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // TODO: [403 error handling]
    //  https://sas-study.tistory.com/362 참고
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute("errorCode");
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (OutputStream os = response.getOutputStream()){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.of(errorCode));
            os.flush();
        }
    }
}
