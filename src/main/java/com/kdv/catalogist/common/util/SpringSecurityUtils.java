package com.kdv.catalogist.common.util;

import com.kdv.catalogist.common.services.security.UserDetailsImpl;
import com.kdv.catalogist.common.services.user.share.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpringSecurityUtils {

  public static Optional<UserDto> getCurrentUser() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .map(UserDetailsImpl.class::cast)
        .map(UserDetailsImpl::getUserDto);
  }

}
