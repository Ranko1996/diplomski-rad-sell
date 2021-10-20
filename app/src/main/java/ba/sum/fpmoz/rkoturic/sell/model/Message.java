package ba.sum.fpmoz.rkoturic.sell.model;

public class Message {
    private String author;
    private String adress;
    private String reciver;
    private String name;

    public Message() {

    }

    public Message(String author, String adress, String reciver, String name) {
        this.author = author;
        this.adress = adress;
        this.reciver = reciver;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

}
