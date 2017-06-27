package controller.online;

/**
 * Created by Kojy on 28.06.2017.
 */
public interface Chat {
    void addMessage(String name, String timeStamp, String message);
    void addMessage(String name, String message);
    void reset();
}
