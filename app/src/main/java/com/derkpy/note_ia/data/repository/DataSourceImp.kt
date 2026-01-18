package com.derkpy.note_ia.data.repository

import android.content.Context
import android.util.Log
import com.derkpy.note_ia.data.remote.google.GoogleHelper
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.domain.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DataSourceImp(
    private val auth : FirebaseAuth,
    private val googleHelper: GoogleHelper,
    private val firestore: FirebaseFirestore
) : DataSource {

    override suspend fun loginWithEmailAndPassword(user: String, password: String): FirebaseUser? {
        return try {
            val authResult = auth.signInWithEmailAndPassword(user, password).await()
            Log.i("TAG", "signInWithGoogle: success")
            authResult.user
        } catch (e: Exception) {
            Log.e("TAG", "signInWithGoogle: failed", e)
            null
        }
    }

    override fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun logoutSession() {
        auth.signOut()
    }

    override suspend fun createAccountEmailAndPassword(
        user: String,
        password: String
    ): FirebaseUser? {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(user, password).await()
            Log.i("TAG", "CreateAccountWithEmailAndPassword: success")
            authResult.user
        } catch (e: Exception) {
            Log.e("TAG", "CreateAccountWithEmailAndPassword: failed", e)
            null
        }
    }

    override suspend fun loginWithGoogle(activityContext: Context, webClientId: String): FirebaseUser? {

        val tokenResult = googleHelper.getGoogleIdToken(activityContext, webClientId)

        return if (tokenResult.isSuccess) {
            val idToken = tokenResult.getOrThrow()
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            try {
                val authResult = auth.signInWithCredential(credential).await()
                authResult.user
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw tokenResult.exceptionOrNull() ?: Exception("Google Sign-In failed")
        }
    }

    override suspend fun addNote(note: NoteModel): Result<String> = try {

        val uId = currentUser()?.uid ?: throw Exception("User not authenticated")

        firestore.collection("users")
            .document(uId)
            .collection("notes")
            .add(note)
            .await()
        Result.success("Note added successfully")
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun addTask(task: TaskModel): Result<String> = try {

        val uId = currentUser()?.uid ?: throw Exception("User not authenticated")

        firestore.collection("users")
            .document(uId)
            .collection("tasks")
            .add(task)
            .await()
        Result.success("Task added successfully")
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getAllNotes(userId: String): Flow<List<NoteModel>> = callbackFlow {

        val notesCollection = firestore.collection("users")
            .document(userId)
            .collection("notes")
            .orderBy("date", Query.Direction.DESCENDING)

        val snapshotListener = notesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val notes = snapshot.documents.mapNotNull { document ->
                    document.toObject(NoteModel::class.java)?.copy(id = document.id)
                }
                trySend(notes)
            }
        }
        awaitClose { snapshotListener.remove() }
    }

    override fun getAllTasks(userId: String): Flow<List<TaskModel>> = callbackFlow {
        val tasksCollection = firestore.collection("users")
            .document(userId)
            .collection("tasks")
            .orderBy("date", Query.Direction.DESCENDING)

        val snapshotListener = tasksCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val task = snapshot.documents.mapNotNull { document ->
                    document.toObject(TaskModel::class.java)?.copy(id = document.id)
                }
                trySend(task)
            }
        }
        awaitClose { snapshotListener.remove() }
    }

    override fun getTask(taskId: String): Flow<TaskModel> = callbackFlow{

        val uId = currentUser()?.uid ?: throw Exception("User not authenticated")

        val taskCollection = firestore.collection("users")
            .document(uId)
            .collection("tasks")
            .document(taskId)

        val snapshotListener = taskCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val task = snapshot.toObject(TaskModel::class.java)?.copy(id = snapshot.id)

                if (task != null) {
                    trySend(task)
                }
            }
        }
        awaitClose { snapshotListener.remove() }
    }

}