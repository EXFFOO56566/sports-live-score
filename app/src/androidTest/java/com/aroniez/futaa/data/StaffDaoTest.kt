package com.aroniez.futaa.data

import androidx.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.aroniez.library.data.MzazilinkDatabase
import com.aroniez.library.data.staff.Staff
import com.aroniez.library.data.staff.StaffDao
import com.aroniez.futaa.utilities.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StaffDaoTest {
    private lateinit var staffDao: StaffDao
    private lateinit var reportsDatabase: MzazilinkDatabase

    private val staff1 = Staff(1, "Lennon Mill", "112233", "ADMIN", "0728110017", "01/08/2018 08:15:22", "demo_school", true)
    private val staff2 = Staff(2, "John Doe", "221133", "BURSAR", "0728110018", "01/08/2018 08:15:22", "demo_school", true)
    private val staff3 = Staff(3, "Mary Jane", "331122", "TEACHING", "0728110019", "01/08/2018 08:15:22", "demo_school", true)
    private val staff4 = Staff(4, "Erick Dier", "332211", "BOM", "0728110020", "01/08/2018 08:15:22", "demo_school", true)

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getContext()
        reportsDatabase = Room.inMemoryDatabaseBuilder(context, MzazilinkDatabase::class.java).build()
        staffDao = reportsDatabase.staffDao()

        // Insert students in non-alphabetical order to test that results are sorted by name
        staffDao.insertAll(listOf(staff1, staff2, staff3, staff4))
    }

    @After
    fun closeDb() {
        reportsDatabase.close()
    }

    @Test
    fun testInsertAndRead() {
        //staffDao.insert(staff4)
        val students = getValue(staffDao.getAllStaff())

        Assert.assertThat(students[0], equalTo(staff1))
    }

    @Test
    fun getTestStaff() {
        val studentsList = getValue(staffDao.getAllStaff())
        Assert.assertThat(studentsList.size, equalTo(4))

        // Ensure plant list is sorted by student number
        //Assert.assertThat(studentsList[0], equalTo(staff1))
        //Assert.assertThat(studentsList[1], equalTo(staff2))
        //Assert.assertThat(studentsList[2], equalTo(staff3))
    }

    @Test
    fun getTestSingleStaff() {
        Assert.assertThat(getValue(staffDao.getSingleStaff(staff1.id_number)), equalTo(staff1))
        Assert.assertThat(getValue(staffDao.getSingleStaff(staff2.id_number)), equalTo(staff2))
        Assert.assertThat(getValue(staffDao.getSingleStaff(staff3.id_number)), equalTo(staff3))
    }

    @Test(expected = NullPointerException::class)
    fun expectedFailure() {
        val o: Any? = null
        o!!.toString()
    }

    @Test
    fun getTestStaffType() {
        val formStudents = getValue(staffDao.getTypeOfStaff("BOM"))

        Assert.assertThat(formStudents[0], equalTo(staff4))
    }

    @Test
    fun getTestEmptyStaffSearch() {
        val staff = getValue(staffDao.getTypeOfStaff("NONE"))

        Assert.assertThat("ggg", equalTo("ggg"))
        Assert.assertThat(staff.size, equalTo(0))
    }

    @Test
    fun getTestStudentSearch() {
        val streamStudents = getValue(staffDao.getStaffSearch("33%"))
        Assert.assertThat(streamStudents.size, equalTo(2))
    }

}