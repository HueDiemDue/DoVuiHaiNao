package dovui.com.dovuihainao.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import dovui.com.dovuihainao.R;
import dovui.com.dovuihainao.database.DBManager;
import dovui.com.dovuihainao.dialog.AnswerDialog;
import dovui.com.dovuihainao.dialog.ContinueDialog;
import dovui.com.dovuihainao.dialog.SaveScoreDialog;
import dovui.com.dovuihainao.models.Player;
import dovui.com.dovuihainao.models.Question;
import dovui.com.dovuihainao.service.MyService;
import dovui.com.dovuihainao.sharepreference.PreferenceUtils;
import dovui.com.dovuihainao.utils.Constants;

/**
 * Created by hue on 30/05/2017.
 */

public class QuestionActivity extends AppCompatActivity {
    private ImageView imgBack, imgShare;
    private TextView tvQuestion, tvA, tvB, tvC, tvD, tvPoint;
    private DBManager dbManager;
    private ArrayList<Question> arrQuestion = new ArrayList<>();
    private ArrayList<Player> arrPoint = new ArrayList<>();
    private Bitmap myBitmap;
    private RelativeLayout rlv;
    private Vibrator vibrator;


    private int check = 0;
    private int score = 0;
    private int position = 0;
    private int point_current = 0;
    private int score_high = 0;
    private boolean cmt = false;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private Typeface typeface;
    private Animation animation, animation_translate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        typeface = Typeface.createFromAsset(getAssets(), Constants.FONT);
        animation = AnimationUtils.loadAnimation(QuestionActivity.this, R.anim.fade_in);
        animation_translate = AnimationUtils.loadAnimation(QuestionActivity.this, R.anim.translate);

        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        initView();
        initData();
        tvQuestion.setText((position + 1) + ". " + arrQuestion.get(position).getQuestion());
        tvA.setText(arrQuestion.get(position).getA());
        tvB.setText(arrQuestion.get(position).getB());
        tvC.setText(arrQuestion.get(position).getC());
        tvD.setText(arrQuestion.get(position).getD());
        tvPoint.setText("Point: " + score);
        fadeIn();
        initAction();
        boolean b = PreferenceUtils.getBooleanFromPreference(Constants.Intents.SWITCH_VOLUME, this);
        if (b) {
            startService(new Intent(this, MyService.class));
            sendBroadcast(new Intent(Constants.Intents.CONTINUE));
        } else {
            sendBroadcast(new Intent(Constants.Intents.SWITCH_BAR_OFF));
            stopService(new Intent(this, MyService.class));
        }
        score_high = PreferenceUtils.getIntFromPreference("score5", QuestionActivity.this);

    }

    public void fadeIn() {
        animation_translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvA.setEnabled(false);
                tvB.setEnabled(false);
                tvC.setEnabled(false);
                tvD.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvA.setEnabled(true);
                tvB.setEnabled(true);
                tvC.setEnabled(true);
                tvD.setEnabled(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tvQuestion.startAnimation(animation_translate);

        tvA.startAnimation(animation);
        tvB.startAnimation(animation);
        tvC.startAnimation(animation);
        tvD.startAnimation(animation);
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvA = (TextView) findViewById(R.id.tvA);
        tvB = (TextView) findViewById(R.id.tvB);
        tvC = (TextView) findViewById(R.id.tvC);
        tvD = (TextView) findViewById(R.id.tvD);
        tvPoint = (TextView) findViewById(R.id.tvPoint);
        rlv = (RelativeLayout) findViewById(R.id.rlvQuestion);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        typeFace();
    }

    public void typeFace() {
        tvQuestion.setTypeface(typeface);
        tvA.setTypeface(typeface);
        tvB.setTypeface(typeface);
        tvC.setTypeface(typeface);
        tvD.setTypeface(typeface);
        tvPoint.setTypeface(typeface);
    }

    private void initData() {
        dbManager = new DBManager(QuestionActivity.this);
        arrQuestion.clear();
        arrQuestion.addAll(dbManager.getData(Constants.TABLE_NAME));
        Collections.shuffle(arrQuestion);
        arrPoint.add(new Player("Player1", 0));
        arrPoint.add(new Player("Player2", 0));
        arrPoint.add(new Player("Player3", 0));
        arrPoint.add(new Player("Player4", 0));
        arrPoint.add(new Player("Player5", 0));

    }

    private void setUpTextView(int position) {
        tvQuestion.setText((position + 1) + " . " + arrQuestion.get(position).getQuestion());
        tvA.setText(arrQuestion.get(position).getA());
        tvB.setText(arrQuestion.get(position).getB());
        tvC.setText(arrQuestion.get(position).getC());
        tvD.setText(arrQuestion.get(position).getD());
    }

    private void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 1;
                if (arrQuestion.get(position).getAnswer() == a) {
                    position = position + 1;
                    score++;
                    cmt = true;
                    sendBroadcast(new Intent(Constants.Intents.CLICK_TRUE));
                } else {
                    check++;
                    cmt = false;
                    vibrator.vibrate(100);
                }
                AnswerDialog dialog = new AnswerDialog(QuestionActivity.this, cmt, check, score, arrQuestion.get(position).getAnswer(), new AnswerDialog.onClickImage() {
                    @Override
                    public void onClickBack() {
                        setUpTextView(position);
                        if (cmt) {
                            fadeIn();
                        }
                        tvPoint.setText("ĐIỂM: " + score);
                        sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                    }

                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                if (check >= 3) {
                    sendBroadcast(new Intent(Constants.Intents.OVER_GAME));
                    if (score > score_high) {
                        SaveScoreDialog dialogScore = new SaveScoreDialog(QuestionActivity.this, score, new SaveScoreDialog.onClick() {
                            @Override
                            public void onClickOk() {
                                sendBroadcast(new Intent(Constants.Intents.CONTINUE));
                            }
                        });
                        dialogScore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogScore.show();
                        dialogScore.setCancelable(false);
                        dialogScore.setCanceledOnTouchOutside(false);
                    }
                    position = 0;
                    check = 0;
                    score = 0;
                    ContinueDialog continueDialog = new ContinueDialog(QuestionActivity.this, score, check, new ContinueDialog.onClick() {
                        @Override
                        public void onClickOk() {
                            setUpTextView(position);
                            if (cmt) {
                                fadeIn();
                            }
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                        }

                        @Override
                        public void onClickCancel() {
                            finish();
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                            onBackPressed();

                        }
                    });

                    continueDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    continueDialog.show();
                    continueDialog.setCancelable(false);
                    continueDialog.setCanceledOnTouchOutside(false);


                }
            }
        });
        tvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b = 2;
                if (b == arrQuestion.get(position).getAnswer()) {
                    position = position + 1;
                    Log.d("position", position + "");
                    score++;
                    cmt = true;
                    sendBroadcast(new Intent(Constants.Intents.CLICK_TRUE));
                } else {
                    check++;
                    cmt = false;
                    vibrator.vibrate(100);
                }
                AnswerDialog dialog = new AnswerDialog(QuestionActivity.this, cmt, check, score, arrQuestion.get(position).getAnswer(), new AnswerDialog.onClickImage() {
                    @Override
                    public void onClickBack() {
                        setUpTextView(position);
                        if (cmt) {
                            fadeIn();
                        }
                        tvPoint.setText("ĐIỂM: " + score);
                        sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                    }

                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                if (check >= 3) {
                    sendBroadcast(new Intent(Constants.Intents.OVER_GAME));
                    if (score > point_current && score > 0) {
                        SaveScoreDialog dialogScore = new SaveScoreDialog(QuestionActivity.this, score, new SaveScoreDialog.onClick() {
                            @Override
                            public void onClickOk() {
                                sendBroadcast(new Intent(Constants.Intents.CONTINUE));
                            }
                        });
                        dialogScore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogScore.show();
                        dialogScore.setCancelable(false);
                        dialogScore.setCanceledOnTouchOutside(false);
                    }
                    position = 0;
                    check = 0;
                    score = 0;
                    ContinueDialog continueDialog = new ContinueDialog(QuestionActivity.this, score, check, new ContinueDialog.onClick() {
                        @Override
                        public void onClickOk() {
                            setUpTextView(position);
                            if (cmt) {
                                fadeIn();
                            }
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                        }

                        @Override
                        public void onClickCancel() {
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                            onBackPressed();

                        }
                    });

                    continueDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    continueDialog.show();
                    continueDialog.setCancelable(false);
                    continueDialog.setCanceledOnTouchOutside(false);
                }
            }
        });
        tvC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = 3;
                if (c == arrQuestion.get(position).getAnswer()) {
                    position = position + 1;
                    Log.d("position", position + "");
                    score++;
                    cmt = true;
                    sendBroadcast(new Intent(Constants.Intents.CLICK_TRUE));
                } else {
                    check++;
                    cmt = false;
                    vibrator.vibrate(100);

                }
                AnswerDialog dialog = new AnswerDialog(QuestionActivity.this, cmt, check, score, arrQuestion.get(position).getAnswer(), new AnswerDialog.onClickImage() {
                    @Override
                    public void onClickBack() {
                        setUpTextView(position);
                        if (cmt) {
                            fadeIn();
                        }
                        tvPoint.setText("ĐIỂM: " + score);
                        sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                    }

                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                if (check >= 3) {
                    sendBroadcast(new Intent(Constants.Intents.OVER_GAME));
                    if (score > point_current && score > 0) {
                        SaveScoreDialog dialogScore = new SaveScoreDialog(QuestionActivity.this, score, new SaveScoreDialog.onClick() {
                            @Override
                            public void onClickOk() {
                                sendBroadcast(new Intent(Constants.Intents.CONTINUE));
                            }
                        });
                        dialogScore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogScore.show();
                        dialogScore.setCancelable(false);
                        dialogScore.setCanceledOnTouchOutside(false);
                    }
                    position = 0;
                    check = 0;
                    score = 0;
                    ContinueDialog continueDialog = new ContinueDialog(QuestionActivity.this, score, check, new ContinueDialog.onClick() {
                        @Override
                        public void onClickOk() {
                            setUpTextView(position);
                            if (cmt) {
                                fadeIn();
                            }
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                        }

                        @Override
                        public void onClickCancel() {
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                            onBackPressed();

                        }
                    });

                    continueDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    continueDialog.show();
                    continueDialog.setCancelable(false);
                    continueDialog.setCanceledOnTouchOutside(false);
                }

            }
        });
        tvD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = 4;
                if (d == arrQuestion.get(position).getAnswer()) {
                    position = position + 1;
                    Log.d("position", position + "");
                    score++;
                    cmt = true;
                    sendBroadcast(new Intent(Constants.Intents.CLICK_TRUE));
                } else {
                    check++;
                    cmt = false;
                    vibrator.vibrate(100);
                }
                AnswerDialog dialog = new AnswerDialog(QuestionActivity.this, cmt, check, score, arrQuestion.get(position).getAnswer(), new AnswerDialog.onClickImage() {
                    @Override
                    public void onClickBack() {
                        setUpTextView(position);
                        if (cmt) {
                            fadeIn();
                        }
                        tvPoint.setText("ĐIỂM: " + score);
                        sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                    }

                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                if (check >= 3) {
                    sendBroadcast(new Intent(Constants.Intents.OVER_GAME));
                    if (score > point_current && score > 0) {

                        SaveScoreDialog dialogScore = new SaveScoreDialog(QuestionActivity.this, score, new SaveScoreDialog.onClick() {
                            @Override
                            public void onClickOk() {
                                sendBroadcast(new Intent(Constants.Intents.CONTINUE));
                            }
                        });
                        dialogScore.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogScore.show();
                        dialogScore.setCancelable(false);
                        dialogScore.setCanceledOnTouchOutside(false);
                    }
                    position = 0;
                    check = 0;
                    score = 0;
                    ContinueDialog continueDialog = new ContinueDialog(QuestionActivity.this, score, check, new ContinueDialog.onClick() {
                        @Override
                        public void onClickOk() {
                            setUpTextView(position);
                            if (cmt) {
                                fadeIn();
                            }
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                        }

                        @Override
                        public void onClickCancel() {
                            sendBroadcast(new Intent(Constants.Intents.GAME_PLAY));
                            onBackPressed();

                        }
                    });

                    continueDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    continueDialog.show();
                    continueDialog.setCancelable(false);
                    continueDialog.setCanceledOnTouchOutside(false);

                }
            }
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlv.post(new Runnable() {
                    public void run() {

                        //take screenshot
                        myBitmap = captureScreen(rlv);
                        if (myBitmap != null) {
                            //save image to SD card
                            saveBitmap(myBitmap);
                            send(myBitmap);
                        }

                    }
                });
            }
        });
    }

    public static Bitmap captureScreen(View v) {

        Bitmap screenshot = null;
        try {

            if (v != null) {

                screenshot = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }

        } catch (Exception e) {
            Log.d("ScreenShotActivity", "Failed to capture screenshot because:" + e.getMessage());
        }

        return screenshot;
    }

    private void saveBitmap(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images_dovuihainao");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void send(Bitmap mBitmap) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);


        OutputStream outstream;
        try {
            outstream = getContentResolver().openOutputStream(uri);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendBroadcast(new Intent(Constants.Intents.PAUSE));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


}
