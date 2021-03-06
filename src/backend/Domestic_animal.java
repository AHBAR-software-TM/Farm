package backend;

public abstract class Domestic_animal extends Animal implements Comparable<Domestic_animal>{

    public abstract Product produce();

    int price=-1;
    public int life=0;
    public int PRODUCE_TIME;
    public int tillProduce = 0;
    //backend.Product productToBeConed; // :))))))

    @Override
    public Dir move(Map[][] map,int x,int y) {
        if (this.life>50)
            return random_move(map,x,y);

        //finding nearest grass
        int X = 0;
        int Y = 0;
        int min = 0;
        Dir direction;
        boolean is_grass_on_map = false;
        //initializing min
        for (int i = 0; i < 6 && !is_grass_on_map; i++) {
            for (int j = 0; j < 6 && !is_grass_on_map; j++) {
                if (map[i][j].getGrass() > 0) {
                    min = Math.abs(i - x) + Math.abs(j - y);
                    is_grass_on_map = true;
                    X = i;
                    Y = j;
                }
            }
        }
        if (is_grass_on_map) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    int m = Math.abs(i - x) + Math.abs(j - y);
                    if (m < min && map[i][j].getGrass() > 0) {
                        min = m;
                        X = i;
                        Y = j;
                    }
                }
            }

            //finding direction
            //returns null if current location has grass
            //(0,0) location is on top left of screen
            if (X > x && Y > y) {
                double d = Math.random();
                if (d < 0.5) direction = Dir.RIGHT;
                else direction = Dir.DOWN;
            } else if (X > x && Y < y) {
                double d = Math.random();
                if (d < 0.5) direction = Dir.RIGHT;
                else direction = Dir.UP;
            } else if (X < x && Y > y) {
                double d = Math.random();
                if (d < 0.5) direction = Dir.LEFT;
                else direction = Dir.DOWN;
            } else if (X < x && Y < y) {
                double d = Math.random();
                if (d < 0.5) direction = Dir.LEFT;
                else direction = Dir.UP;
            } else if (X == x && Y > y)
                direction = Dir.DOWN;

            else if (X == x && Y < y)
                direction = Dir.UP;

            else if (X > x && Y == y)
                direction = Dir.RIGHT;

            else if (X < x && Y == y)
                direction = Dir.LEFT;
            //if(X==x && Y==y)
            else
                direction = null;


            return direction;
        }

        //move random if the maps have no grass
        // (0,0) location is on top left of screen
        else return random_move(map, x, y);
    }



    public Product update() {
        if (currentlyIn != null){
            life -= 10;
            if (life <= 0)
                return new Dead();
            tillProduce++;
            if (tillProduce == PRODUCE_TIME) {

                Product p = produce();
                tillProduce = 0;
                return p;

            }
            return null;
        }
        return null;
    }
    void eat(){
        life=110;
    }
    void hunted(World world){
        world.allDomestics.remove(this);
        Integer count = world.boughtTillNow.get(getClass().getSimpleName());
        if(count != null){
            world.boughtTillNow.replace(getClass().getSimpleName(),--count);
        }
        //this.currentlyIn.animalsInside.remove(this);
    }

    @Override
    public int compareTo(Domestic_animal a) {
        return Integer.compare((this.life), a.life);
    }
}
