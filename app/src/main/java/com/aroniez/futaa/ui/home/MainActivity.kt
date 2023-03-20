package com.aroniez.futaa.ui.home

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.selectedLang
import com.aroniez.futaa.services.MatchesReloadService
import com.aroniez.futaa.ui.about.AboutActivity
import com.aroniez.futaa.ui.competitions.LeaguesHomeFragment
import com.aroniez.futaa.ui.content.videos.VideosFragment
import com.aroniez.futaa.ui.favorites.FavoriteMatchesFragment
import com.aroniez.futaa.ui.fixture.fragments.MatchesHomeFragment
import com.aroniez.futaa.ui.livegames.LiveMatchesFragment
import com.aroniez.futaa.ui.matches.DateMatchesActivity
import com.aroniez.futaa.utils.SharingUtil
import com.aroniez.futaa.utils.loadBannerAds
import com.aroniez.futaa.utils.loadInterstialAds
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val selectedDate = formatter.format(calendar.time)
        val intent = Intent(this@MainActivity, DateMatchesActivity::class.java)
        intent.putExtra("date", selectedDate)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        MobileAds.initialize(this)

        changeLocalLanguage() //for testing purposes only

        //initialize firebase analytics
        FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.title = ""
        }

        handleBottomBarTabClicks()
        bottomBar.selectedItemId = R.id.action_live_matches

        startService(Intent(this, MatchesReloadService::class.java))

        loadBannerAds(this, homeBannerLayout)
        loadInterstialAds(this)

        //AdmobAdsUtil.loadNativeAds1(this, homeBannerLayout)


    }

    private fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("Facebook", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("Facebook", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("Facebook", "printHashKey()", e)
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun changeLocalLanguage() {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(selectedLang)) // API 17+ only.
        res.updateConfiguration(conf, dm)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_about_us) {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
        if (id == R.id.action_calendar) {
            datePickerDialog.show()
        }
        if (id == R.id.action_share) {
            SharingUtil.shareApp(this)
        }
        if (id == R.id.action_rate) {
            SharingUtil.openAppInPlaystore(this)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun handleBottomBarTabClicks() {
        bottomBar!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_videos -> displaySelectedFragment(VideosFragment::class.java.simpleName)
                R.id.action_live_matches -> displaySelectedFragment(MatchesHomeFragment::class.java.simpleName)
                R.id.action_matches -> displaySelectedFragment(LiveMatchesFragment::class.java.simpleName)
                R.id.action_teams -> displaySelectedFragment(FavoriteMatchesFragment::class.java.simpleName)
                else -> displaySelectedFragment(LeaguesHomeFragment::class.java.simpleName)
            }

            true
        }
    }

    private fun replaceFragment(
            fragmentManager: FragmentManager,
            currentTag: String,
            clazz: Fragment
    ) {

        val selectedFragment = fragmentManager.findFragmentByTag(currentTag)
        val allTags = arrayListOf(
                VideosFragment::class.java.simpleName,
                MatchesHomeFragment::class.java.simpleName,
                LiveMatchesFragment::class.java.simpleName,
                FavoriteMatchesFragment::class.java.simpleName,
                LeaguesHomeFragment::class.java.simpleName
        )
        if (selectedFragment != null) {
            fragmentManager.beginTransaction().show(selectedFragment).commit()
        } else {
            fragmentManager.beginTransaction().add(R.id.contentContainer, clazz, currentTag)
                    .commit()
        }
        allTags.remove(currentTag)
        for (fragmentTag in allTags) {
            val fragmentToHide = fragmentManager.findFragmentByTag(fragmentTag)
            if (fragmentToHide != null) {
                fragmentManager.beginTransaction().hide(fragmentToHide).commit()
            }
        }
    }

    private fun displaySelectedFragment(fragmentTag: String) {
        val fm = supportFragmentManager
        when (fragmentTag) {
            VideosFragment::class.java.simpleName -> {
                toolbarTitle.text = "Videos"
                replaceFragment(fm, VideosFragment::class.java.simpleName, VideosFragment())
            }
            MatchesHomeFragment::class.java.simpleName -> {
                toolbarTitle.text = getString(R.string.upcoming_matches_title)
                replaceFragment(fm, MatchesHomeFragment::class.java.simpleName, MatchesHomeFragment())
            }
            LiveMatchesFragment::class.java.simpleName -> {
                toolbarTitle.text = getString(R.string.live_matches_title)
                replaceFragment(fm, LiveMatchesFragment::class.java.simpleName, LiveMatchesFragment())
            }
            FavoriteMatchesFragment::class.java.simpleName -> {
                toolbarTitle.text = getString(R.string.favorite_matches_title)
                replaceFragment(fm, FavoriteMatchesFragment::class.java.simpleName, FavoriteMatchesFragment())
            }
            LeaguesHomeFragment::class.java.simpleName -> {
                toolbarTitle.text = getString(R.string.soccer_league_title)
                replaceFragment(fm, LeaguesHomeFragment::class.java.simpleName, LeaguesHomeFragment())
            }
        }
    }
}
