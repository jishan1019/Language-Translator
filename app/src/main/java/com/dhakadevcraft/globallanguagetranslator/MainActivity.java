package com.dhakadevcraft.globallanguagetranslator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mannan.translateapi.TranslateAPI;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE_SPEECH_INPUT = 100;
    Button button;
    ImageView clear_button,share;
    ImageView copyButton;
    EditText edInput;
    Spinner spLanguage;
    TextView textView;
    ImageView voiceIcon,btnPaste,btnListen;
    TextToSpeech textToSpeech;
    ProgressBar loader;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.edInput = (EditText) findViewById(R.id.edInput);
        this.button = (Button) findViewById(R.id.btn_translate);
        this.textView = (TextView) findViewById(R.id.tvDisplay);
        this.spLanguage = (Spinner) findViewById(R.id.spLanguage);
        this.copyButton = (ImageView) findViewById(R.id.copy_button);
        this.voiceIcon = (ImageView) findViewById(R.id.voiceIcon);
        this.clear_button = (ImageView) findViewById(R.id.clear_button);
        btnPaste = (ImageView) findViewById(R.id.btnPaste);
        btnListen = (ImageView) findViewById(R.id.btnListen);
        share = (ImageView) findViewById(R.id.share);
        loader = findViewById(R.id.loader);

        //-------------------Text To Speatch Code --------------------------------------
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    }
                } else {

                }
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.spLanguage.setAdapter(adapter);

        //-------------------Share Prefrence EdInput Fild Save Code --------------------------------------
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String saveText = sharedPreferences.getString("userInput","");
        edInput.setText(saveText);

        //-------------------Edit text text add sharePrefrence Code --------------------
        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userInput = s.toString();
                editor.putString("userInput", userInput);
                editor.apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //-------------------Translate Button Code --------------------------------------
        this.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.this.edInput.length() == 0) {
                    toast("Please input some text frist.");
                    return;
                }
                showLoader();
                String targetLanguageCode = "bn";
                if (MainActivity.this.spLanguage.getSelectedItem().toString().equals("English")) {
                    targetLanguageCode = "en";
                }
                new TranslateAPI("auto", targetLanguageCode, MainActivity.this.edInput.getText().toString()).setTranslateListener(new TranslateAPI.TranslateListener() {
                    public void onSuccess(String translatedText) {
                        hideLoader();
                        MainActivity.this.textView.setText("" + translatedText);
                    }

                    public void onFailure(String ErrorText) {
                        hideLoader();
                    }
                });
            }
        });

        //--------------------------Edit Text Button--------------------------------------
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null && clipboard.hasPrimaryClip()) {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    String textToPaste = item.getText().toString();
                    edInput.setText(textToPaste);
                    toast("Text Past Success.");
                }
            }
        });
        this.clear_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (textToSpeech != null && textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                }
                MainActivity.this.edInput.setText("");
                MainActivity.this.textView.setText("");
                toast("Text Clear.");
            }
        });
        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this.edInput.length() == 0) {
                    toast("Please input some text frist.");
                    return;
                }
                String textToSpeak = textView.getText().toString();
                textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "listenText");
            }
        });

        //--------------------------Tv Display Button---------------------------------------
        this.copyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String textToCopy = MainActivity.this.textView.getText().toString();
                if (textToCopy.length() != 0) {
                    ((ClipboardManager) MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copied Text", textToCopy));
                    toast("Text copied to clipboard");
                    return;
                }
                Toast.makeText(MainActivity.this, "Please translate frist", Toast.LENGTH_SHORT).show();
            }
        });
        this.voiceIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startSpeechToText();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToShare = textView.getText().toString();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share text via"));
            }
        });


    }

    public void startSpeechToText() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
        intent.putExtra("android.speech.extra.PROMPT", "Speak something...");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, this.REQUEST_CODE_SPEECH_INPUT);
        } else {
            toast("Your device does not support speech recognition");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> result;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CODE_SPEECH_INPUT && resultCode == -1 && data != null && (result = data.getStringArrayListExtra("android.speech.extra.RESULTS")) != null && !result.isEmpty()) {
            this.edInput.setText(result.get(0));
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(getDrawable(R.drawable.icon)).setMessage((CharSequence) "Thankyou for use our Application❣️").setTitle((CharSequence) "Do you want's to exit?").setCancelable(false).setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    private void toast(String massage) {
        Toast.makeText(this, ""+massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}