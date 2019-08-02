class homeMadeBoss implements Serializable {

    private int clock;
    private int groupType;
    private int chickenType;
    private int groupSpeedX =1;
    private int groupSpeedY =1;
    private int number;
    private int n;
    private int m;
//    private int x;
//    private int y;

    private final ArrayList<ChickenObject> chickenObjects = new ArrayList<>();
    private final ArrayList<int[]> locs=new ArrayList<>();
    public homeMadeBoss (int number,int wave,int chickenType,int m) {
        clock =0;
        this.m=m;
        setText(wave!=-1?"Wave "+wave:"final Wave");
        this.chickenType=chickenType;
        this.number=number;
        n=7;
    }
    private int[] getNowLocation(){
        return new int[]{getX(), getY()};
    }

    private void updateLocations (int i){
        int X=0;
        int Y=0;
        X+=groupSpeedX;
        Y+=groupSpeedY;
        if(X<50){
            groupSpeedX =1;
        }else if(X > Constants.frameWidth-50)
            groupSpeedX =-1;
        if(Y<50){
            groupSpeedX =1;
        }else if(X > Constants.frameHeight-50)
            groupSpeedX =-1;
        chickenObjects.get(i).stepToLocation(new int[]{X, Y});
    }

    public int[] getChickenLocation(int i){
        return chickenObjects.get(i).getLocation();
    }

    @Override
    public void stepClock(){
        clock++;
        move();
        makeChickens();
    }

    @Override
    public boolean dispose() {
        return getChickenObjectsize()==0&&number==0;
    }

    public ArrayList<ChickenObject> getChickenObjects(){
        return new ArrayList<>(chickenObjects);
    }

    public int getChickenObjectsize(){
        return chickenObjects.size();
    }

    public void remove(ChickenObject chicken){
        chickenObjects.remove(chicken);
    }

    public int getGroupType() {
        return groupType;
    }

    public int getChickenType() {
        return chickenType;
    }

    public boolean Alarm(){
        return clock<2000;
    }

    private void makeChickens(){
        if (!Alarm() && clock%50==0 && getChickenObjectsize()<number){
            chickenObjects.add(new ChickenObject(Constants.frameWidth/2,-Constants.frameHeight/2,5,1,m));
        }
        if(getChickenObjectsize()==number){
            number=0;
        }
    }

    private void move(){
        if(clock%10==0) {
            if(groupType ==1) {
                setX(getX() + groupSpeedX);
            }else if(groupType ==2) {
                setX(getX() + groupSpeedX);
                setY(getY() + groupSpeedY);
            }else{
                setX(0);
                setY(0);
            }
        }
        for (int i = 0; i < getChickenObjectsize(); i++) {
            updateLocations(i);
        }
    }

    public int getFrame(){
        if(groupType!=5)
            return clock/40;
        else
            return chickenObjects.get(0).getFrameForBoss();
    }
//    public homeMadeBoss (int x, int y, int width, int height, String text, String paintMethod) {
//
//    }

//    public abstract int[] getChickenLocation(int i){
//
//    }
//    public abstract ArrayList<ChickenObject> getChickenObjects(){
//
//    }
//    public abstract int getChickenObjectsize(){
//
//    }
//    public abstract void remove(ChickenObject chicken){
//
//    }
//    public abstract int getGroupType(){
//
//    }
//    public abstract int getChickenType(){
//
//    }
//    public abstract boolean Alarm(){
//
//    }
//    public abstract int getFrame(){
//
//    }
}