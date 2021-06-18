public class EggPowderPlant extends Workshop{

    EggPowderPlant(){
        this.level=1;
        this.build_price=150;
        this.produce_time=4;
        resourceType="Egg";
    }

    @Override
    Product produce() {
        return new Flour();
    }
    @Override
    int getPrice() {
        return build_price;
    }
}
