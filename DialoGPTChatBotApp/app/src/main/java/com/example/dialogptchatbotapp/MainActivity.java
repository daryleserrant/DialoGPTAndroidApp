package com.example.dialogptchatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    LinearLayout contentView;
    ScrollView scrollView;
    Button sendButton;
    EditText promptTextBox;
    String serverURL;
    ProgressBar loadingCursor;

    AlertDialog.Builder builder;
    RequestQueue requestQueue;
    Handler handler;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);
        handler = new Handler(Looper.getMainLooper());
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        contentView = this.findViewById(R.id.contentView);
        sendButton = this.findViewById(R.id.sendButton);
        promptTextBox = this.findViewById(R.id.promptTextbox);
        scrollView = this.findViewById(R.id.scrollView);
        loadingCursor = this.findViewById(R.id.progressCursor);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sendButton.setOnClickListener(v -> handleSendButtonClick());
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    private void handleSendButtonClick()
    {
        String prompt = promptTextBox.getText().toString().trim();
        if (!prompt.equals(""))
        {
            UserChatBubble bubble = new UserChatBubble(this, null);
            bubble.setText(promptTextBox.getText().toString());
            contentView.addView(bubble);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bubble.getLayoutParams();
            float leftDp = 160;
            float bottomDp = 10;
            Resources r = getResources();
            float leftPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDp, r.getDisplayMetrics());
            float bottomDx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottomDp, r.getDisplayMetrics());
            params.setMargins((int)leftPx,params.topMargin, params.rightMargin,(int)bottomDx);
            bubble.setLayoutParams(params);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
            loadingCursor.setVisibility(View.VISIBLE);
            sendPromptToChatbot(promptTextBox.getText().toString());
            promptTextBox.getText().clear();
        }
    }

    private void sendPromptToChatbot(String prompt)
    {
        try {
            String url = serverURL + "/get_response";
            JSONObject data = new JSONObject();
            data.put("prompt", prompt);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data, response -> {
                try {
                    createResponseBubble(response.getString("response"));
                }
                catch (Exception e){
                    Log.e("MAIN","Error parsing response");
                }
            }, error -> showServerNotFoundDialog());
            requestQueue.add(request);
        }
        catch (Exception e)
        {
            Log.e("MAIN", e.toString());
        }
    }

    private void createResponseBubble(String response)
    {
        ResponseChatBubble bubble = new ResponseChatBubble(this, null);
        bubble.setText(response);
        contentView.addView(bubble);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) bubble.getLayoutParams();
        float bottomDp = 10;
        Resources r = getResources();
        float bottomDx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottomDp, r.getDisplayMetrics());
        params.setMargins(params.leftMargin,params.topMargin, params.rightMargin,(int)bottomDx);
        bubble.setLayoutParams(params);
        loadingCursor.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showServerDialog();
    }

    private void showServerDialog()
    {
        builder.setTitle("Chatbot Server URL");
        builder.setMessage("Enter the URL of the Chatbot Server");
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        String url = preferences.getString("CHATBOTURL", null);
        if (url != null)
        {
            input.setText(url);
        }
        builder.setView(input);
        builder.setPositiveButton("Submit", (dialog, which) -> {
            serverURL = input.getText().toString();
            if (serverURL.charAt(serverURL.length() - 1) == '/')
            {
                serverURL = serverURL.substring(0, serverURL.length() - 1);
            }
            preferences.edit().putString("CHATBOTURL", serverURL).apply();
        });
        builder.show();
    }

    private void showServerNotFoundDialog()
    {
        builder.setTitle("Chatbot Server Not Found");
        builder.setMessage("The address you provided was not found. Please enter the correct Chatbot address.");
        EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(serverURL);
        builder.setView(input);
        builder.setPositiveButton("Submit", (dialog, which) -> {
            serverURL = input.getText().toString();
            if (serverURL.charAt(serverURL.length() - 1) == '/')
            {
                serverURL = serverURL.substring(0, serverURL.length() - 1);
            }
            preferences.edit().putString("CHATBOTURL", serverURL).apply();
        });
        builder.show();
    }
}