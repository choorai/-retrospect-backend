package choorai.retrospect.user.entity;

import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.user.entity.value.Email;
import choorai.retrospect.user.entity.value.Name;
import choorai.retrospect.user.entity.value.Password;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Name name;

    private String department;

    private String position;

    private String companyName;

    public User(String email, String password, String name, String companyName, String department, String position) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new Name(name);
        this.companyName = companyName;
        this.department = department;
        this.position = position;
    }

    public User(String email, String password, String name) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new Name(name);
    }
}
