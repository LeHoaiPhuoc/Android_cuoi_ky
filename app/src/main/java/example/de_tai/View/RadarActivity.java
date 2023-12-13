package example.de_tai.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import example.de_tai.R;

public class RadarActivity extends AppCompatActivity {

    WebView webView;

    ImageButton imgBack2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        webView = findViewById(R.id.webView);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Prevent opening browser when clicking a link inside WebView
        webView.setWebViewClient(new WebViewClient());

        // Load the website
        webView.loadUrl("https://www.windy.com/?16.470,107.601,5");


        imgBack2 = (ImageButton) findViewById(R.id.imgBack2);

        imgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}