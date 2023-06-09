package com.messenger.chat.application.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatInfoDto {
    @NonNull private String chatName;
    @NonNull private UUID avatarId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NonNull
    private LocalDateTime creationDate;
    private UUID ownerId;

    public ChatInfoDto(@NonNull String chatName, @NonNull UUID avatarId, @NonNull LocalDateTime creationDate) {
        this.chatName = chatName;
        this.avatarId = avatarId;
        this.creationDate = creationDate;
    }

    public ChatInfoDto(
            @NonNull String chatName,
            @NonNull UUID avatarId,
            @NonNull LocalDateTime creationDate,
            UUID ownerId
    ) {
        this.chatName = chatName;
        this.avatarId = avatarId;
        this.creationDate = creationDate;
        this.ownerId = ownerId;
    }
}
