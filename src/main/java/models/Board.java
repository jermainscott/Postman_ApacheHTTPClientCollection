package models;

public class Board {
    private String id;
    private String name;
    private String url;


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    // Constructors
    public Board() {}


    public Board(String name) {
        this.name = name;
    }

    public Board( String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }


    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }



}
