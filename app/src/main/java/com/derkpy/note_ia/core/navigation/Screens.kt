package com.derkpy.note_ia.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Presentation

@Serializable
object Login

@Serializable
object SignUp

@Serializable
object Home

@Serializable
data class Detail(val id: String)

@Serializable
object Notification