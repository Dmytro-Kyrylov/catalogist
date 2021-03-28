package com.kdv.catalogist.api.services.user;

import com.kdv.catalogist.BaseRestUrls;

public interface UserRestUrls {

  String RESOURCE_NAME = "/users";

  String BASE = BaseRestUrls.BASE + RESOURCE_NAME;

  String FIND = BASE;

  String CREATE = BASE;

}
