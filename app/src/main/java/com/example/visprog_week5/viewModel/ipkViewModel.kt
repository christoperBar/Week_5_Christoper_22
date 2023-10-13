package com.example.visprog_week5.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import com.example.visprog_week5.model.ipkUiState
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ipkViewModel: ViewModel() {
//    private val _uiState = MutableLiveData<List<ipkUiState>>()
//    val uiState: LiveData<List<ipkUiState>> get() = _uiState

    private val _course = MutableStateFlow<List<ipkUiState>>(emptyList())
//    val course: StateFlow<List<ipkUiState>> = _uiState.asStateFlow()
    val course: StateFlow<List<ipkUiState>> get() = _course

    var mataKuliah by mutableStateOf("")
        private set
    var jumlahSKS by mutableStateOf("")
        private set
    var nilai by mutableStateOf("")
        private set

    fun tambahCourse(){
        addCourse(ipkUiState(mataKuliah, jumlahSKS.toInt(), nilai.toDouble()))
    }
    fun updateMataKuliah(matkul: String){
        mataKuliah = matkul
    }

    fun updateSKS(sks: String){
        jumlahSKS = sks
    }

    fun updateNilai(Nilai: String){
        nilai = Nilai
    }



    fun addCourse(courses: ipkUiState) {
        val currentCourses = _course.value.toMutableList()
        currentCourses.add(courses)
        _course.value = currentCourses
    }

    fun deleteCourse(courses: ipkUiState) {
        val currentCourses = _course.value.toMutableList()
        currentCourses.remove(courses)
        _course.value = currentCourses
    }

    fun getTotalCredits(): Int {
        return _course.value.sumBy { it.sks }
    }

    fun calculateGPA(): Double {
        val totalCredits = getTotalCredits()
        if (totalCredits == 0) return 0.0
        val totalPoints = _course.value.sumByDouble { it.sks * it.score }
        return totalPoints / totalCredits
    }

}