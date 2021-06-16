public abstract class Domestic_animal extends Animal {

    public abstract Product produce();

    public int price;
    public int life;
    public int PRODUCE_TIME;
    public int tillProduce = 0;
    //Product productToBeConed; // :))))))

    @Override
    public void walk() {
        //it will find the nearest grass
        // TODO: 2021-05-26  
    }

    Product update(int grass) {
        eat(grass);
        life -= 10;
        if (life <= 0)
            //todo: put hunted here
            return new Dead();
        tillProduce++;
        if (tillProduce == PRODUCE_TIME) {

            Product p = produce();
            tillProduce = 0;
            return p;

        }
        return null;
    }
    void eat(int grass){
        //todo
    }
     void hunted(World world){
        //todo:delete from world and other arrays
         // will be executed by map
         // shouldnt be in update
     }
}
