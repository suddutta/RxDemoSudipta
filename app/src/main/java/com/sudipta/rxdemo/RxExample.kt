package com.app.rxexamples

import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable


fun main() {
    // executeFromArrayOperatorDemo()

    /*   executeCreate(listOf(10, 0, 5,12))
           .subscribe { println("Items: $it") }*/

}

private fun executeFromArrayOperatorDemo() {
    Observable.fromArray(
        "India",
        "America",
        "Australia",
        "SouthAfrica",
        "jj",
        "jkjk",
        "uu",
        "dfdf",
        "erer",
        "ewerw",
        "iuuio",
        "fgdgf",
        "hjj"
    )
        .subscribe { println(it) }
}

fun executeCreate(list: List<Int>) =
    Observable.create<Int> { emitter ->
        list.forEach { item ->
            if (item <= 0) {
                emitter.onError(item)
            } else {
                emitter.onNext(item)
            }
        }
        emitter.onComplete()
    }

private fun <T> Emitter<T>.onError(item: T) {
    println("Items in error: $item")
}



