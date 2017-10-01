package debuggers.sewakendaraan;

/**
 * Created by BrosnanUhYeah on 21/09/2017.
 */

public class Rental {
    private String nm_motor;
    private String info;
    private String gambar;

    Rental(){}

    Rental(String nm_motor, String info){
        this.nm_motor = nm_motor;
        this.info = info;
    }

    Rental(String nm_motor, String info, String gambar){
        this.nm_motor = nm_motor;
        this.info = info;
        this.gambar = gambar;
    }


    public String getNm_motor() {
        return nm_motor;
    }

    public void setNm_motor(String nm_motor) {
        this.nm_motor = nm_motor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
