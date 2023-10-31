package smt3.assignme_11;

import android.widget.ImageView;

import java.util.Date;

public class Tugas {
    private int id_Tugas;
    private String nama_mapel;
    private String deskripsi_tugas;
    private String tgl_berakhir;
    private String imageUrl;

    public Tugas(int id_Tugas, String nama_mapel, String deskripsi_tugas, String tgl_berakhir,String imageUrl) {
        this.id_Tugas = id_Tugas;
        this.nama_mapel = nama_mapel;
        this.deskripsi_tugas = deskripsi_tugas;
        this.tgl_berakhir = tgl_berakhir;
        this.imageUrl=imageUrl;
    }

    public int getId_Tugas() {
        return id_Tugas;
    }

    public void setId_Tugas(int id_Tugas) {
        this.id_Tugas = id_Tugas;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public void setNama_mapel(String nama_mapel) {
        this.nama_mapel = nama_mapel;
    }

    public String getDeskripsi_tugas() {
        return deskripsi_tugas;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDeskripsi_tugas(String deskripsi_tugas) {
        this.deskripsi_tugas = deskripsi_tugas;
    }

    public String getTgl_berakhir() {
        return tgl_berakhir;
    }

    public void setTgl_berakhir(String tgl_berakhir) {
        this.tgl_berakhir = tgl_berakhir;
    }

    @Override
    public String toString() {
        return "Tugas{" +
                "id_Tugas=" + id_Tugas +
                ", nama_mapel='" + nama_mapel + '\'' +
                ", deskripsi_tugas='" + deskripsi_tugas + '\'' +
                ", tgl_berakhir=" + tgl_berakhir +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
