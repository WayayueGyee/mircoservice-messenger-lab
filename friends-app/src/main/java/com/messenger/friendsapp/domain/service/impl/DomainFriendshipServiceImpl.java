package com.messenger.friendsapp.domain.service.impl;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.entity.Friendship;
import com.messenger.friendsapp.domain.event.FriendshipDeletedEvent;
import com.messenger.friendsapp.domain.exception.UserIsBlockedException;
import com.messenger.friendsapp.domain.repository.BlacklistRepository;
import com.messenger.friendsapp.domain.repository.FriendshipRepository;
import com.messenger.friendsapp.domain.service.DomainFriendshipService;
import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import lombok.NonNull;

import javax.transaction.Transactional;
import java.util.UUID;

public class DomainFriendshipServiceImpl implements DomainFriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final BlacklistRepository blacklistRepository;

    public DomainFriendshipServiceImpl(
            FriendshipRepository friendshipRepository,
            BlacklistRepository blacklistRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.blacklistRepository = blacklistRepository;
    }

    /*
     * 1. Проверить, не добавлен ли пользователь уже в друзья. Если добавлен, просто выходим из метода
     * 2. Проверить, не заблокировали ли мы пользователя. Если да, то нужно уведомить о том, что пользователь заблокирован
     * 3. Проверить, добавлял ли нас пользователь в друзья. Если да, то нужно поставить статус MUTUAL у обоих.
     */
    @Override
    @Transactional
    public void addToFriendshipList(UUID requesterId, @NonNull Addressee addressee) throws UserIsBlockedException {
        var isAdded = friendshipRepository.isAddedToFriendList(requesterId, addressee.getId());

        if (isAdded) {
            return;
        }

        if (blacklistRepository.isRequesterBlocked(requesterId, addressee.getId())) {
            throw new UserIsBlockedException();
        }

        var optionalAddresseeFriendship = friendshipRepository.findFriend(addressee.getId(), requesterId);
        Friendship requesterFriendship;

        if (optionalAddresseeFriendship.isPresent()) {
            var addresseeFriendship = optionalAddresseeFriendship.get();
            addresseeFriendship.setMutualStatus();

            requesterFriendship = Friendship.createNewFriendship(requesterId, addressee, FriendshipStatus.MUTUAL);

            friendshipRepository.save(addresseeFriendship);
        } else {
            requesterFriendship = Friendship.createNewFriendship(requesterId, addressee);
        }

        friendshipRepository.save(requesterFriendship);
    }

    /*
     * 1. Если дружба была невзаимной, просто удаляем из друзей
     * 2. Если дружба была взаимной, меняем состояние на PENDING (у того, кого удалили)
     */
    @Override
    @Transactional
    public void removeFromFriendshipList(UUID requesterId, UUID addresseeId) {
        var optionalFriendship = friendshipRepository.findFriend(requesterId, addresseeId);

        if (optionalFriendship.isEmpty()) {
            return;
        }

        var friendship = optionalFriendship.get();

        var optionalAddresseeFriendship = friendshipRepository.findFriend(addresseeId, requesterId);

        if (optionalAddresseeFriendship.isPresent()) {
            var addresseeFriendship = optionalAddresseeFriendship.get();
            addresseeFriendship.setPendingStatus();

            friendshipRepository.save(addresseeFriendship);
        }

        friendship.getDomainEvents().add(new FriendshipDeletedEvent(
                friendship.getId(),
                friendship.getRequesterId(),
                friendship.getAddressee()
        ));

        friendshipRepository.delete(friendship);
    }
}
