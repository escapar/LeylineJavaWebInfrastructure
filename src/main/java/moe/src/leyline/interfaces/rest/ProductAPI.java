package moe.src.leyline.interfaces.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaslang.Tuple;
import moe.src.leyline.infrastructure.persistence.daos.ProductDao;
import moe.src.leyline.service.ProductService;

/**
 * Created by POJO on 5/29/16.
 */
@RestController
@RequestMapping("product")

public class ProductAPI extends SimpleCrudAPI<ProductDao> {


}
