package com.dicoding.ruligandari.jetpack_submission3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.ruligandari.jetpack_submission3.data.remote.ApiResponse
import com.dicoding.ruligandari.jetpack_submission3.data.remote.StatusResponse
import com.dicoding.ruligandari.jetpack_submission3.util.AppExecutor
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutor: AppExecutor) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadDataFromDB()

        result.addSource(dbSource){
            data -> result.removeSource(dbSource)
            if (shouldFetch(data)){
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource){
                    newData -> result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val apiResponse = createCall()

        result.addSource(dbSource){
            newData -> result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse){
            response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when(response.status){
                StatusResponse.SUCCESS ->
                    mExecutor.diskIO().execute{
                        saveCallResult(response.body)
                        mExecutor.mainThread().execute{
                            result.addSource(loadDataFromDB()){
                               newData -> result.value = Resource.success(newData)
                            }
                        }
                    }
                StatusResponse.EMPTY -> mExecutor.mainThread().execute{
                    result.addSource(loadDataFromDB()){
                        newData -> result.value =Resource.success(newData)
                    }
                }
                StatusResponse.ERROR ->{
                    onFetchFailed()
                    result.addSource(dbSource){
                        newData -> result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    private fun onFetchFailed() {}

    protected abstract fun saveCallResult(data : RequestType)

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadDataFromDB(): LiveData<ResultType>

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}