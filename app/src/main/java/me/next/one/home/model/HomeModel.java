package me.next.one.home.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NeXT on 16/9/12.
 */
public class HomeModel implements Parcelable {

    /**
     * strLastUpdateDate : 2016-09-07 14:07:35
     * strDayDiffer :
     * strHpId : 1464
     * strHpTitle : VOL.1437
     * strThumbnailUrl : http://image.wufazhuce.com/FmBxzN7EV5jjD3Ceyr4omJHb5Xd_
     * strOriginalImgUrl : http://image.wufazhuce.com/FmBxzN7EV5jjD3Ceyr4omJHb5Xd_
     * strAuthor : 你所看到的未必真实&于雷 作品
     * strContent : 幸福是做你来到这个世界上注定要做的事，幸福是你所从事的事业和你这个人的本性是一致的。 by 乔纳森·弗兰岑
     * strMarketTime : 2016-09-13
     * sWebLk : http://m.wufazhuce.com/one/1464
     * strPn : 1225
     * wImgUrl :
     */

    private String strLastUpdateDate;
    private String strDayDiffer;
    private String strHpId;
    private String strHpTitle;
    private String strThumbnailUrl;
    private String strOriginalImgUrl;
    private String strAuthor;
    private String strContent;
    private String strMarketTime;
    private String sWebLk;
    private String strPn;
    private String wImgUrl;

    public String getStrLastUpdateDate() {
        return strLastUpdateDate;
    }

    public void setStrLastUpdateDate(String strLastUpdateDate) {
        this.strLastUpdateDate = strLastUpdateDate;
    }

    public String getStrDayDiffer() {
        return strDayDiffer;
    }

    public void setStrDayDiffer(String strDayDiffer) {
        this.strDayDiffer = strDayDiffer;
    }

    public String getStrHpId() {
        return strHpId;
    }

    public void setStrHpId(String strHpId) {
        this.strHpId = strHpId;
    }

    public String getStrHpTitle() {
        return strHpTitle;
    }

    public void setStrHpTitle(String strHpTitle) {
        this.strHpTitle = strHpTitle;
    }

    public String getStrThumbnailUrl() {
        return strThumbnailUrl;
    }

    public void setStrThumbnailUrl(String strThumbnailUrl) {
        this.strThumbnailUrl = strThumbnailUrl;
    }

    public String getStrOriginalImgUrl() {
        return strOriginalImgUrl;
    }

    public void setStrOriginalImgUrl(String strOriginalImgUrl) {
        this.strOriginalImgUrl = strOriginalImgUrl;
    }

    public String getStrAuthor() {
        return strAuthor;
    }

    public void setStrAuthor(String strAuthor) {
        this.strAuthor = strAuthor;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public String getStrMarketTime() {
        return strMarketTime;
    }

    public void setStrMarketTime(String strMarketTime) {
        this.strMarketTime = strMarketTime;
    }

    public String getSWebLk() {
        return sWebLk;
    }

    public void setSWebLk(String sWebLk) {
        this.sWebLk = sWebLk;
    }

    public String getStrPn() {
        return strPn;
    }

    public void setStrPn(String strPn) {
        this.strPn = strPn;
    }

    public String getWImgUrl() {
        return wImgUrl;
    }

    public void setWImgUrl(String wImgUrl) {
        this.wImgUrl = wImgUrl;
    }

    @Override
    public String toString() {
        return "HomeModel{" +
                "strLastUpdateDate='" + strLastUpdateDate + '\'' +
                ", strDayDiffer='" + strDayDiffer + '\'' +
                ", strHpId='" + strHpId + '\'' +
                ", strHpTitle='" + strHpTitle + '\'' +
                ", strThumbnailUrl='" + strThumbnailUrl + '\'' +
                ", strOriginalImgUrl='" + strOriginalImgUrl + '\'' +
                ", strAuthor='" + strAuthor + '\'' +
                ", strContent='" + strContent + '\'' +
                ", strMarketTime='" + strMarketTime + '\'' +
                ", sWebLk='" + sWebLk + '\'' +
                ", strPn='" + strPn + '\'' +
                ", wImgUrl='" + wImgUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.strLastUpdateDate);
        dest.writeString(this.strDayDiffer);
        dest.writeString(this.strHpId);
        dest.writeString(this.strHpTitle);
        dest.writeString(this.strThumbnailUrl);
        dest.writeString(this.strOriginalImgUrl);
        dest.writeString(this.strAuthor);
        dest.writeString(this.strContent);
        dest.writeString(this.strMarketTime);
        dest.writeString(this.sWebLk);
        dest.writeString(this.strPn);
        dest.writeString(this.wImgUrl);
    }

    public HomeModel() {
    }

    protected HomeModel(Parcel in) {
        this.strLastUpdateDate = in.readString();
        this.strDayDiffer = in.readString();
        this.strHpId = in.readString();
        this.strHpTitle = in.readString();
        this.strThumbnailUrl = in.readString();
        this.strOriginalImgUrl = in.readString();
        this.strAuthor = in.readString();
        this.strContent = in.readString();
        this.strMarketTime = in.readString();
        this.sWebLk = in.readString();
        this.strPn = in.readString();
        this.wImgUrl = in.readString();
    }

    public static final Parcelable.Creator<HomeModel> CREATOR = new Parcelable.Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel source) {
            return new HomeModel(source);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };
}
