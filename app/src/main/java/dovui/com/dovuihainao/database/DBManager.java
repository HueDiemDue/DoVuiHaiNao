package dovui.com.dovuihainao.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import dovui.com.dovuihainao.models.Question;
import dovui.com.dovuihainao.utils.Constants;

import static android.content.ContentValues.TAG;
import static dovui.com.dovuihainao.utils.Constants.PATH;

/**
 * Created by hue on 30/05/2017.
 */

public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private ArrayList<Question> arrQuestion = new ArrayList<>();

    public DBManager(Context context) {
        copyDatabase(context);
        this.context = context;
    }

    private void copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("thanhtroll.sqlite");
            File file = new File(PATH);
            if (!file.exists()) {
                File parent = file.getParentFile();
                parent.mkdirs();
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while (count != -1) {
                    outputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                outputStream.close();
            }
            inputStream.close();
        } catch (Exception ex) {

        }
    }

    public void openDatabase() {
        database = context.openOrCreateDatabase(PATH, Context.MODE_APPEND, null);
    }

    public void closeDatabase() {
        database.close();
    }

    public ArrayList<Question> getData(String TABLE_NAME) {
        arrQuestion.clear();
        openDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        int indexId = cursor.getColumnIndex(Constants.ID);
        int indexQuestion = cursor.getColumnIndex(Constants.QUESTION);
        int indexA = cursor.getColumnIndex(Constants.ANSWER_A);
        int indexB = cursor.getColumnIndex(Constants.ANSWER_B);
        int indexC = cursor.getColumnIndex(Constants.ANSWER_C);
        int indexD = cursor.getColumnIndex(Constants.ANSWER_D);
        int indexAnswser = cursor.getColumnIndex(Constants.ANSWER);
        int indexType = cursor.getColumnIndex(Constants.TYPE);
        int indexNote = cursor.getColumnIndex(Constants.NOTE);
        int indexFavorite = cursor.getColumnIndex(Constants.FAVORITE);
        while (!cursor.isAfterLast()) {
            Log.e(TAG, "read!!!!!");
            long id = cursor.getLong(indexId);
            String question = cursor.getString(indexQuestion);
            String decodeQuestion = decodeBase64(question);
            decodeQuestion = getFomatString(decodeQuestion);
            String a = cursor.getString(indexA);
            String decodeA = decodeBase64(a);
            String b = cursor.getString(indexB);
            String decodeB = decodeBase64(b);
            String c = cursor.getString(indexC);
            String decodeC = decodeBase64(c);
            String d = cursor.getString(indexD);
            String decodeD = decodeBase64(d);
            int answer = cursor.getInt(indexAnswser);
            int type = cursor.getInt(indexType);
            String note = cursor.getString(indexNote);
            String favorite = cursor.getString(indexFavorite);
            arrQuestion.add(new Question(id, decodeQuestion, decodeA, decodeB, decodeC, decodeD, answer, type, note, favorite));
            cursor.moveToNext();
        }
        closeDatabase();
        return arrQuestion;
    }

    public static String decodeBase64(String input) {
        byte[] data = Base64.decode(input, 0);
        String output = null;
        try {
            output = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String getFomatString(String input) {
        String output = "";
        output = input.replace("<br/>","\n");
        return output;

    }
}
