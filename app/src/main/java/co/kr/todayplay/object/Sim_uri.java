package co.kr.todayplay.object;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Sim_uri implements Serializable, Parcelable {
    Uri images;
    public Sim_uri(Uri images){
        this.images = images;
    }

    protected Sim_uri(Parcel in) {
        images = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Sim_uri> CREATOR = new Creator<Sim_uri>() {
        @Override
        public Sim_uri createFromParcel(Parcel in) {
            return new Sim_uri(in);
        }

        @Override
        public Sim_uri[] newArray(int size) {
            return new Sim_uri[size];
        }
    };

    public Uri getImages() {
        return images;
    }

    public void setImages(Uri images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(images, i);
    }
}
