package codingon.spring_boot_security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    // error 발생시 에러 메세지를 반환하도록
    private String error;

    // Generic 을 이용해서 2xx 대 응답시 해당 type 의 리스트를 반환하도록
    private List<T> data;
}

// ResponseDTO 를 사용하는 이유?
// - 응답 형식의 일관성을 유지하기 위함
// - HTTP Response 할 때 사용하게 될 DTO
// - 서버에서 클라이언트로 응답할 떄 사용할 데이터 구조 정의