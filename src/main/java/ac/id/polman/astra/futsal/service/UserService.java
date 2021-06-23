package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsUser;
import ac.id.polman.astra.futsal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<MsUser> getAllUser(){
        List<MsUser> msUserList = (List<MsUser>) userRepository.findAll();
        return msUserList;
    }

    public MsUser getUserById(int id_user){
        MsUser msUser = userRepository.findByIdUser(id_user);
        return msUser;
    }

    // ========================================================================

    public void saveUser(MsUser msUser){
        userRepository.save(msUser);
    }

    public void update(MsUser msUser){
        userRepository.save(msUser);
    }
}
