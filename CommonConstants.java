import java.awt.*;

public class CommonConstants {
    //Color
    public static final Color BACKGRAUND_COLOR = Color.black;
    public static final Color BAR_COLOR = Color.white;

    //Size
    public static final Dimension FRAME_SIZE = new Dimension(400,700);
    public static final Dimension BOARD_SIZE = new Dimension((int)(FRAME_SIZE.width*0.96),(int)(FRAME_SIZE.height*0.60));
    public static final Dimension BUTTON_SIZE = new Dimension(100,100);
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/3), (int)(FRAME_SIZE.height/6));

    //Text
    public static final String X_LABEL = "X";
    public static final String O_LABEL = "0";
    public static final String SCORE_LABEL = "X: 0 | O: 0";
}
