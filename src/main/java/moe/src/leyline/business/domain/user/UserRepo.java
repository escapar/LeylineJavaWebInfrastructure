package moe.src.leyline.business.domain.user;

import moe.src.leyline.framework.domain.user.UserRepoAbstact;
import org.springframework.stereotype.Repository;

/**
 * Created by POJO on 6/8/16.
 */
@Repository
public interface UserRepo extends UserRepoAbstact<User>  {
}
