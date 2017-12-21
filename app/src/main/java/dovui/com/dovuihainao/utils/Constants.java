package dovui.com.dovuihainao.utils;

import android.os.Environment;

/**
 * Created by hue on 30/05/2017.
 */

public class Constants {
    public static final String PATH = Environment.getDataDirectory().getPath() + "/data/dovui.com.dovuihainao/database/thanhtroll.sqlite";
    public static final String ID = "Id";

    public static final String QUESTION = "Question";
    public static final String ANSWER_A = "AnswerA";
    public static final String ANSWER_B = "AnswerB";
    public static final String ANSWER_C = "AnswerC";
    public static final String ANSWER_D = "AnswerD";
    public static final String ANSWER = "CorrectAnswer";
    public static final String TYPE = "Type";
    public static final String NOTE = "Note";
    public static final String FAVORITE = "favorite";
    public static final String TABLE_NAME = "tbl_question";

    public class Save {
        public static final String POSITION = "POSITION";
        public static final String CHECK = "CHECK";
        public static final String SCORE = "SCORE.CURRENT";

    }

    public class Intents {
        public static final String CLICK_TRUE = "com.click";
        public static final String EXIT = "exit";
        public static final String SWITCH_BAR_OFF = "seek.bar";
        public static final String OVER_GAME = "com.over.game";
        public static final String GAME_PLAY = "com.play.game";
        public static final String SWITCH_VOLUME = "com.volume.switch";
        public static final String PAUSE = "com.pause";
        public static final String CONTINUE = "com.continue";

    }

    public static final String FONT = "fonts/SFUJ.TTF";



}
