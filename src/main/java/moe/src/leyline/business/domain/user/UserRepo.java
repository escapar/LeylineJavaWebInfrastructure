package moe.src.leyline.business.domain.user;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import moe.src.leyline.framework.domain.user.LeylineUserRepo;

/**
 * Created by POJO on 6/8/16.
 */
@Repository
public interface UserRepo extends LeylineUserRepo<DomainUser> {

    default Boolean authOK(String name,String password){
        if(name==null || password==null || name.isEmpty() || password.isEmpty()){
            return false;
        }
        DomainUser u = findByNameEquals(name);
        return u!=null && BCrypt.checkpw(password, u.getPassword());
    }

    default DomainUser checkAndGet(String name,String password){
        return authOK(name,password)? findByNameEquals(name) : null;
    }
}
