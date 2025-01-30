package com.hilalkara.cryptotracker.data.mapper

interface Mapper<I, O> {
    fun map(input: I?): O
}