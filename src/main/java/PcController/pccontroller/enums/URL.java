package PcController.pccontroller.enums;

public enum URL {
    YOUTUBE("www.youtube.com"),
    DISNEY("https://www.disneyplus.com/el-gr/home");

    private final String link;
    URL(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
