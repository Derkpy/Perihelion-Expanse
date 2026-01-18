package com.derkpy.note_ia.di

import com.derkpy.note_ia.core.navigation.vm.NavigationViewModel
import com.derkpy.note_ia.ui.detail.vm.DetailViewModel
import com.derkpy.note_ia.ui.login.vm.LoginViewModel
import com.derkpy.note_ia.ui.home.vm.HomeViewModel
import com.derkpy.note_ia.ui.signup.vm.SingUpViewModel
import com.derkpy.note_ia.ui.presentation.vm.PresentationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainViewModule = module {

    viewModelOf(::LoginViewModel)

    viewModelOf(::NavigationViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::SingUpViewModel)

    viewModelOf(::PresentationViewModel)

    viewModelOf(::DetailViewModel)
}