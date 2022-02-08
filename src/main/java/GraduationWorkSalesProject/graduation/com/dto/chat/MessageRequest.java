package GraduationWorkSalesProject.graduation.com.dto.chat;

import GraduationWorkSalesProject.graduation.com.entity.chat.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MessageRequest {

    @NotNull
    private Long roomId;

    @Length(max = 500)
    @NotEmpty
    private String content;

    @NotBlank
    private Message.MessageType type;
}
