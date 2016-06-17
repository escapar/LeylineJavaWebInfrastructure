package moe.src.leyline.framework.domain.user;

import moe.src.leyline.framework.domain.LeylineRepo;
import net.masadora.mall.business.domain.user.User;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineRepo<User>{
    User findByNameEquals(String name);
}
