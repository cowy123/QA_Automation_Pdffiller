package trelloTest.api.model;

public class CardLabel {

    public String name;
    public String id;
    public String color;

    @Override
    public String toString() {
        return "CardLabel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
