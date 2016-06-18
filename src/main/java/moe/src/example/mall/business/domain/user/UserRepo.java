package moe.src.example.mall.business.domain.user;

import org.springframework.stereotype.Repository;

import moe.src.leyline.framework.domain.user.LeylineUserRepo;

/**
 * Created by POJO on 6/8/16.
 */
@Repository
public interface UserRepo extends LeylineUserRepo<User> {
}
