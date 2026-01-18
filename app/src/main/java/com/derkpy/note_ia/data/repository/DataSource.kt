package com.derkpy.note_ia.data.repository

import android.content.Context
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.domain.model.TaskModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface DataSource {

    suspend fun loginWithEmailAndPassword(user : String, password : String): FirebaseUser?

    fun currentUser(): FirebaseUser?

    fun logoutSession()

    suspend fun createAccountEmailAndPassword(user: String, password: String): FirebaseUser?

    suspend fun loginWithGoogle(activityContext: Context, webClientId: String): FirebaseUser?

    suspend fun addNote(note: NoteModel) : Result<String>

    suspend fun addTask(task: TaskModel) : Result<String>

    fun getAllNotes(userId: String) : Flow<List<NoteModel>>

    fun getAllTasks(userId: String) : Flow<List<TaskModel>>

    fun getTask(taskId: String) : Flow<TaskModel>

}
