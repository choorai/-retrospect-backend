package choorai.retrospect.retrospect_room.entity;

import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.retrospect_room.entity.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RetrospectRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Subject subject;

    @Embedded
    private Details details;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Embedded
    private TimeLimit timeLimit;

    @Embedded
    private ShareLink shareLink;

    private RetrospectRoom(final Long id, final Subject subject, final Details details, final Type type, final TimeLimit timeLimit, final ShareLink shareLink) {
        this.id = id;
        this.subject = subject;
        this.details = details;
        this.type = type;
        this.timeLimit = timeLimit;
        this.shareLink = shareLink;
    }

    public static RetrospectRoom forSave(final String subject, final String details, final String type, final String timeLimit, final String shareLink) {
        return new RetrospectRoom(null,
            new Subject(subject),
            new Details(details),
            Type.valueOf(type),
            TimeLimit.from(timeLimit),
            new ShareLink(shareLink));
    }
}
