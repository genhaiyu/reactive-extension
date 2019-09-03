package org.yugh.product.controller;


import lombok.Data;

/**
 * Created by muzongping on 2019/7/11.
 */
@Data
public class DataSetDTO {

  private Integer dataSetId;
  private String name;
  private String description;
  private String originalSql;
  private Integer isOrg;


}
