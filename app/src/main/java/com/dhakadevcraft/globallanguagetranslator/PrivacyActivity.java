package com.dhakadevcraft.globallanguagetranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class PrivacyActivity extends AppCompatActivity {

    TextView tvPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        tvPrivacy = findViewById(R.id.tvPrivacy);

        String appName = getString(R.string.app_name);
        String contactGmail = getString(R.string.contact_gmail);
        String companyName = getString(R.string.company_name);


        String htmlText = "<h2>Privacy Policy</h2>" +
                "<p>" +companyName + " built the " +appName + " App as a Free app. This service is provided by " +companyName + " at no cost and is intended for use as is.</p>" +
                "<p>This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.</p>" +
                "<p>If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>" +
                "<p>The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at " +appName + " App unless otherwise defined in this Privacy Policy.</p>" +
                "<h3>Information Collection and Use</h3>" +
                "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information, including but not limited to Device IDs. The information that we request will be retained by us and used as described in this privacy policy.</p>" +
                "<p>The app does use third party services that may collect information used to identify you. Link to privacy policy of third party service providers used by the app:</p>" +
                "<ul>" +
                "<li>Google Play Services</li>" +
                "<li>AdMob</li>" +
                "<li>Meta Audience Network (Facebook ads)</li>" +
                "<li>Google Analytics for Firebase</li>" +
                "<li>In-App Updates</li>" +
                "</ul>" +
                "<h3>Log Data</h3>" +
                "<p>We want to inform you that whenever you use our Service, in a case of an error in the app we collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (\"IP\") address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>" +
                "<h3>Cookies</h3>" +
                "<p>Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.</p>" +
                "<p>This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>" +
                "<h3>Service Providers</h3>" +
                "<p>We may employ third-party companies and individuals due to the following reasons:</p>" +
                "<ul>" +
                "<li>To facilitate our Service;</li>" +
                "<li>To provide the Service on our behalf;</li>" +
                "<li>To perform Service-related services; or</li>" +
                "<li>To assist us in analyzing how our Service is used.</li>" +
                "</ul>" +
                "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>" +
                "<h3>Security</h3>" +
                "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>" +
                "<h3>Links to Other Sites</h3>" +
                "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>" +
                "<h3>In-App Updates</h3>" +
                "<p>We use in-app updates to provide our users with the latest features and improvements to our app. During the in-app update process, we may collect information about your device.including your device model, operating system version, and app version. This information is collected to ensure that we provide you with the correct version of the app for your device. We do not collect any personal information during the in-app update process. </p>"+
                "<h3>Children’s Privacy</h3>" +
                "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>" +
                "<h3>Changes to This Privacy Policy</h3>" +
                "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. This policy is effective as of 2024-12-31.</p>" +
                "<h3>Contact Us</h3>" +
                "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at <a href=\"mailto:"+contactGmail+"\">"+contactGmail +"</a>.</p>";


        tvPrivacy.setText(HtmlCompat.fromHtml(htmlText, 0));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}