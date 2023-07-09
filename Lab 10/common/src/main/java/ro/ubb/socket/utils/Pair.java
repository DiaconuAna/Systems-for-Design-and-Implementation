package ro.ubb.socket.utils;

public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }


    public String toString()
    {
        return "(" + first + "," + second + ")";
    }

    /**
     * Gets the "first" parameter of a Pair entity.
     * @return A - first
     */
    public A getFirst() {
        return first;
    }

    /**
     * Sets the object's "first" parameter to the given parameter value.
     * @param first - new first
     */
    public void setFirst(A first) {
        this.first = first;
    }

    /**
     * Gets the "second" parameter of a Pair entity.
     * @return A - second
     */
    public B getSecond() {
        return second;
    }

    /**
     * Sets the object's "second" parameter to the given parameter value.
     * @param second - new second
     */
    public void setSecond(B second) {
        this.second = second;
    }
}