package com.masadora.shop.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import moe.src.leyline.interfaces.dto.DTO;
import moe.src.leyline.interfaces.view.View;

/**
 * Created by POJO on 6/3/16.
 */
@EqualsAndHashCode @Data public class ProductDTO extends DTO{
    @JsonView(View.LIST.class)
    private int id;

    @JsonView(View.LIST.class)
    private String name;

    @JsonView(View.DETAIL.class)
    private double price;
}
