import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class view extends Application{
    private ArrayList<spot> spotArrayList=new ArrayList<>();
    private ArrayList<Circle> circleArrayList=new ArrayList<>();
    private ArrayList<Line> lineArrayList=new ArrayList<>();
    private ArrayList<Text> textArrayList=new ArrayList<>();
    private Timeline timeline;
    private  Pane pane=new Pane();
    private BorderPane borderPane=new BorderPane();
    private GridPane gridPane=new GridPane();
    private  Label labeltishi=new Label("点击鼠标生成一个点");
    private tudata data;
    private int maxcolor=1;
    private boolean finish=false;
    private int fristSpot;
    private boolean frist=false;

    public void setColor(int n,int color){
        Circle circle=circleArrayList.get(n);
        circle.setRadius(9);
        switch (color){
            case 1: circle.setFill(Color.rgb(234,207,2));break;
            case 2:circle.setFill(Color.rgb(108,137,11));break;
            case 3:circle.setFill(Color.rgb(170,184,163));break;
            case 4:circle.setFill(Color.rgb(235,237,244));break;
            case 5:circle.setFill(Color.rgb(122,2,60));break;
            case 6:circle.setFill(Color.rgb(127,24,116));break;
            case 7:circle.setFill(Color.rgb(223, 60, 77));break;
            default:circle.setFill(Color.rgb(93, 35, 244));break;
        }

    }
    public void revokeColor(int n){
        Circle circle=circleArrayList.get(n);
        circle.setFill(Color.BURLYWOOD);
        circle.setRadius(6);
    }

    public void addMaxColor(int maxColor){
        maxcolor=maxColor;
    }
    @Override
    public void start(Stage primaryStage)  {
        timeline=new Timeline(new KeyFrame(Duration.millis(800),event ->{
            if(!finish) {
                finish = data.oneStep();
                labeltishi.setText("尝试" + (maxcolor - 1) + "种颜色染色失败，现在尝试" + maxcolor + "种颜色染色");
            }
            if(finish) {
                labeltishi.setText("" + maxcolor + "种颜色染色成功");
            }
        } ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        Button buttonStart=new Button("开始");
        Button buttonPause=new Button("暂停");
        Button buttonNext=new Button("下一步");
        Button buttonOK=new Button("OK");
        Button buttonok=new Button("ok");

        BorderPane borderPane=new BorderPane();
        gridPane.addRow(0,buttonStart,buttonPause,buttonNext);
        gridPane.setHgap(10);
        pane.setPrefSize(500,500);
        pane.setMinSize(300,300);
        pane.setOnMouseClicked(e-> {
            spotArrayList.add(new spot((int) e.getX(), (int) e.getY()));
            Circle circle=new Circle((int) e.getX(), (int) e.getY(),6);
            circle.setFill(Color.BURLYWOOD);
            pane.getChildren().add(circle);
            circleArrayList.add(circle);
        });
        //第一次ok
        buttonOK.setOnAction(e->{
            labeltishi.setText("点击两个点形成连接");
            borderPane.setBottom(buttonok);
            pane.setOnMouseClicked(ee->{
                int x=(int)ee.getX();
                int y=(int)ee.getY();
                int i;
                spot spott;
                for( i=0;i<spotArrayList.size();i++){
                    spott=spotArrayList.get(i);
                    if(spott.x-6<=x&&x<=spott.x+6&&spott.y-6<=y&&y<=spott.y+6){
                        break;
                    }
                }
                if(i<spotArrayList.size()) {
                    if (frist&&i!=fristSpot) {
                        spot spotfirst = spotArrayList.get(fristSpot);
                        spot spotnow=spotArrayList.get(i);
                        spotnow.spotnext.add(fristSpot);
                        spotfirst.spotnext.add(i);
                        Circle circle = circleArrayList.get(fristSpot);
                        circle.setRadius(6);
                        Line line=new Line(x,y,spotfirst.x,spotfirst.y);
                        line.setStrokeWidth(4);
                        line.setStroke(Color.AQUA);
                        pane.getChildren().add(line);
                        frist = false;
                    } else {
                        Circle circle=circleArrayList.get(i);
                        circle.setRadius(8);
                        fristSpot = i;
                        frist = true;
                    }
                }
            });
        });
        //第二次ok
        buttonok.setOnAction(e->{
            labeltishi.setText("尝试一种颜色染色");
            borderPane.setBottom(gridPane);
            pane.setOnMouseClicked(null);
            data=new tudata(spotArrayList,this);
        });
        buttonStart.setOnAction(e->timeline.play());
        buttonPause.setOnAction(e->timeline.pause());
        buttonNext.setOnAction(e-> {
            timeline.pause();
            if (!finish) {
                finish = data.oneStep();
                labeltishi.setText("尝试" + (maxcolor - 1) + "种颜色染色失败，现在尝试" + maxcolor + "种颜色染色");
            }
            if(finish) {
                labeltishi.setText("" + maxcolor + "种颜色染色成功");
            }
        });


        borderPane.setCenter(pane);
        borderPane.setTop(labeltishi);
        borderPane.setBottom(buttonOK);
        Scene scene=new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
