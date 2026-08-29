package com.example.attendancesystem2

data class AttendanceRecord(

    val studentId: String = "",

    val studentName: String = "",

    val course: String = "",

    val date: String = "",

    val status: String = ""
)

object AttendanceRepository {

    private val attendanceRecords =
        mutableListOf<AttendanceRecord>()

    fun markAttendance(
        record: AttendanceRecord
    ) {

        attendanceRecords.add(record)
    }

    fun getAttendance(): List<AttendanceRecord> {

        return attendanceRecords
    }

    fun getAttendancePercentage(): Int {

        if (attendanceRecords.isEmpty()) {
            return 0
        }

        val present =
            attendanceRecords.count {
                it.status == "Present"
            }

        return (present * 100) /
                attendanceRecords.size
    }
}
