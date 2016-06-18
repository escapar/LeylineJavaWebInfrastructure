package moe.src.leyline.framework.domain.user;

import moe.src.leyline.framework.domain.LeylineRepo;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineRepo<LeylineUser>{
    LeylineUser findByNameEquals(String name);
}
