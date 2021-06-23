package ac.id.polman.astra.futsal.cotroller;

import ac.id.polman.astra.futsal.model.MsRole;
import ac.id.polman.astra.futsal.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/Role")
    public String getRole(Model model)
    {
        List<MsRole> msRoleList = roleService.getAllRole();
        model.addAttribute("listRole", msRoleList);
        return "role/list";
    }

    @GetMapping("/Role-add")
    public String gotoAdd(Model model){
        model.addAttribute("msRole", new MsRole());
        return "role/add";
    }

    @GetMapping("/Role-edit")
    public String gotoEdit(
            @RequestParam("id_role") int id_role,
            Model model
    ){
        MsRole msRole = roleService.getRole(id_role);
        model.addAttribute("msRole", msRole);
        return "role/edit";
    }

    //========================================
    @PostMapping("/addRole")
    public String addRole(
            @RequestParam("role") String role
    ){
        MsRole msRole = new MsRole();
        msRole.setRole(role);
        msRole.setCreaby("Teddy(harusnya ambil nama yang bikin)");
        msRole.setCreadate(LocalDateTime.now());
        msRole.setModiby("");
        msRole.setModidate(LocalDateTime.now());
        msRole.setStatus(1);

        roleService.saverole(msRole);
        return "redirect:/Role";
    }

    @PostMapping("/editRole")
    public String editRole(
            @RequestParam("idRole") int idRole,
            @RequestParam("role") String role
    ){
        MsRole msRole = roleService.getRole(idRole);

        msRole.setRole(role);
        msRole.setModiby("yang login saat ini");
        msRole.setModidate(LocalDateTime.now());

        roleService.saverole(msRole);
        return "redirect:/Role";
    }

    @GetMapping("/deleteRole")
    public String deleteRole(
            @RequestParam("id_role") int idRole
    ){
        MsRole msRole = roleService.getRole(idRole);

        msRole.setModiby("yang login saat ini");
        msRole.setModidate(LocalDateTime.now());
        if(msRole.getStatus() == 1){
            msRole.setStatus(0);
        }else{
            msRole.setStatus(1);
        }
        roleService.saverole(msRole);
        return "redirect:/Role";
    }
}
