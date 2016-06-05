package com.masadora.shop.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import moe.src.leyline.interfaces.dto.DTO;
import moe.src.leyline.interfaces.view.VIEW;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode @Data public class ProductDTO extends DTO{
    @JsonView(VIEW.LIST.class)
    private int id;

    @JsonView(VIEW.LIST.class)
    private String name;

    @JsonView(VIEW.DETAIL.class)
    private double price;
}
