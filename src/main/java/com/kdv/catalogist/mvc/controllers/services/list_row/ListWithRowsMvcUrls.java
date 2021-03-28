package com.kdv.catalogist.mvc.controllers.services.list_row;

import com.kdv.catalogist.mvc.controllers.services.list.ListMvcUrls;

public interface ListWithRowsMvcUrls {

  String ID = "/{listRowId}";

  String BASE = ListMvcUrls.RESOURCE_NAME + ListMvcUrls.ID;

  String FIND = BASE;

  String CREATE = BASE + "/create";

  String DELETE = BASE + "/delete" + ID;

}
