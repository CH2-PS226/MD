package capstone.catora.di

import android.content.Context
import capstone.catora.data.CatoraRepository
import capstone.catora.data.pref.UserPreferences
import capstone.catora.data.pref.dataStore
import capstone.catora.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): CatoraRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return CatoraRepository.getInstance(apiService, pref)
    }
}