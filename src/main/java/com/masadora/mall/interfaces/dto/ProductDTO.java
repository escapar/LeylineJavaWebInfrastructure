package com.masadora.mall.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.masadora.mall.framework.interfaces.dto.DTO;
import com.masadora.mall.framework.interfaces.view.VIEW;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode @Data public class ProductDTO extends DTO {
    @JsonView(VIEW.LIST.class)
    private int id;

    @JsonView(VIEW.LIST.class)
    private String name;

    @JsonView(VIEW.DETAIL.class)
    private double price;
}
