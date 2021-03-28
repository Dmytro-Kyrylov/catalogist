package com.kdv.catalogist.mvc.controllers.services.list;

public interface ListMvcUrls {

  String RESOURCE_NAME = "/lists";

  String ID = "/{listId}";

  String FIND = RESOURCE_NAME;

  String CREATE = RESOURCE_NAME + "/create";

  String UPDATE = RESOURCE_NAME + "/update" + ID;

  String DELETE = RESOURCE_NAME + "/delete" + ID;

}
