package com.anovosvit.anymaster.di

import com.anovosvit.anymaster.data.repositories.MessageRepository
import com.anovosvit.anymaster.data.repositories.MessageRepositoryImpl
import com.anovosvit.anymaster.data.sources.SocketMock
import com.anovosvit.anymaster.data.sources.SocketMockImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMessageRepository(socketMock: SocketMock): MessageRepository =
        MessageRepositoryImpl(socketMock)

    @Provides
    @Singleton
    fun provideSocketMock(): SocketMock = SocketMockImpl()

}
