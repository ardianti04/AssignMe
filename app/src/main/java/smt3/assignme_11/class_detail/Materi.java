package smt3.assignme_11.class_detail;

public class Materi {
   private int id_Materi;
   private String nama_Materi;
   private String tanggal_upload;
   private int imgMateri;

    public Materi(int id_Materi, String nama_Materi, String tanggal_upload, int imgMateri) {
        this.id_Materi = id_Materi;
        this.nama_Materi = nama_Materi;
        this.tanggal_upload = tanggal_upload;
        this.imgMateri = imgMateri;
    }

    public int getId_Materi() {
        return id_Materi;
    }

    public void setId_Materi(int id_Materi) {
        this.id_Materi = id_Materi;
    }

    public String getNama_Materi() {
        return nama_Materi;
    }

    public void setNama_Materi(String nama_Materi) {
        this.nama_Materi = nama_Materi;
    }

    public String getTanggal_upload() {
        return tanggal_upload;
    }

    public void setTanggal_upload(String tanggal_upload) {
        this.tanggal_upload = tanggal_upload;
    }

    public int getImgMateri() {
        return imgMateri;
    }

    public void setImgMateri(int imgMateri) {
        this.imgMateri = imgMateri;
    }

    @Override
    public String toString() {
        return "Materi{" +
                "id_Materi=" + id_Materi +
                ", nama_Materi='" + nama_Materi + '\'' +
                ", tanggal_upload='" + tanggal_upload + '\'' +
                ", imgMateri=" + imgMateri +
                '}';
    }
}
