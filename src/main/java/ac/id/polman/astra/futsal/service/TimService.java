package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.MsMerchant;
import ac.id.polman.astra.futsal.model.MsTim;
import ac.id.polman.astra.futsal.model.TrBookingLapangan;
import ac.id.polman.astra.futsal.repository.TimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<MsTim> getTeamByName(String cari){
        List<MsTim> a = getAllActive();
        List<MsTim> output = new ArrayList<>();
        for (MsTim b : a)
        {
            if(b.getNama().toLowerCase().contains(cari.toLowerCase())
                    || b.getNama().toLowerCase().contains(cari.toLowerCase())
                    || b.getPrivat().toString().toLowerCase().contains(cari.toLowerCase())
                    || b.getTipePemain().toString().toLowerCase().contains(cari.toLowerCase())){
                output.add(b);
            }
        }
        return output;
    }

    public MsTim getTimByNama(String nama){
        List<MsTim> a = getAllActive();
        for (MsTim b:a) {
            if(b.getNama().equals(nama)){
                return b;
            }
        }
        MsTim c = new MsTim();
        c.setIdTim(-1);
        return c;
    }

    public List<MsTim> getReportTim(List<TrBookingLapangan> booking){
        List<MsTim> timList = new ArrayList<>();
        for(TrBookingLapangan book : booking){
            System.out.println(book.getIdTim());
            for(MsTim t : getAllTim()){
                if(t.getIdTim() == book.getIdTim() && book.getIdTim() != 0){
                    if(timList.size() == 0){
                        t.setStatus(1);
                        timList.add(t);
                    }else{
                        try{
                            int index = 0;
                            boolean ada = false;
                            for(MsTim t2 : timList){
                                if(t2.getIdTim() == t.getIdTim()){
                                    t2.setStatus(t2.getStatus()+1);
                                    timList.set(index, t2);
                                    ada = true;
                                }
                                index++;
                            }
                            if(!ada){
                                t.setStatus(1);
                                timList.add(t);
                            }
                        }catch (Exception e){
                        }
                    }
                }
            }
        }
        return timList;
    }
//   =================================================

    public void saveTim(MsTim msTim){
        timRepository.save(msTim);
    }

    public void update(MsTim msTim){
        timRepository.save(msTim);
    }
}
