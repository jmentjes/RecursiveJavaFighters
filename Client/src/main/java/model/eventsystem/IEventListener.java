package model.eventsystem;

/**
 * Created by Kojy on 26.06.2017.
 */
interface IEventListener<T extends Event> {
    void handle(T event);
}
