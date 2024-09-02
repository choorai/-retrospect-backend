package choorai.retrospect.retrospect_room.entity;

import choorai.retrospect.global.domain.BaseEntity;
import choorai.retrospect.retrospect_room.entity.value.Details;
import choorai.retrospect.retrospect_room.entity.value.ShareLink;
import choorai.retrospect.retrospect_room.entity.value.Subject;
import choorai.retrospect.retrospect_room.entity.value.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Time;

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

    private Time timeLimit;

    @Embedded
    private ShareLink shareLink;
}
