package com.messenger.friendsapp.application.command.handler;

import com.messenger.friendsapp.application.command.DeleteFriendCommand;
import com.messenger.friendsapp.domain.service.DomainFriendshipService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DeleteFriendCommandHandler {
    private final DomainFriendshipService domainFriendshipService;

    public DeleteFriendCommandHandler(DomainFriendshipService domainFriendshipService) {
        this.domainFriendshipService = domainFriendshipService;
    }

    /**
     * Removes friend from user's friend list
     * @param command CQS command for appropriate handler
     */
    public void handle(@NonNull DeleteFriendCommand command) {
        domainFriendshipService.removeFromFriendshipList(command.getRequesterId(), command.getAddresseeId());
    }
}
