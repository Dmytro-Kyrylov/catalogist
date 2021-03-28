package com.kdv.catalogist.common.services.user.share;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

  private Long id;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
