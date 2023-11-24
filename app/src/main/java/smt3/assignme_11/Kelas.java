package smt3.assignme_11;

public class Kelas  {
    private int id_kelas;
    private String nama_kelas;
    private String kode_kelas;
    private String nama_mapel;
    private String dekripsi;

    public Kelas(String nama_kelas, String kode_kelas, String nama_mapel, String dekripsi) {
        this.nama_kelas = nama_kelas;
        this.kode_kelas = kode_kelas;
        this.nama_mapel = nama_mapel;
        this.dekripsi = dekripsi;
    }

    public Kelas(String nama_kelas, String nama_mapel) {
        this.nama_kelas = nama_kelas;
        this.nama_mapel = nama_mapel;
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

    @Override
    public String toString() {
        return "Kelas{" +
                "id_kelas=" + id_kelas +
                ", nama_kelas='" + nama_kelas + '\'' +
                ", kode_kelas='" + kode_kelas + '\'' +
                ", nama_mapel='" + nama_mapel + '\'' +
                ", dekripsi='" + dekripsi + '\'' +
                '}';
    }
}

