package com.ccb.proandroiddevreader.common

import org.mockito.kotlin.whenever

infix fun Any?.returns(mockValue: Any?) = whenever(this).thenReturn(mockValue)