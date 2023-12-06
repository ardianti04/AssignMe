package smt3.assignme_11.class_detail;

public class Materi {
   private int id_Materi;
   private int classId;
   private String nama_Materi;
    private String deskripsi_materi;
   private String tanggal_upload;
   private String attachment;

    public Materi(int id_Materi, int classId, String nama_Materi, String deskripsi_materi, String tanggal_upload, String attachment) {
        this.id_Materi = id_Materi;
        this.classId = classId;
        this.nama_Materi = nama_Materi;
        this.deskripsi_materi = deskripsi_materi;
        this.tanggal_upload = tanggal_upload;
        this.attachment = attachment;
    }

    public String getDeskripsi_materi() {
        return deskripsi_materi;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public int getClassId() {
        return classId;
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


    @Override
    public String toString() {
        return "Materi{" +
                "id_Materi=" + id_Materi +
                ", nama_Materi='" + nama_Materi + '\'' +
                ", tanggal_upload='" + tanggal_upload + '\'' +
                '}';
    }
}
