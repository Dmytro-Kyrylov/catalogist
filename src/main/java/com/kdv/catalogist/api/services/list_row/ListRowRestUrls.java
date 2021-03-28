package com.kdv.catalogist.api.services.list_row;

import com.kdv.catalogist.api.services.list.ListRestUrls;

public interface ListRowRestUrls {

  String RESOURCE_NAME = "/list-rows";

  String ID = "/{listRowId}";

  String BASE = ListRestUrls.GET + RESOURCE_NAME;

  String FIND = BASE;

  String GET = BASE + ID;

  String CREATE = BASE;

  String UPDATE = BASE + ID;

  String DELETE = BASE + ID;

}
