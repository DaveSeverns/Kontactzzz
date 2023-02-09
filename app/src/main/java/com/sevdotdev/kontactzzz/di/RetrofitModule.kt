package com.sevdotdev.kontactzzz.di

import com.sevdotdev.kontactzzz.contacts.data.ContactsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
private object RetrofitModule {
    @Provides
    @ViewModelScoped
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @ViewModelScoped
    fun providesContactsApi(retrofit: Retrofit) = retrofit.create(ContactsApi::class.java)

}