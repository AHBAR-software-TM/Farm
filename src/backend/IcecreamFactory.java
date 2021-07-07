package backend;

public class IcecreamFactory extends Workshop{

    IcecreamFactory(){
        this.level=1;
        this.build_price=550;
        this.produce_time=7;
        resourceType = "backend.PackedMilk";
    }

    @Override
    Product produce() {
        return new Icecream();
    }
    @Override
    int getPrice() {
        return build_price;
    }
}
