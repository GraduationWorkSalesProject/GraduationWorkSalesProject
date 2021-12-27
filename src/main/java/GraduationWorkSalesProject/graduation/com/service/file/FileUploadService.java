package GraduationWorkSalesProject.graduation.com.service.file;

import GraduationWorkSalesProject.graduation.com.vo.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    Image saveTest(MultipartFile file) throws IOException;
    String generateFileName(String fileName);
    String getExtension(String fileName);

}
