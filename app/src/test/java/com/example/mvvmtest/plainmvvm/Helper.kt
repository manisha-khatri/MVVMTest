package com.example.mvvmtest.plainmvvm

object Helper {

    fun readFileResource(filename: String): String {
        val inputStream = object {}.javaClass.classLoader?.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("File $filename not found in test resources")
        return inputStream.bufferedReader().use { it.readText() }
    }
}