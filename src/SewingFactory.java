public class SewingFactory extends Workshop{

    SewingFactory(){
        this.level=1;
        this.build_price=400;
        this.produce_time=6;
        resourceType="Cloth";
    }

    @Override
    Product produce() {
        return new Shirt();
    }
    @Override
    int getPrice() {
        return build_price;
    }

}
