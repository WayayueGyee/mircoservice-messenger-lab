package com.messenger.chat.domain.user;

import java.util.UUID;

// INFO: Менять данные через этот репозиторий нельзя, так как объявив такой интерфейс мы неявно покажем, что сервис чатов может менять
//  пользователей, что неверно. Поэтому, для синхронизации данных с сервисом профилей можно использовать инфраструктурный
//  сервис, о котором ни в домене, ни в слое приложения знать не будут.
public interface UserRepository {
    User getUser(UUID userId);
}
