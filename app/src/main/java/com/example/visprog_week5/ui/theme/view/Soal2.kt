package com.example.visprog_week5.ui.theme.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visprog_week5.model.ipkUiState
import com.example.visprog_week5.ui.theme.view.ui.theme.VisProg_Week5Theme
import com.example.visprog_week5.viewModel.ipkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Soal2(ipkViewModel: ipkViewModel = viewModel() ){
    val courses = ipkViewModel.course.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Courses",
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    modifier = Modifier.padding(15.dp)
                )
                Text(
                    text = "Total SKS: ${ipkViewModel.getTotalCredits()}",
                    modifier = Modifier.padding(start = 15.dp, bottom = 5.dp)
                )
                Text(
                    text = "IPK: ${String.format("%.2f", ipkViewModel.calculateGPA())}",
                    modifier = Modifier.padding(start = 15.dp, bottom = 5.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = ipkViewModel.jumlahSKS,
                        singleLine = true,
                        modifier = Modifier
                            .width(170.dp)
                            .padding(start = 5.dp, bottom = 10.dp),
                        onValueChange = { ipkViewModel.updateSKS(it) },
                        label = { Text( "SKS") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        )
                    )
                    OutlinedTextField(
                        value = ipkViewModel.nilai,
                        singleLine = true,
                        onValueChange = {  ipkViewModel.updateNilai(it) },
                        label = { Text(text = "Score")},
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done),
                        modifier = Modifier
                            .width(170.dp)
                            .padding(end = 5.dp, bottom = 10.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedTextField(
                        value = ipkViewModel.mataKuliah,
                        singleLine = true,
                        onValueChange = {  ipkViewModel.updateMataKuliah(it) },
                        label = { Text(text = "Name")},
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done),
                        modifier = Modifier
                            .width(270.dp)
                            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        onClick = { ipkViewModel.tambahCourse() },
                        enabled = ipkViewModel.nilai.isNotBlank() && ipkViewModel.mataKuliah.isNotBlank() && ipkViewModel.jumlahSKS.isNotBlank()
                    ) {
                        Text("+")
                    }

                }
            }
        }
        items(courses.value){ courses ->
            CourseItem(courses, onDeleteClick = {
                ipkViewModel.deleteCourse(courses)
            })
        }
    }
}

@Composable
fun CourseItem(course: ipkUiState, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = Modifier.padding(10.dp)
        ){
            Text(text = "Name: ${course.matkul}", fontWeight = FontWeight.SemiBold)
            Text(text = "SKS: ${course.sks}")
            Text(text = "Score: ${course.score}")
        }
        Button(
            onClick = onDeleteClick,
            modifier = Modifier.padding(10.dp).background(Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "slebew",
                tint = Color.Red
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2Preview() {
    VisProg_Week5Theme {
        Soal2()
    }
}