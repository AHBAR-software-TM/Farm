public class Spinnery extends Workshop{

    Spinnery(){
        this.level=1;
        this.build_price=250;
        this.produce_time=5;
        resourceType="Feather";
    }

    @Override
    Product produce() {
        return new Cloth();
    }
}
