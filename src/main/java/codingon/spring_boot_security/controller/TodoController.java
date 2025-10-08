package codingon.spring_boot_security.controller;

import codingon.spring_boot_security.dto.ResponseDTO;
import codingon.spring_boot_security.dto.TodoDTO;
import codingon.spring_boot_security.entity.TodoEntity;
import codingon.spring_boot_security.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService service;

    // ResponseEntity 란
    // - 해당 객체를 이용해 상태코드, 응답 본문 등을 설정해서 클라이언트 응답
    // - HTTP 응답의 상태코드와 헤더를 퐇마해 더 세부적으로 제어
    // 메서드
    // - ok(): 성공
    // - headers(): 응답 헤더 설정
    // - body(): 응답 본문 설정
    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        // @AuthenticationPrincipal
        // - 현재 인증된 사용자 정보에 접근할 수 있게 함
        // - Spring Security 는 security context 에서 현재 인증된 사용자의 principal 을 가져옴
        // 우리 코드에서는) jwtAuthenticationFilter 클래스에서 userId 를 바탕으로 인증 객체 생성
        try {
            // TODO: 임시 유저 하드코딩한 부분으로 추후 로그인된 유저로 변경 필요
//            String temporaryUserId = "temporary-user";

            // (1) dto to entity
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) 생성하는 당시에는 id(pk) 는 null 로 초기화
            // - 새로 생성하는 레코드(행) 이기 때문
            entity.setId(null);

            // (3) 유저 아이디 설정 ("누가" 생성한 투두인지를 설정)
            // TODO: 임시 유저 하드코딩한 부분으로 추후 로그인된 유저로 변경 필요
//            entity.setUserId(temporaryUserId);
            // 기존 temporaryUserId 대신 매개변수로 넘어온 userId  설정
            entity.setUserId(userId);

            // (4) 서비스 계층을 이용해 todo 엔티티 생성
            List<TodoEntity> entities = service.create(entity);

            // (5) 리턴된 엔티티 리스트를 TodoDTO 로 변환
            List<TodoDTO> dtos = new ArrayList<>();
            for (TodoEntity tEntity: entities) {
                TodoDTO tDto = new TodoDTO(tEntity);
                dtos.add(tDto);
            }

            // (6) 변환된 todoDTO 리스트를 이용해 ResponseDTO 초기화
            // -> TodoDTO 타입을 담는 ResponseDTO 객체를 빌드하겠습니다..!
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // (7) ResponseDTO 를 클라이언트에게 응답
            // ResponseEntity.ok(): http 상태코드를 200 으로 설정
            // body(response): 응답의 body 를 response 인스턴스로 설정
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // (8) 예외가 발생한 경우, ResponseDTO 의 data 필드 대신, error 필드에 에러 메세지를 넣어서 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            // badRequest(): 400 에러 응답을 전송
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        // TODO: 임시 유저 하드코딩으로 추후 수정 필요
//        String temporaryUserId = "temporary-user";

        // (1) 서비스 계층의 retrieve 메서드를 사용해 투두 리스트 가져오기
//        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoEntity> entities = service.retrieve(userId);

        // (2) 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = new ArrayList<>();
        for (TodoEntity tEntity: entities) {
            TodoDTO tDto = new TodoDTO(tEntity);
            dtos.add(tDto);
        }

        // (3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO 를 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // (4) ResponseDTO 를 리턴
        return ResponseEntity.ok().body(response);
    }
}

// dev ?!!
// haeun
// haeun22