package org.web.gamedev.rpg.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web.gamedev.rpg.forum.config.JwtTokenUtil;
import org.web.gamedev.rpg.forum.error.TokenRefreshException;
import org.web.gamedev.rpg.forum.error.UserCreationException;
import org.web.gamedev.rpg.forum.mapper.UserDetailsMapper;
import org.web.gamedev.rpg.forum.mapper.UserMapper;
import org.web.gamedev.rpg.forum.model.entity.RefreshTokenEntity;
import org.web.gamedev.rpg.forum.model.entity.RoleEntity;
import org.web.gamedev.rpg.forum.model.entity.UserDto;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;
import org.web.gamedev.rpg.forum.model.payload.CustomUserDetails;
import org.web.gamedev.rpg.forum.model.payload.MessageResponse;
import org.web.gamedev.rpg.forum.model.payload.TokenRefreshResponse;
import org.web.gamedev.rpg.forum.model.payload.UserResponseDto;
import org.web.gamedev.rpg.forum.repository.RoleRepository;
import org.web.gamedev.rpg.forum.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsMapper userDetailsMapper;
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    @Transactional
    @Override
    public UserResponseDto signUp(UserDto userDto) {
        if (isUserExist(userDto)) {
            String message = String.format("User %s is already exist", userDto.getUsername());
            log.info(message);
            throw new UserCreationException(message);
        }
        return saveUser(userDto, false);
    }

    @Transactional
    private UserResponseDto saveUser(UserDto userDto, boolean isAdmin) {
        try {
            UserEntity userEntity = userMapper.getEntityFromUserDto(userDto);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleRepository.findByName(USER));
            if (isAdmin) {
                roles.add(roleRepository.findByName(ADMIN));
            }
            userEntity.setRoles(roles);
            UserEntity savedUserEntity = userRepository.save(userEntity);
            log.info(String.format("User has been saved id: %s", savedUserEntity.getId()));

            UserResponseDto response = new UserResponseDto();
            response.setUsername(userDto.getUsername());
            response.setRefreshToken(refreshTokenService.createRefreshToken(savedUserEntity.getId()).getToken());
            response.setToken(jwtTokenUtil.generateToken(userDetailsMapper.getUserDetailsFromUserEntity(savedUserEntity)));
            return response;
        } catch (Exception ex) {
            log.error("UserServiceImpl.saveUser() ", ex);
            throw new UserCreationException("Error during user creation");
        }
    }


    @Transactional
    @Override
    public UserResponseDto signIn(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findByUsername(userDto.getUsername());
        if (user.isPresent() && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            final RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(user.get().getId());
            String token = jwtTokenUtil.generateToken(userDetailsMapper.getUserDetailsFromUserEntity(user.get()));
            UserResponseDto responseDto = new UserResponseDto();
            responseDto.setUsername(user.get().getUsername());
            responseDto.setRefreshToken(refreshTokenEntity.getToken());
            responseDto.setToken(token);

            return responseDto;
        }

        throw new IllegalArgumentException("Invalid username or password");
    }


    @Override
    public MessageResponse signOut(Long userId) {
        refreshTokenService.deleteByUserId(userId);
        return new MessageResponse(0, "User signed out");
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshToken(String refreshToken, CustomUserDetails userDetails) {
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    final RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(user.getId());
                    final String jwt = jwtTokenUtil.generateToken(userDetails);
                    return new TokenRefreshResponse(jwt, refreshTokenEntity.getToken());
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in DB!"));
    }

    @Override
    public boolean isUserExist(UserDto userDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(userDto.getUsername());
        return optionalUserEntity.isPresent();
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userMapper.getUserDtoFromUserEntity(userRepository.getById(userId));
    }

    @Override
    public Long getUserCount() {
        return userRepository.count();
    } //Example.of(..)

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.getUserDtoListFromUserEntityList(userRepository.findAll());
    }
}
