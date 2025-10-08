package codingon.spring_boot_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Todo") // name 을 지정하지 않으면 클래스이름(TodoEntity)을 테이블 이름으로 간주하게 됨
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="user_id", nullable = false)
    private String userId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="done", nullable = false)
    private boolean done;
}
