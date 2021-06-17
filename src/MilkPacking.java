public class MilkPacking extends Workshop{

    MilkPacking(){
        this.level=1;
        this.build_price=400;
        this.produce_time=6;
        resourceType = "Milk";

    }

    @Override
    Product produce() {
        return new PackedMilk();
    }
}
