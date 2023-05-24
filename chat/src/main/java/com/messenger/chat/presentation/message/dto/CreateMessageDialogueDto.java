package com.messenger.chat.presentation.message.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateMessageDialogueDto {
    private UUID recipientId;
    private String messageText;
}