import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javax.swing.*;

public class view {
    private Circle[] circles;
    private Line[] lines;
    private strategy strategy;
    //private Timer timer=;
    private Pane p1=new Pane();
    public void setStrategy(strategy s){
        strategy=s;
    }
}

interface strategy{
    public abstract void randomdot(int n){}
    public abstract void nextline(){}
}

class stratequick  {
    //private dots dotss;
    private dots[] strack;
    private int top=0;
    private int down=0;
    public stratequick(){ }
    public void setDots(int[][] xy,int n){

        int min;
        int x;
        int y;
        down=0;
        for(int i=0;i<n;i++){
            for(int j=i+1,min=i;j<n;j++){
                if(xy[j][0]<xy[min][0])
                    min=j;
                else if(xy[j][0]==xy[min][0]&&xy[j][1]<xy[min][1])
                    min=j;
            }
            x=xy[min][0];
            y=xy[min][1];
            xy[min][0]=xy[i][0];
            xy[min][1]=xy[i][1];
            xy[i][0]=x;
            xy[i][1]=y;
        }

        strack=new dots[15];
        strack[0]=new dots();
        strack[0].d1=new dot(xy[0][0],xy[0][1]);
        strack[0].d2=new dot(xy[n-1][0],xy[n-1][1]);

        int a=strack[0].d2.y-strack[0].d1.y;
        int b=strack[0].d1.x-strack[0].d2.x;
        int c=strack[0].d1.x*strack[0].d2.y-strack[0].d1.y*strack[0].d2.x;
        int[][] xy1=new int[strack[down].n][2];
        int[][] xy2=new int[strack[down].n][2];
        int n1=0;
        int n2=0;
        for(int i=0;i<n;i++){
            if(isup(a,b,c,xy[i][0],xy[i][1])==-1){
                xy1[n1][0]=xy[i][0];
                xy1[n1][1]=xy[i][1];
                n1++;
            }
            if(isup(a,b,c,xy[i][0].x,xy[i][1])==1){
                xy2[n1][0]=xy[i][0];
                xy2[n1][1]=xy[i][1];
                n2++;
            }
        }
        strack[0].ds=new dot[n1];
        for(int i=0;i<n1;i++){
            strack[0].ds[i]=new dot(xy1[i][0],xy1[i][1]);
        }
        strack[0].n=n1;
        strack[0].isup=-1;
        strack[1]=new dots();
        strack[1].ds=new dot[n2];
        strack[1].n=n2;
        for(int i=0;i<n2;i++){
            strack[1].ds[i]=new dot(xy2[i][0],xy2[i][1]);
        }
        strack[1].isup=1;
        strack[1].d1=new dot(xy[0][0],xy[0][1]);
        strack[1].d2=new dot(xy[n-1][0],xy[n-1][1]);
        top=2;

        //返回线段
    }
   /* public void randomdot(int n) {

        for(int i=0;i<n;i++){

       }
   }*/

    public void nextline() {
        if(top!=down) {
            int[][] xy1 = new int[strack[down].n][2];
            int[][] xy2 = new int[strack[down].n][2];
            int n1 = 0;
            int n2 = 0;

            int max = 0;
            for (int i = 1; i < strack[down].n; i++) {
                if (area(strack[down].d1, strack[down].d2, strack[max]) < area(strack[down].d1, strack[down].d2, strack[down].ds[i]))
                    max = i;
            }
            int a1 = strack[down].ds[max].y - strack[down].d1.y;
            int b1 = strack[down].d1.x - strack[down].ds[max].x;
            int c1 = strack[down].d1.x * strack[down].ds[max].y - strack[down].d1.y * strack[down].ds[max].x;
            //找出线上或线下的点

            int a2 = strack[down].ds[max].y - strack[down].d2.y;
            int b2 = strack[down].d2.x - strack[down].ds[max].x;
            int c2 = strack[down].d2.x * strack[down].ds[max].y - strack[down].d2.y * strack[down].ds[max].x;
            //找出线上或线下的点

            for(int i=0;i<strack[down].n;i++){
                if(isup(a1,b1,c1,strack[down].ds[i].x,strack[down].ds[i].y)==strack[down].isup){
                    xy1[n1][0]=strack[down].ds[i].x;
                    xy1[n1][1]=strack[down].ds[i].y;
                    n1++;
                }
                if(isup(a2,b2,c2,strack[down].ds[i].x,strack[down].ds[i].y)==strack[down].isup){
                    xy2[n2][0]=strack[down].ds[i].x;
                    xy2[n2][1]=strack[down].ds[i].y;
                    n2++;
                }
            }
            strack[top]=new dots();
            strack[top].ds=new dot[n1];
            strack[top].n=n1;
            strack[top].isup=strack[down].isup;
            for(int i=0;i<n1;i++){
                strack[i].ds[i]=new dot(xy1[i][0],xy1[i][1]);
            }
            top++;
            strack[top]=new dots();
            strack[top].ds=new dot[n2];
            strack[top].n=n2;
            strack[top].isup=strack[down].isup;
            for(int i=0;i<n2;i++){
                strack[i].ds[i]=new dot(xy2[i][0],xy2[i][1]);
            }
            top++;
            //判断是否为一 撤销线
            //返回线段
            down++;
        }
    }
    private double area(dot d1,dot d2,dot d3){
        int result =d1.x*d2.y+d3.x*d1.y+d2.x*d3.y-d3.x*d2.y-d2.x*d1.y-d1.x*d3.y;
        if(result>=0)
            return result/2.0;
        else
            return -result/2.0;
    }
    private int isup(int a,int b,int c,int x,int  y){
        if(a*x+b*y-c>0)
            return -1;
        else if(a*x+b*y-c==0)
            return 0;
        else
            return 1;
    }
}
class dot{
    public int x;
    public int y;
    public dot(int x,int y){
        this.x=x;
        this.y=y;
    }
}
class dots{
    public dot[] ds;
    public int n=0;
    public dot d1;
    public dot d2;
    public int isup;//
    public dots(){ }
}