package com.aroniez.futaa.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aroniez.futaa.BuildConfig
import com.aroniez.futaa.R
import com.aroniez.futaa.utils.facebookLink
import com.aroniez.futaa.utils.twitterLink
import com.aroniez.futaa.utils.whatsappContact
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_in_browser.toolbar


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        companyName.text = getString(R.string.sample_company_name) + " v" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")"

        twitter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(twitterLink)
            startActivity(intent)
        }

        facebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(facebookLink)
            startActivity(intent)
        }
        whatsapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$whatsappContact")
            startActivity(intent)
        }

    }

}