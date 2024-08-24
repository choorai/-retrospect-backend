package choorai.retrospect.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "idx_user_id", columnList = "user_id", unique = true)
})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 320, nullable = false)
    private String userId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "name", length = 5, nullable = false)
    private String name;

    @Column(name = "department", length = 50, nullable = false)
    private String department;

    @Column(name = "position", length = 50, nullable = false)
    private String position;

    @Column(name = "company_name", length = 50, nullable = false)
    private String companyName;

    public User(String userId, String password, String name, String department, String position, String companyName) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.department = department;
        this.position = position;
        this.companyName = companyName;
    }
}
