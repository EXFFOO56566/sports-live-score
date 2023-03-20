package com.aroniez.futaa.data

import androidx.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.aroniez.library.data.MzazilinkDatabase
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
class StudentsDaoTest {
    private lateinit var studendDao: StudentDao
    private lateinit var reportsDatabase: MzazilinkDatabase

    private val student1 = Student(1, "Meek Mill", "demo_school", "1234", "1", "West", "0728110017", "01/08/2018 08:15:22", false, true, true)
    private val student2 = Student(2, "Aaron Norris", "demo_school", "1235", "1", "North", "0728110018", "01/09/2018 08:15:22", false, false, true)
    private val student3 = Student(3, "John Doe", "demo_school", "1256", "1", "West", "0728110019", "01/10/2018 08:15:22", false, true, false)
    private val student4 = Student(4, "Mary Jane", "demo_school", "1237", "4", "North", "0728110020", "01/10/2018 08:15:22", false, true, false)

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getContext()
        reportsDatabase = Room.inMemoryDatabaseBuilder(context, MzazilinkDatabase::class.java).build()
        studendDao = reportsDatabase.studentDao()

        // Insert students in non-alphabetical order to test that results are sorted by name
        studendDao.insertAll(listOf(student1, student2, student3, student4))
    }

    @After
    fun closeDb() {
        reportsDatabase.close()
    }

    @Test
    fun testInsertAndRead() {
        studendDao.insert(student4)
        val students = getValue(studendDao.getStudents())

        Assert.assertThat(students[3], equalTo(student4))
    }

    @Test
    fun getTestStudents() {
        val studentsList = getValue(studendDao.getStudents())
        Assert.assertThat(studentsList.size, equalTo(4))

        // Ensure plant list is sorted by student number
        Assert.assertThat(studentsList[0], equalTo(student1))
        Assert.assertThat(studentsList[1], equalTo(student2))
        Assert.assertThat(studentsList[2], equalTo(student3))
    }

    @Test
    fun getTestStudent() {
        Assert.assertThat(getValue(studendDao.getStudent(student1.student_number)), equalTo(student1))
        Assert.assertThat(getValue(studendDao.getStudent(student2.student_number)), equalTo(student2))
        Assert.assertThat(getValue(studendDao.getStudent(student3.student_number)), equalTo(student3))
    }

    @Test
    fun getTestFormStudents() {
        val formStudents = getValue(studendDao.getFormStudents("1"))

        Assert.assertThat(formStudents[0], equalTo(student1))
    }

    @Test
    fun getTestStreamStudents() {
        val streamStudents = getValue(studendDao.getStreamStudents("1", getValue(studendDao.getFormStreams())))

        Assert.assertThat(streamStudents.size, equalTo(3))
        // Assert.assertThat(streamStudents[0], equalTo(student1))
    }

    @Test
    fun getTestSchoolStream() {
        val streamStudents = getValue(studendDao.getFormStreams())
        print(streamStudents)
        Assert.assertThat(streamStudents.size, equalTo(2))
    }
    @Test
    fun getTestStudentSearch() {
        val streamStudents = getValue(studendDao.getSearchStudents("123%"))
        Assert.assertThat(streamStudents.size, equalTo(3))
    }

}