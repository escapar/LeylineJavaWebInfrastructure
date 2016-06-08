package moe.src.leyline.framework.domain.user;

import moe.src.leyline.framework.domain.Repo;

/**
 * Created by POJO on 6/8/16.
 */
public interface UserRepoAbstact<T extends UserDOAbstract> extends Repo<T> {
    T findByNameEquals(String username);

}
