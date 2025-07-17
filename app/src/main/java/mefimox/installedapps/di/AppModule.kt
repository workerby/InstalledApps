package mefimox.installedapps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mefimox.installedapps.data.PackageManagerRepository
import mefimox.installedapps.domain.repositories.PackagesRepository
import mefimox.installedapps.domain.usecases.GetAppInfo
import mefimox.installedapps.domain.usecases.GetInstalledPackages
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePackageRepository(@ApplicationContext context: Context): PackagesRepository {
        return PackageManagerRepository(context.packageManager)
    }

    @Provides
    @Singleton
    fun provideGetAppInfo(packagesRepository: PackagesRepository): GetAppInfo {
        return GetAppInfo(packagesRepository)
    }

    @Provides
    @Singleton
    fun provideGetInstalledPackages(packagesRepository: PackagesRepository): GetInstalledPackages {
        return GetInstalledPackages(packagesRepository)
    }
}
