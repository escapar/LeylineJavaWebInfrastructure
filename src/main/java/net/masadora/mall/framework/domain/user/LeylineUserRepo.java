package net.masadora.mall.framework.domain.user;

import net.masadora.mall.business.domain.user.User;
import net.masadora.mall.framework.domain.LeylineRepo;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineRepo<User> {
    User findByNameEquals(String name);
}
