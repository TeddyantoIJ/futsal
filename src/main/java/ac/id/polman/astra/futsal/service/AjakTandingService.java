package ac.id.polman.astra.futsal.service;

import ac.id.polman.astra.futsal.model.*;
import ac.id.polman.astra.futsal.repository.AjakTandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AjakTandingService {
    @Autowired
    AjakTandingRepository ajakTandingRepository;
    @Autowired
    DtAjakTandingService dtAjakTandingService;

    public List<TrAjakTanding> getAll(){
        List<TrAjakTanding> tandingList = (List<TrAjakTanding>) ajakTandingRepository.findAll();
        return tandingList;
    }

    public TrAjakTanding getAllById(int id){
        TrAjakTanding tandingList = ajakTandingRepository.findById(id);
        return tandingList;
    }

    public TrAjakTanding getLastId(){
        TrAjakTanding tandingList = ajakTandingRepository.findTopByOrderByIdDesc();
        return tandingList;
    }

    public TrAjakTanding getByIdandTanggalandJam(int id, Date tanggal, Time jam){
        TrAjakTanding tandingList = ajakTandingRepository.findByIdTim1AndTanggalAndJam(id, tanggal, jam);
        return tandingList;
    }

    public List<TrAjakTanding> getAllByTim(int id_tim){
        List<TrAjakTanding> tandingList = ajakTandingRepository.findByIdTim1(id_tim);
        return tandingList;
    }

    public List<TrAjakTanding> getAllByTim2(int id_tim){
        List<TrAjakTanding> tandingList = ajakTandingRepository.findByIdTim2(id_tim);
        return tandingList;
    }

    public List<TrAjakTanding> getAllLast6(){
        List<TrAjakTanding> list = (List<TrAjakTanding>) ajakTandingRepository.findAll();
        List<TrAjakTanding> output = new ArrayList<>();
        int juju = (int) list.size() - 6;
        for (int i = list.size()-1 ; i >=juju  ; i--)
        {
            if(list.get(i).getStatus()== 1){
                output.add(list.get(i));
            }
        }

        return output;
    }

    public List<TrAjakTanding> getAllByIdTimTanggal(String tanggal1, String tanggal2, int idTim){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<TrAjakTanding> a = ajakTandingRepository.findAllByOrderByModidateAsc();
        List<TrAjakTanding> c = new ArrayList<>();

        TrAjakTanding total = new TrAjakTanding();
        int kemenangan = 0;
        for(TrAjakTanding b : a){
            try{
                Date tanggal = sdf.parse(b.getTanggal().toString());
                Date comp1 = sdf.parse(tanggal1);
                Date comp2 = sdf.parse(tanggal2);
                if(tanggal.compareTo(comp1) >= 0 && tanggal.compareTo(comp2) <= 0){
                    if(b.getId_status() == 3){
                        if(b.getIdTim1() == idTim || b.getIdTim2() == idTim){
                            DtAjakTanding dt = dtAjakTandingService.getAllByAjakTandings(b.getId());
                            if(dt.getJuara() == idTim){
                                kemenangan++;
                                b.setId_lapangan(idTim);
                            }else{
                                b.setId_lapangan(-1);
                            }
                            if(b.getIdTim2() == idTim){
                                b.setIdTim2(b.getIdTim1());
                                b.setIdTim1(idTim);
                            }
                            c.add(b);
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("Error pak");
            }
        }
        total.setId_status(kemenangan);
        total.setIdTim1(-1);
        total.setIdTim2(c.size());
        c.add(total);
        return c;
    }

    public void save(TrAjakTanding trAjakTanding){
        ajakTandingRepository.save(trAjakTanding);
    }
}
