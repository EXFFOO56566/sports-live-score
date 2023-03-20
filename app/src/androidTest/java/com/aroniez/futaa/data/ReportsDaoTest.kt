package com.aroniez.futaa.data

import androidx.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.aroniez.library.data.MzazilinkDatabase
import com.aroniez.library.data.reports.Departure
import com.aroniez.library.data.reports.ReportsDao
import com.aroniez.library.data.students.Student
import com.aroniez.library.data.students.StudentDao
import com.aroniez.futaa.utilities.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportsDaoTest {
    private lateinit var reportsDao: ReportsDao
    private lateinit var studentDao: StudentDao
    private lateinit var reportsDatabase: MzazilinkDatabase

    private val student1 = Student(1, "Meek Mill", "demo_school", "1234", "1", "West", "0728110017", "01/08/2018 08:15:22", false, true, true)
    private val student2 = Student(2, "Aaron Norris", "demo_school", "1235", "1", "North", "0728110018", "01/09/2018 08:15:22", false, false, true)
    private val student3 = Student(3, "John Doe", "demo_school", "1256", "1", "West", "0728110019", "01/10/2018 08:15:22", false, true, false)
    private val student4 = Student(4, "Mary Jane", "demo_school", "1237", "4", "North", "0728110020", "01/10/2018 08:15:22", false, true, false)

    private val report1 = Departure("01/10/2018 08:15:22", student1, "Departure", "demo_school", "To collect Fee", "Home", "HOD")
    private val report2 = Departure("01/10/2018 08:15:23", student2, "Arrival", "demo_school", "N/A", "School", "N/A")
    private val report3 = Departure("01/10/2018 08:15:24", student3, "Departure", "demo_school", "To collect Fee", "Home", "HOD")
    private val report4 = Departure("01/10/2018 08:15:25", student4, "Arrival", "demo_school", "N/A", "School", "N/A")

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getContext()
        reportsDatabase = Room.inMemoryDatabaseBuilder(context, MzazilinkDatabase::class.java).build()
        reportsDao = reportsDatabase.reportsDao()
        studentDao = reportsDatabase.studentDao()

        // Insert students in non-alphabetical order to test that results are sorted by name
        studentDao.insertAll(listOf(student1, student2, student3, student4))
        reportsDao.insertAll(listOf(report1, report2, report3, report4))
    }

    @After
    fun closeDb() {
        reportsDatabase.close()
    }

    @Test
    fun testInsertAndRead() {
        // reportsDao.insert(report1)
        val students = getValue(reportsDao.getReports("1"))

        Assert.assertThat(students[0], equalTo(report1))
    }

    @Test
    fun arrivalAllReportsTest() {
        val studentsList = getValue(reportsDao.getAllArrivalReports())
        Assert.assertThat(studentsList.size, equalTo(2))
    }

    @Test
    fun arrivalFilterReportsTest() {
        val studentsList = getValue(reportsDao.getArrivalReports(arrayListOf("01/10/2018%")))
        Assert.assertThat(studentsList.size, equalTo(2))
    }

    @Test
    fun departureAllReportsTest() {
        val studentsList = getValue(reportsDao.getAllDepartureReports())
        Assert.assertThat(studentsList.size, equalTo(2))
    }

    @Test
    fun departureFilterReportsTest() {
        val studentsList = getValue(reportsDao.getDepartureReports(arrayListOf("01/10/2018%")))
        Assert.assertThat(studentsList.size, equalTo(2))
    }
}