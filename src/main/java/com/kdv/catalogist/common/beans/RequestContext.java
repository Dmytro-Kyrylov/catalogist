package com.kdv.catalogist.common.beans;

import com.kdv.catalogist.common.services.user.share.UserDto;
import com.kdv.catalogist.common.util.SpringSecurityUtils;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequestScope
@Data
public class RequestContext {

  private Optional<UserDto> currentUser;

  @PostConstruct
  private void init() {
    currentUser = SpringSecurityUtils.getCurrentUser();
  }

}
