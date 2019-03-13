package vn.edu.hcmut.linexo.utils;

/**
 * A container object which contain an event code and {@code @nullable} data of it.
 * It may be use to emit event to some listener.
 */

public class Event {

    /**
     * Shift flag bit to distinguish between views.
     */
    private static final int MODE_SHIFT = 28; //Maximum 16 different views.

    /**
     * Used when need to return an event but do nothing or default action.
     */
    public static final int NONE = -1;

    /***********************************************************************************************
     * Class implementation.
     **********************************************************************************************/

    private int         type;
    private Object[]    data;

    public static Event create(int type) {
        return new Event(type, (Object)null);
    }

    public static Event create(int type, Object... data) {
        return new Event(type, data);
    }

    private Event(int type, Object... data) {
        this.type = type;
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(Object... data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public Object[] getData() {
        return data;
    }
}