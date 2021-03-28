package com.kdv.catalogist.api.services.list;

import com.kdv.catalogist.BaseRestUrls;

public interface ListRestUrls {

  String RESOURCE_NAME = "/lists";

  String ID = "/{listId}";

  String BASE = BaseRestUrls.BASE + RESOURCE_NAME;

  String FIND = BASE;

  String GET = BASE + ID;

  String CREATE = BASE;

  String UPDATE = BASE + ID;

  String DELETE = BASE + ID;

}
