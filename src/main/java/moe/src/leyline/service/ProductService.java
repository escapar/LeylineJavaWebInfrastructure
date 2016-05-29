package moe.src.leyline.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javaslang.Tuple2;
import moe.src.leyline.domain.Coupon;
import moe.src.leyline.domain.Discount;
import moe.src.leyline.domain.Product;
import moe.src.leyline.infrastructure.persistence.daos.ProductDao;
import static moe.src.leyline.infrastructure.persistence.tables.ProductTable.PRODUCT;

/**
 * Created by POJO on 5/29/16.
 */
@Service
public class ProductService extends LeylineService<ProductDao>{
    Coupon c = new Coupon();

    public boolean getByCouponId(){
        c.getId();
        return true;
    }
}
