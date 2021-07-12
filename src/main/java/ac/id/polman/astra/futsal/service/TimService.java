package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsTim;
import ac.id.polman.astra.futsal.repository.TimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimService {
    @Autowired
    TimRepository timRepository;

    public List<MsTim> getAllTim(){
        List<MsTim> msTimList = (List<MsTim>) timRepository.findAll();
        return msTimList;
    }

    public MsTim getTimById(int id_tim){
        MsTim msTim = timRepository.findByIdTim(id_tim);
        return msTim;
    }

    public List<MsTim> getAllActive(){
        List<MsTim> a = getAllTim();
        for ( MsTim b : a )
        {
            if(b.getStatus() != 1){
                a.remove(b);
            }
        }
        return a;
    }

    public MsTim getTimByIdUser(int idUser){
        List<MsTim> a = getAllTim();
        MsTim c = null;
        for(MsTim b : a){
            if(b.getIdUser() == idUser){
                c = b;
                break;
            }
        }
        return c;
    }
//   =================================================

    public void saveTim(MsTim msTim){
        timRepository.save(msTim);
    }

    public void update(MsTim msTim){
        timRepository.save(msTim);
    }
}
