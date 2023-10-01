package com.dhakadevcraft.globallanguagetranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
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
    DrawerLayout drowerLayout;
    MaterialToolbar metarialToolbar;
    NavigationView navigationView;


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
        drowerLayout = findViewById(R.id.drowerLayout);
        metarialToolbar = findViewById(R.id.metarialToolbar);
        navigationView = findViewById(R.id.navigationView);

        //-------------------Drower Layout Code --------------------------------------
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drowerLayout,metarialToolbar,R.string.navigation_drawer_close,
                R.string.navigation_drawer_open);
        drowerLayout.addDrawerListener(toggle);
        drowerItem();


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
                String selectedLanguage = MainActivity.this.spLanguage.getSelectedItem().toString();
                String targetLanguageCode = "bn";

                if (selectedLanguage.equals("Albanian")) {
                    targetLanguageCode = "sq";
                } else if (selectedLanguage.equals("Amharic")) {
                    targetLanguageCode = "am";
                } else if (selectedLanguage.equals("Arabic")) {
                    targetLanguageCode = "ar";
                } else if (selectedLanguage.equals("Basque")) {
                    targetLanguageCode = "eu";
                } else if (selectedLanguage.equals("Bemba")) {
                    targetLanguageCode = "bem";
                } else if (selectedLanguage.equals("Bengali")) {
                    targetLanguageCode = "bn";
                } else if (selectedLanguage.equals("Bielarus")) {
                    targetLanguageCode = "be";
                } else if (selectedLanguage.equals("Bislama")) {
                    targetLanguageCode = "bi";
                } else if (selectedLanguage.equals("Bosnian")) {
                    targetLanguageCode = "bs";
                } else if (selectedLanguage.equals("Breton")) {
                    targetLanguageCode = "br";
                } else if (selectedLanguage.equals("Bulgarian")) {
                    targetLanguageCode = "bg";
                } else if (selectedLanguage.equals("Catalan")) {
                    targetLanguageCode = "ca";
                } else if (selectedLanguage.equals("Coptic")) {
                    targetLanguageCode = "cop";
                } else if (selectedLanguage.equals("Croatian")) {
                    targetLanguageCode = "hr";
                } else if (selectedLanguage.equals("Czech")) {
                    targetLanguageCode = "cs";
                } else if (selectedLanguage.equals("Danish")) {
                    targetLanguageCode = "da";
                } else if (selectedLanguage.equals("Dutch")) {
                    targetLanguageCode = "nl";
                } else if (selectedLanguage.equals("Dzongkha")) {
                    targetLanguageCode = "dz";
                } else if (selectedLanguage.equals("English")) {
                    targetLanguageCode = "en";
                } else if (selectedLanguage.equals("Estonian")) {
                    targetLanguageCode = "et";
                } else if (selectedLanguage.equals("Faroese")) {
                    targetLanguageCode = "fo";
                } else if (selectedLanguage.equals("Finnish")) {
                    targetLanguageCode = "fi";
                } else if (selectedLanguage.equals("French")) {
                    targetLanguageCode = "fr";
                } else if (selectedLanguage.equals("Galician")) {
                    targetLanguageCode = "gl";
                } else if (selectedLanguage.equals("German")) {
                    targetLanguageCode = "de";
                } else if (selectedLanguage.equals("Greek")) {
                    targetLanguageCode = "el";
                } else if (selectedLanguage.equals("Gujarati")) {
                    targetLanguageCode = "gu";
                } else if (selectedLanguage.equals("Hausa")) {
                    targetLanguageCode = "ha";
                } else if (selectedLanguage.equals("Hebrew")) {
                    targetLanguageCode = "he";
                } else if (selectedLanguage.equals("Hindi")) {
                    targetLanguageCode = "hi";
                } else if (selectedLanguage.equals("Hungarian")) {
                    targetLanguageCode = "hu";
                } else if (selectedLanguage.equals("Icelandic")) {
                    targetLanguageCode = "is";
                } else if (selectedLanguage.equals("Indonesian")) {
                    targetLanguageCode = "id";
                } else if (selectedLanguage.equals("Italian")) {
                    targetLanguageCode = "it";
                } else if (selectedLanguage.equals("Japanese")) {
                    targetLanguageCode = "ja";
                } else if (selectedLanguage.equals("Kazakh")) {
                    targetLanguageCode = "kk";
                } else if (selectedLanguage.equals("Khmer")) {
                    targetLanguageCode = "km";
                } else if (selectedLanguage.equals("Kannada")) {
                    targetLanguageCode = "kn";
                } else if (selectedLanguage.equals("Korean")) {
                    targetLanguageCode = "ko";
                } else if (selectedLanguage.equals("Kurdish")) {
                    targetLanguageCode = "ku";
                } else if (selectedLanguage.equals("Kyrgyz")) {
                    targetLanguageCode = "ky";
                } else if (selectedLanguage.equals("Latin")) {
                    targetLanguageCode = "la";
                } else if (selectedLanguage.equals("Lao")) {
                    targetLanguageCode = "lo";
                } else if (selectedLanguage.equals("Latvian")) {
                    targetLanguageCode = "lv";
                } else if (selectedLanguage.equals("Mende")) {
                    targetLanguageCode = "men";
                } else if (selectedLanguage.equals("Malagasy")) {
                    targetLanguageCode = "mg";
                } else if (selectedLanguage.equals("Maori")) {
                    targetLanguageCode = "mi";
                } else if (selectedLanguage.equals("Malay")) {
                    targetLanguageCode = "ms";
                } else if (selectedLanguage.equals("Maldivian")) {
                    targetLanguageCode = "dv";
                } else if (selectedLanguage.equals("Maltese")) {
                    targetLanguageCode = "mt";
                } else if (selectedLanguage.equals("Burmese")) {
                    targetLanguageCode = "my";
                } else if (selectedLanguage.equals("Nepali")) {
                    targetLanguageCode = "ne";
                } else if (selectedLanguage.equals("Niuean")) {
                    targetLanguageCode = "niu";
                } else if (selectedLanguage.equals("Norwegian")) {
                    targetLanguageCode = "no";
                } else if (selectedLanguage.equals("Nyanja")) {
                    targetLanguageCode = "ny";
                } else if (selectedLanguage.equals("Pakistani")) {
                    targetLanguageCode = "ur";
                } else if (selectedLanguage.equals("Palauan")) {
                    targetLanguageCode = "pau";
                } else if (selectedLanguage.equals("Panjabi")) {
                    targetLanguageCode = "pa";
                } else if (selectedLanguage.equals("Pashto")) {
                    targetLanguageCode = "ps";
                } else if (selectedLanguage.equals("Pijin")) {
                    targetLanguageCode = "pis";
                } else if (selectedLanguage.equals("Polish")) {
                    targetLanguageCode = "pl";
                } else if (selectedLanguage.equals("Portuguese")) {
                    targetLanguageCode = "pt";
                } else if (selectedLanguage.equals("Kirundi")) {
                    targetLanguageCode = "rn";
                } else if (selectedLanguage.equals("Romanian")) {
                    targetLanguageCode = "ro";
                } else if (selectedLanguage.equals("Russian")) {
                    targetLanguageCode = "ru";
                } else if (selectedLanguage.equals("Sango")) {
                    targetLanguageCode = "sg";
                } else if (selectedLanguage.equals("Sinhala")) {
                    targetLanguageCode = "si";
                } else if (selectedLanguage.equals("Slovak")) {
                    targetLanguageCode = "sk";
                } else if (selectedLanguage.equals("Samoan")) {
                    targetLanguageCode = "sm";
                } else if (selectedLanguage.equals("Shona")) {
                    targetLanguageCode = "sn";
                } else if (selectedLanguage.equals("Somali")) {
                    targetLanguageCode = "so";
                } else if (selectedLanguage.equals("Spanish")) {
                    targetLanguageCode = "es";
                } else if (selectedLanguage.equals("Swedish")) {
                    targetLanguageCode = "sv";
                } else if (selectedLanguage.equals("Swahili")) {
                    targetLanguageCode = "sw";
                } else if (selectedLanguage.equals("Tamil")) {
                    targetLanguageCode = "ta";
                } else if (selectedLanguage.equals("Telugu")) {
                    targetLanguageCode = "te";
                } else if (selectedLanguage.equals("Tetum")) {
                    targetLanguageCode = "tet";
                } else if (selectedLanguage.equals("Tajik")) {
                    targetLanguageCode = "tg";
                } else if (selectedLanguage.equals("Thai")) {
                    targetLanguageCode = "th";
                } else if (selectedLanguage.equals("Tigrinya")) {
                    targetLanguageCode = "ti";
                } else if (selectedLanguage.equals("Turkmen")) {
                    targetLanguageCode = "tk";
                } else if (selectedLanguage.equals("Tagalog")) {
                    targetLanguageCode = "tl";
                } else if (selectedLanguage.equals("Tswana")) {
                    targetLanguageCode = "tn";
                } else if (selectedLanguage.equals("Tongan")) {
                    targetLanguageCode = "to";
                } else if (selectedLanguage.equals("Turkish")) {
                    targetLanguageCode = "tr";
                } else if (selectedLanguage.equals("Ukrainian")) {
                    targetLanguageCode = "uk";
                } else if (selectedLanguage.equals("Uzbek")) {
                    targetLanguageCode = "uz";
                } else if (selectedLanguage.equals("Vietnamese")) {
                    targetLanguageCode = "vi";
                } else if (selectedLanguage.equals("Wolof")) {
                    targetLanguageCode = "wo";
                } else if (selectedLanguage.equals("Xhosa")) {
                    targetLanguageCode = "xh";
                } else if (selectedLanguage.equals("Yiddish")) {
                    targetLanguageCode = "yi";
                } else if (selectedLanguage.equals("Zulu")) {
                    targetLanguageCode = "zu";
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


    private void drowerItem(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home){
                    drowerLayout.closeDrawer(GravityCompat.START);

                }else if(item.getItemId() == R.id.rate){
                    String packageName = BuildConfig.APPLICATION_ID;
                    Uri uri = Uri.parse("market://details?id=" + packageName + "&hl=en-US&showAllReviews=true");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                    drowerLayout.closeDrawer(GravityCompat.START);

                }else if(item.getItemId() == R.id.feedback){
//                    String packageName = BuildConfig.APPLICATION_ID;
//                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    if (intent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(intent);
//                    }

                    String packageName = BuildConfig.APPLICATION_ID;
                    Uri uri = Uri.parse("market://details?id=" + packageName + "&hl=en-US#reviews");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                    drowerLayout.closeDrawer(GravityCompat.START);

                }else if(item.getItemId() == R.id.update){

                    drowerLayout.closeDrawer(GravityCompat.START);

                }else if(item.getItemId() == R.id.more){
                    String developerName = "DhakaDevCraft";
                    Uri uri = Uri.parse("https://play.google.com/store/search?q=pub:" + developerName);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                    drowerLayout.closeDrawer(GravityCompat.START);

                }else if(item.getItemId() == R.id.privacy){
                    startActivity(new Intent(MainActivity.this,PrivacyActivity.class));
                    drowerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
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