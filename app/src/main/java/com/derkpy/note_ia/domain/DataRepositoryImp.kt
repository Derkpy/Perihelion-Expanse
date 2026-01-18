package com.derkpy.note_ia.domain

import android.content.Context
import com.derkpy.note_ia.data.repository.DataSource
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.domain.model.TaskModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class DataRepositoryImp(private val remoteData : DataSource) : DataRepository {

    override suspend fun requestLoginWithEmailAndPassword(user: String, password: String): FirebaseUser? {
        return remoteData.loginWithEmailAndPassword(user, password)
    }

    override fun currentUser(): FirebaseUser? {
        return remoteData.currentUser()
    }

    override fun logoutSession() {
        remoteData.logoutSession()
    }

    override suspend fun requestCreateAccountWithEmailAndPassword(
        user: String,
        password: String
    ): FirebaseUser? {
        return remoteData.createAccountEmailAndPassword(user, password)
    }

    override suspend fun requestSingWithGoogle(activityContext: Context, webClientId: String): FirebaseUser? {
        return remoteData.loginWithGoogle(activityContext, webClientId)
    }

    override suspend fun makePostSaveNote(note: NoteModel): Result<String> {
        return remoteData.addNote(note)
    }

    override suspend fun makePostSaveTask(task: TaskModel): Result<String> {
        return remoteData.addTask(task)
    }

    override fun requestNotes(userId: String): Flow<List<NoteModel>> {
        return remoteData.getAllNotes(userId)
    }

    override fun requestTasks(userId: String): Flow<List<TaskModel>> {
        return remoteData.getAllTasks(userId)
    }

}