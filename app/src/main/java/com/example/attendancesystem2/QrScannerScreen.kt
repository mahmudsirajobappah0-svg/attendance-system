package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ScannerBackground = Color(0xFF080D17)
private val ScannerGold = Color(0xFFE7B96B)
private val ScannerWhite = Color(0xFFF5F5F5)
private val ScannerGray = Color(0xFF9BA5B5)

@Composable
fun QrScannerScreen(
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScannerBackground)
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Scan Attendance",
                color = ScannerWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Point your camera at the lecturer's QR code",
                color = ScannerGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(
                        Color(0xFF151E2D),
                        RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "📷",
                    fontSize = 70.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Camera scanner will be connected next",
                color = ScannerGold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ScannerGold,
                    contentColor = Color(0xFF101722)
                )
            ) {
                Text(
                    text = "Back to Dashboard",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
