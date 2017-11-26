import java.util.ArrayList;

public class tudata {
    private ArrayList<spot> spotArrayList;
    private ArrayList<Integer> colorArrayList = new ArrayList<>();
    private view View;
    private int numb = 0;
    private int maxcolor = 1;

    public tudata(ArrayList<spot> spotArrayList, view View) {
        this.spotArrayList = spotArrayList;
        this.View = View;
        for (int i = 0; i < spotArrayList.size(); i++) {
            colorArrayList.add(0);
        }
    }

    public boolean oneStep() {
        System.out.println("ks");
        if (numb < 0) {
            numb = 0;
            colorArrayList.set(0, 0);
            maxcolor++;
            View.addMaxColor(maxcolor);//
            System.out.println("jia yi");
            return false;
        } else {
            if (numb < spotArrayList.size()) {
                spot spot1 = spotArrayList.get(numb);
                int x = colorArrayList.get(numb) + 1;
                for (; x <= maxcolor; x++) {
                    int xx = 0;
                    for (int ii : spot1.spotnext) {
                        if (x == colorArrayList.get(ii)) {
                            xx = 1;
                            break;
                        }
                    }
                    if (xx == 0)
                        break;
                }
                if (x <= maxcolor) {//染色成功，numb加一
                    colorArrayList.set(numb, x);
                    View.setColor(numb, x);
                    System.out.println("染色成功，numb加一" + "numb " + numb + "x " + x);
                    numb++;
                    return false;
                } else {//染色失败，numb减一
                    colorArrayList.set(numb, 0);
                    View.revokeColor(numb);
                    System.out.println("染色失败，numb减一" + "numb " + numb + "x " + x);
                    numb--;
                    return false;
                }
            } else {
                return true;
            }

        }
    }
}

class spot{
    int x;
    int y;
    public spot(int x,int y){
        this.x=x;
        this.y=y;
    }
    public spot(){

    }
    //int color=0;
    //ArrayList<spot> spotnext=new ArrayList<>();
    ArrayList<Integer> spotnext=new ArrayList<>();
    //int n;
}