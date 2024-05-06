package com.eunho.crossfitranker.common

/**
 * room data 상태
 **/
sealed class RoomDataResult<out T> {
    data object NoConstructor : RoomDataResult<Nothing>()
    data class Success<T>(val resultData: T) : RoomDataResult<T>()
    data class Error(val exception: Throwable) : RoomDataResult<Nothing>()
    data class RoomDBError(val exception: Throwable) : RoomDataResult<Nothing>()
}
