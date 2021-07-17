package ac.id.polman.astra.futsal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DtAjakTanding {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    private int idAjakTanding;
    private int juara;
    private int skor;
    private int skor2;
    private String creaby;
    @JsonFormat(
            pattern = "dd-MM-yyyy HH:mm:ss"
    )
    private LocalDateTime creadate;
    private String modiby;
    @JsonFormat(
            pattern = "dd-MM-yyyy HH:mm:ss"
    )
    private LocalDateTime modidate;
    private int status;

    public DtAjakTanding() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAjakTanding() {
        return this.idAjakTanding;
    }

    public void setIdAjakTanding(int idAjakTanding) {
        this.idAjakTanding = idAjakTanding;
    }

    public int getJuara() {
        return this.juara;
    }

    public void setJuara(int juara) {
        this.juara = juara;
    }

    public int getSkor() {
        return this.skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }

    public int getSkor2() {
        return this.skor2;
    }

    public void setSkor2(int skor2) {
        this.skor2 = skor2;
    }

    public String getCreaby() {
        return this.creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }

    public LocalDateTime getCreadate() {
        return this.creadate;
    }

    public void setCreadate(LocalDateTime creadate) {
        this.creadate = creadate;
    }

    public String getModiby() {
        return this.modiby;
    }

    public void setModiby(String modiby) {
        this.modiby = modiby;
    }

    public LocalDateTime getModidate() {
        return this.modidate;
    }

    public void setModidate(LocalDateTime modidate) {
        this.modidate = modidate;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}