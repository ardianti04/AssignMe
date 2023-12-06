package smt3.assignme_11.class_detail;

import android.widget.ImageView;

import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import smt3.assignme_11.task_detail.Attachment;

public class Tugas implements Parcelable {
    private int id_Tugas;
    private String nama_mapel;
    private String deskripsi_tugas;
    private String tgl_berakhir;
    private String imageUrl;
    private int classId;
    private String attachment;
    private Attachment attachmentStudent;

    public Tugas(int id_Tugas, String nama_mapel, String deskripsi_tugas, String tgl_berakhir, int classId, String attachment) {
        this.id_Tugas = id_Tugas;
        this.nama_mapel = nama_mapel;
        this.deskripsi_tugas = deskripsi_tugas;
        this.tgl_berakhir = tgl_berakhir;
        this.classId = classId;
        this.attachment = attachment;
    }

    public Attachment getAttachmentStudent() {
        return attachmentStudent;
    }
    public void setAttachmentStudent(Attachment attachment) {
        this.attachmentStudent = attachment;
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


    public Tugas(/* Your constructor parameters */) {
        // Initialize your properties
    }

    // Constructor for parcel
    protected Tugas(Parcel in) {
        // Retrieve values from parcel and set properties
    }

    // Method to write properties to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write properties to parcel
    }

    // Parcelable creator
    public static final Parcelable.Creator<Tugas> CREATOR = new Parcelable.Creator<Tugas>() {
        @Override
        public Tugas createFromParcel(Parcel in) {
            return new Tugas(in);
        }

        @Override
        public Tugas[] newArray(int size) {
            return new Tugas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
