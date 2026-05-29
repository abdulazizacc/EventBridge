package com.uni.eventbridge.domain.util

data class PagedResult<T>(
    val items: List<T>,
    val currentPage: Int,
    val pageSize: Int,
)