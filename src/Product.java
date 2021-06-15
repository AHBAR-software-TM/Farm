public abstract class Product implements Cloneable{

    int price;
    int volume;
    int disappear_time;

    @Override
    protected Product clone() throws CloneNotSupportedException {
        return ((Product) super.clone());
    }
}
