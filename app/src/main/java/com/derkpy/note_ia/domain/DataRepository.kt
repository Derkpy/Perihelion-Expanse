package com.derkpy.note_ia.domain

import android.content.Context
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.domain.model.TaskModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun requestLoginWithEmailAndPassword(user : String, password : String): FirebaseUser?

    fun currentUser(): FirebaseUser?

    fun logoutSession()

    suspend fun requestCreateAccountWithEmailAndPassword(user: String, password: String): FirebaseUser?

    suspend fun requestSingWithGoogle(activityContext: Context, webClientId: String): FirebaseUser?

    suspend fun makePostSaveNote(note: NoteModel) : Result<String>

    suspend fun makePostSaveTask(task: TaskModel) : Result<String>

    fun requestNotes(userId : String) : Flow<List<NoteModel>>

    fun requestTasks(userId : String) : Flow<List<TaskModel>>

}