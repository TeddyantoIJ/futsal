package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsRole;
import ac.id.polman.astra.futsal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<MsRole> getAllRole(){
        List<MsRole> msRoleList = (List<MsRole>) roleRepository.findAll();
        return msRoleList;
    }

    public MsRole getRole(int id_role){
        MsRole msRole = roleRepository.findByIdRole(id_role);
        return msRole;
    }

    // ======================================

    public void saverole(MsRole msRole){
        roleRepository.save(msRole);
    }
}
