package moe.src.leyline.business.domain.user;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import moe.src.leyline.framework.domain.user.LeylineUserRepo;

/**
 * Created by POJO on 6/8/16.
 */
@Repository
public interface UserRepo extends LeylineUserRepo<User> {
    User findByNameAndPassword(String name , String password);

    default Boolean authOK(String name,String password){
        User u = findByNameEquals(name);
        return u!=null && BCrypt.checkpw(password, u.getPassword());
    }

    default User checkAndGet(String name,String password){
        return authOK(name,password)? findByNameEquals(name) : null;
    }
}
