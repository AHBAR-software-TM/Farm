public class Bakery extends Workshop{

    Bakery(){
        this.level=1;
        this.build_price=250;
        this.produce_time=5;
        resourceType = "Flour";
    }

    @Override
    Product produce() {
        return new Bread();
    }
}
