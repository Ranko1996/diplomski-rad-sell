package ba.sum.fpmoz.rkoturic.sell.model;

public class Upload {
    private String mName;
    private String mDescription;
    private String mImageUrl;

    private boolean isExpanded;
    private String price;
   private String mUser;

    public Upload() {
        //Prazan konstruktor nam je potreban zbog firebase
    }

    public Upload(String mName, String mDescription, String mImageUrl, String price, String mUser) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mImageUrl = mImageUrl;
        this.isExpanded = isExpanded;
        this.price = price;
        this.mUser = mUser;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
