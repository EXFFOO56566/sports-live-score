package com.aroniez.futaa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aroniez.futaa.database.converters.assistant.AssistantConverter
import com.aroniez.futaa.database.converters.card.CardDataConverter
import com.aroniez.futaa.database.converters.card.CardListConverter
import com.aroniez.futaa.database.converters.coach.CoachConverter
import com.aroniez.futaa.database.converters.color.ColorConverter
import com.aroniez.futaa.database.converters.color.TeamColorConverter
import com.aroniez.futaa.database.converters.country.CountryConverter
import com.aroniez.futaa.database.converters.country.CountryDataConverter
import com.aroniez.futaa.database.converters.formation.FormationConverter
import com.aroniez.futaa.database.converters.goal.GoalDataConverter
import com.aroniez.futaa.database.converters.goal.GoalListConverter
import com.aroniez.futaa.database.converters.league.*
import com.aroniez.futaa.database.converters.player.PlayerDataConverter
import com.aroniez.futaa.database.converters.round.RoundConverter
import com.aroniez.futaa.database.converters.round.RoundDataConverter
import com.aroniez.futaa.database.converters.season.SeasonDataConverter
import com.aroniez.futaa.database.converters.stage.StageConverter
import com.aroniez.futaa.database.converters.stage.StageDataConverter
import com.aroniez.futaa.database.converters.time.StartingTimeConverter
import com.aroniez.futaa.database.converters.time.TimeConverter
import com.aroniez.futaa.database.converters.weather.TemperatureConverter
import com.aroniez.futaa.database.converters.weather.WeatherReportConverter
import com.aroniez.futaa.database.daos.FavoritesDao
import com.aroniez.futaa.database.daos.FixturesDao
import com.aroniez.futaa.database.daos.LeagueDao
import com.aroniez.futaa.models.Favorite
import com.aroniez.futaa.models.competitions.Competition
import com.aroniez.futaa.models.fixture.Fixture

@Database(entities = [
    Favorite::class,
    Competition::class,
    Fixture::class
], version = 1, exportSchema = false)

@TypeConverters(
        WeatherReportConverter::class,
        TemperatureConverter::class,
        ColorConverter::class,
        CardListConverter::class,
        GoalListConverter::class,
        TeamColorConverter::class,
        FormationConverter::class,
        ScoreConverter::class,
        TimeConverter::class,
        StartingTimeConverter::class,
        CoachConverter::class,
        StandingConverter::class,
        AssistantConverter::class,
        TeamConverter::class,
        PlayerDataConverter::class,
        StatsDataConverter::class,
        SubstitutionDataConverter::class,
        GoalDataConverter::class,
        CardDataConverter::class,
        VenueDataConverter::class,
        LeagueConverter::class,
        SeasonDataConverter::class,
        RoundDataConverter::class,
        RoundConverter::class,
        CountryConverter::class,
        CountryDataConverter::class,
        StageConverter::class,
        StageDataConverter::class,
        CoverageConverter::class,
        CustomLeagueConverter::class,
        CustomLeagueDataConverter::class,
        LeagueDataConverter::class
)
abstract class SoccerDatabase : RoomDatabase() {

    abstract fun fixtureDao(): FixturesDao
    abstract fun leaguesDao(): LeagueDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var instance: SoccerDatabase? = null

        fun getInstance(context: Context): SoccerDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SoccerDatabase {
            return Room.databaseBuilder(context, SoccerDatabase::class.java, "futaa")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //val request = OneTimeWorkRequestBuilder<StudentsDatabaseWorker>().build()
                            //WorkManager.getInstance().enqueue(request)
                        }
                    })
                    //.fallbackToDestructiveMigration()
                    .build()
        }
    }
}