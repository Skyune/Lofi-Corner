package com.bawp.jetweatherforecast.widgets

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bawp.jetweatherforecast.R
import org.intellij.lang.annotations.JdkConstants


@Composable
fun RoomImage(
    modifier: Modifier = Modifier,
    ImageId: Int,
    onClick: () -> Unit,
    roomTitle: String) {
    Box(modifier = modifier.fillMaxWidth()
        .padding(all = 4.dp)
        .clickable {
            onClick.invoke()
        }, ) {
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {


                Box(modifier = Modifier, Alignment.Center) {
                    Image(
                        painter = painterResource(id = ImageId),
                        modifier = Modifier,
                        contentDescription = "clickable room"
                    )
//                    Column(
//                        verticalArrangement = Arrangement.Bottom,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        //Spacer(Modifier.fillMaxSize(0.5f))
//
//                        RoundIconButton(
//                            modifier = Modifier,
//                            imageVector = Icons.Default.Star,
//                            onClick = { /*TODO*/ })
//                    }
                }
                Text(text = roomTitle, modifier = Modifier.padding(vertical = 10.dp), fontSize = 12.sp)

            }
        }
    }

}