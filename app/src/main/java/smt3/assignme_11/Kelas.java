package smt3.assignme_11;

public class Kelas  {
    private int id_kelas;
    private String nama_kelas;
    private String kode_kelas;
    private String nama_guru;
    private String nama_mapel;
    private String dekripsi;
    private String imageUrl;

    public Kelas(int id_kelas, String nama_kelas, String kode_kelas, String nama_guru, String nama_mapel, String dekripsi) {
        this.id_kelas = id_kelas;
        this.nama_kelas = nama_kelas;
        this.kode_kelas = kode_kelas;
        this.nama_guru = nama_guru;
        this.nama_mapel = nama_mapel;
        this.dekripsi = dekripsi;
        //this.imageUrl = imageUrl;
    }

    public int getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(int id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getKode_kelas() {
        return kode_kelas;
    }

    public void setKode_kelas(String kode_kelas) {
        this.kode_kelas = kode_kelas;
    }

    public String getNama_guru() {
        return nama_guru;
    }

    public void setNama_guru(String nama_guru) {
        this.nama_guru = nama_guru;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public void setNama_mapel(String nama_mapel) {
        this.nama_mapel = nama_mapel;
    }

    public String getDekripsi() {
        return dekripsi;
    }

    public void setDekripsi(String dekripsi) {
        this.dekripsi = dekripsi;
    }

    //public String getImageUrl() {
        //return imageUrl;
    //}

    //public void setImageUrl(String imageUrl) {
        //this.imageUrl = imageUrl;
    //}
}

