package net.masadora.mall.business.domain.user;

import net.masadora.mall.framework.domain.user.LeylineUserRepo;
import org.springframework.stereotype.Repository;

/**
 * Created by POJO on 6/8/16.
 */
@Repository
public interface UserRepo extends LeylineUserRepo<User> {
}
