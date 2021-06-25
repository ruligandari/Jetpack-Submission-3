package com.dicoding.ruligandari.jetpack_submission3.util

import androidx.paging.PagedList
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
object UtilPagedList {

    fun <T> mockPagedList(list: List<T>): PagedList<T>{
        val pagedList = mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList[anyInt()]).then{
            invocation -> val index = invocation.arguments.first() as Int
            list[index]
        }
        `when` (pagedList.size).thenReturn(list.size)

        return pagedList
    }
}