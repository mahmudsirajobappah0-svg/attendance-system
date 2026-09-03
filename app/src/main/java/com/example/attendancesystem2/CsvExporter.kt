package com.example.attendancesystem2

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileWriter

object CsvExporter {

    fun exportAttendance(context: Context, course: String, records: List<AttendanceRecord>) {
        val fileName = "attendance_${course.replace(" ", "_")}_${System.currentTimeMillis()}.csv"
        val file = File(context.cacheDir, fileName)

        FileWriter(file).use { writer ->
            writer.append("Student Name,Date,Status\n")
            records.forEach { record ->
                writer.append("${record.studentName},${record.date},${record.status}\n")
            }
        }

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, "Export attendance"))
    }
}
