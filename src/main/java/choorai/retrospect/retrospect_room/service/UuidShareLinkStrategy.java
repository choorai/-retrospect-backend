package choorai.retrospect.retrospect_room.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidShareLinkStrategy implements ShareLinkCreateStrategy {

    @Override
    public String createShareLink() {
        return UUID.randomUUID().toString();
    }
}
