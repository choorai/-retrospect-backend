package choorai.retrospect.user.entity;

import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.retrospect_room.card.entity.Card;
import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;
import choorai.retrospect.user.entity.value.Email;
import choorai.retrospect.user.entity.value.Name;
import choorai.retrospect.user.entity.value.Password;
import choorai.retrospect.user.entity.value.Role;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "\"user\"")
public class User extends BaseEntity implements UserDetails {

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public User(final String email, final String password, final String name, final String companyName, final String department, final String position, final Role role) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new Name(name);
        this.companyName = companyName;
        this.department = department;
        this.position = position;
        this.role = role;
    }

    public User(final String email, final String password, final String name) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new Name(name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email.getValue();
    }

    @Override
    public String getPassword() {
        return password.getValue();
    }

    public void addCard(final Card card) {
        this.cards.add(card);
    }

    public void removeCardById(final Long cardId) {
        boolean removed = cards.removeIf(card -> card.getId().equals(cardId));
        if (!removed) {
            throw new CardException(CardErrorCode.CARD_NOT_FOUND_FOR_ID);
        }
    }
}
