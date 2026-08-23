package com.example.attendancesystem2
        import androidx.compose.foundation.layout.Column
        import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable
fun ScanScreen() {
Column {
    Text("Scan QR Attendance")
    Button(
        onClick = {
            //qr code will be here later
        }
    ){
        Text("Scan QR")
    }
}
}