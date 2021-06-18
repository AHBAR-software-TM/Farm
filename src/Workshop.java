public abstract class Workshop {

    int level;
    int build_price;
    int produce_time;
    int current_time = 0;
    boolean isWorking = false;
    static String resourceType;

    Product update() {
        if (isWorking) {
            current_time++;
            if (current_time == produce_time) {

                Product p = produce();
                current_time = 0;
                isWorking = false;
                return p;

            }
        }
        return null;
    }

    abstract Product produce();

    boolean startWorking(Inventory inv){
        if(isWorking){
            System.out.println(this.getClass().getSimpleName()+" is already assigned to work.");
            return false;
        }
        Product p = inv.getProduct(resourceType);
        if (p!=null) {
            isWorking = true;
            current_time = 0;
            System.out.println(this.getClass().getSimpleName() + " started working.");
            return true;
        }
        System.out.println("Not enough resource.");
        return false;
    }
    int getPrice(){
        return build_price;
    }

}
