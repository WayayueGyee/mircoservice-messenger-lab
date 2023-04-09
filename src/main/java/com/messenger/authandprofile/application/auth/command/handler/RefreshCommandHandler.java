package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.command.RefreshCommand;
import com.messenger.authandprofile.application.auth.dto.TokenPairDto;
import com.messenger.authandprofile.application.auth.mapper.TokenPairMapper;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.domain.repository.UserRepository;
import lombok.NonNull;

@SuppressWarnings("unused")
public class RefreshCommandHandler implements Command.Handler<RefreshCommand, TokenPairDto> {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenPairMapper tokenPairMapper;

    public RefreshCommandHandler(
            TokenService tokenService, UserRepository userRepository,
            TokenPairMapper tokenPairMapper
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.tokenPairMapper = tokenPairMapper;
    }

    /*
     * 1. Получить информацию о том, использовался ли данный рефреш токен
     * 2. Если токен уже использовался, мы инвалидируем всё семейство рефреш токенов (refresh token family) и ничего не возвращаем
     * 3. Если токен не использовался:
     *   3.1. Состояние только использованного токена меняем на "использован"
     *   3.2. Генерируем новую пару токенов и возвращаем их пользователю
     *
     * Возможно стоит вынести токены в домен. У нас будет 2 сущности - refresh token и access token, а также агрегат, так как мы
     * используем Refresh Token Rotation и создаваться токены могут только в паре. TokenService тоже был бы вынесен в домен.
     * В этих сущностях не было бы реализации, но они были бы маркерами в коде.
     * Также, возможно, в них были бы возможные клеймы и прочая важная информация.
     */
    @Override
    public TokenPairDto handle(@NonNull RefreshCommand command) {
        if (tokenService.wasRefreshTokenUsed(command.getRefreshToken())) {
            tokenService.invalidateRefreshTokenFamily(command.getUserId());
            return null;
        }

        var user = userRepository.findUserById(command.getUserId());

        var tokenPair = tokenService.generateTokens(user);
        tokenService.invalidateRefreshToken(command.getRefreshToken());

        return tokenPairMapper.mapToTokenPairDto(tokenPair);
    }
}