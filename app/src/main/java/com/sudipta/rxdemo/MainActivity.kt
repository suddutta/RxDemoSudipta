package com.sudipta.rxdemo

import java.util.concurrent.TimeUnit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

//import io.reactivex.Observable
//import io.reactivex.Observer
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.disposables.Disposable
//import io.reactivex.observers.DisposableObserver
// import io.reactivex.schedulers.Schedulers
import com.jakewharton.rxbinding4.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.SingleObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()

      //  deBounceDemo()
        //executeJustOperatorDemo()
        //  executeInterval()
        // executeIntervalWithTake()
        //  executeTimer()
        // executeDelay()
        // rangeOperatorDemo()
        //  executeDistinct()
        //  executeSkipLast()
        executeCount()
        // bufferOperatorDemo()
        //   concatAndMergeDemo()

       //  mapOperatorDemo()
        // flatMapOperatorDemo()
        //   concatFlatMapDemo()
        //  switchMapOperatorDemo()

    }

    private fun executeInterval() {

        Observable.interval(0, 5, TimeUnit.SECONDS)
            .flatMap {
                return@flatMap Observable.create<String> { emitter ->

                    emitter.onNext("Interval-OnNext")
                    emitter.onComplete()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println(it)
            }

    }

    private fun executeIntervalWithTake()
    {
        Observable.interval(0, 2, TimeUnit.SECONDS)
            .take(5)
            .flatMap {
                return@flatMap Observable.create<String> { emitter ->
                    emitter.onNext("take-OnNext")
                    emitter.onComplete()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println(it)
            }
    }

    private fun executeTimer() {
        println("TimerExample")
        Observable.timer(10, TimeUnit.SECONDS)
            .flatMap {
                return@flatMap Observable.create<String> { emitter ->
                    emitter.onNext("OnNext-Timer")
                    emitter.onComplete()
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe {
                println("TimerExample: $it")
            }
    }

    private fun executeDelay() {
        println("DelayExample")
        Observable.create<String> { emitter ->
            emitter.onNext("OnNext-Delay")
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .delay(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println("DelayExample: $it")
            }
    }

    private fun executeDistinct(){
        Observable.just("India", "Bangaldesh", "Japan", "India")
            .distinct()
            .subscribe {
                println(it)
            }
    }

    private fun executeSkipLast(){
        Observable.just("India", "Bangaldesh", "Japan", "India", "China")
            .skipLast(2)
            .subscribe {
                println(it)
            }
    }

    private fun executeCount(){
        println("Count:")
        Observable.just(1, 2, 3, 4, 5)
            .count()
            .subscribe(object : SingleObserver<Long> {
                fun onSubscribe(d: Disposable?) {}
                override fun onSuccess(aLong: Long) {
                    println("Count: $aLong")
                }

                override fun onError(e: Throwable) {}
                override fun onSubscribe(d: io.reactivex.disposables.Disposable) {

                }
            })
    }

    private fun deBounceDemo() {
        compositeDisposable.add(
            search_ed.textChangeEvents()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchQuery())

        )

    }

    private fun searchQuery(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onComplete() {
                Log.d("RX", "Completed Debounce...")
            }

            override fun onNext(t: TextViewTextChangeEvent) {
                search_tv.text = "Query : ${t.text}"
            }

            override fun onError(e: Throwable) {

            }

        }

    }

    private fun executeJustOperatorDemo() {
        println("::::::::::::::::::Just Operator Demo::::::::::::::::::::::::::::::::")
        justOperatorObsever(justOperatorObservableDemo())
    }

    private fun justOperatorObservableDemo(): Observable<String> {
        return Observable
            .just("India", "US", "Australia", "Canada",
                "France","China","obc","yhn","Chinujma","ggb")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun justOperatorObsever(observable: Observable<String>) {
        observable.subscribe(object : Observer<String> {
            override fun onComplete() {
                println("Completed Just Operator Demo")
                disposable.dispose()
            }

            override fun onSubscribe(d: Disposable) {
                println(" Just Operator Subscribed")
                disposable = d

            }

            override fun onNext(t: String) {
                println("Just Operator Emission $t")
            }

            override fun onError(e: Throwable) {
                println("Just operator Throws an Error ${e.localizedMessage}")
            }

        })
    }

    private fun rangeOperatorDemo() {
        println("::::::::Range Operator Demo ::::::::::::::::::::")
        Observable.range(1, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it % 2 == 0 }
            .map { it.toString() }
            .subscribe(object : DisposableObserver<String?>() {

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.e("RX", "All numbers emitted!")
                }

                override fun onNext(t: String?) {
                    Log.i("RX", "Number: $t")
                }
            })


    }

    private fun bufferOperatorDemo() {
        println("::::::::::::::::::::::Buffer Operator Demo ::::::::::::::::::::::::")
        val observable = Observable.range(1, 40)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it % 2 == 1 }
            .map { "$it Is odd Number" }
            .buffer(3)

        val observer = object : DisposableObserver<List<String>>() {
            override fun onComplete() {
                println("All Emissions Completed")
                Log.d("RX", "All Emissions Completed")
            }

            override fun onNext(t: List<String>) {
                Log.d("RX", "In onNext")
                Log.d("RX", "Emitted Val is $t")
            }

            override fun onError(e: Throwable) {
                Log.e("RX", "Error Thrown ${e.localizedMessage}")
            }

        }

        observable.subscribe(observer)


    }

    private fun concatAndMergeDemo() {
        val concatObservable = Observable.concat(fetchMaleObservable(), fetchFemaleObservable())
        val mergerObservable = Observable.merge(fetchMaleObservable(), fetchFemaleObservable())

        val observer = object : Observer<User> {
            override fun onComplete() {
                println("Completed Concat Observable")
            }

            override fun onSubscribe(d: Disposable) {
                println("Subscribed Concat Observable")
            }


            override fun onError(e: Throwable) {
                println("Error on Concat Observable")
            }


            override fun onNext(t: User) {
                try {
                    Thread.sleep(200)
                    println("Concate Observable ::::User Name is ${t?.name} and Gender is ${t?.gender} ")
                } catch (e: Exception) {

                }

            }

        }

        val mergeObserver = object : Observer<User> {
            override fun onComplete() {
                println("Completed Merge Observable")
            }

            override fun onSubscribe(d: Disposable) {
                println("Subscribed Merge Observable")
            }


            override fun onError(e: Throwable) {
                println("Error on Merge Observable")
            }


            override fun onNext(t: User) {
                try {
                    Thread.sleep(200)
                    println("Merge Observable ::User Name is ${t?.name} and Gender is ${t?.gender} ")
                } catch (e: Exception) {

                }

            }

        }
        concatObservable.subscribe(observer)
        mergerObservable.subscribe(mergeObserver)
    }

    private fun fetchMaleObservable(): Observable<User> {

        return Observable.fromIterable(DataSource.createMaleUserList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun fetchFemaleObservable(): Observable<User> {
        return Observable.fromIterable(DataSource.createFeMaleUserList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun updateUser(user: User): User {
        user.email = "${user.name}@email.com"
        return user
    }

    private fun mapOperatorDemo() {
        var observable = fetchFemaleObservable().map {
            updateUser(it)
        }


        val observer = object : Observer<User> {
            override fun onComplete() {
                println("Completed Map Operator Demo")
            }

            override fun onSubscribe(d: Disposable) {
                println("Subscribed Map Operator Demo")
            }

            override fun onNext(t: User) {
                println("User Info $t")
            }

            override fun onError(e: Throwable) {
                println("Error on  Map Operator Demo")
            }

        }

        observable.subscribe(observer)
    }

    private fun flatMapOperatorDemo() {
        println("Flat Map Operator Demo ::::::::::")
        val observable = fetchMaleObservable()
            .map { updateUser(it) }
            .flatMap { fetchAddressObsevable(it) }
        val observer = object : Observer<User> {
            override fun onComplete() {
                println("In Flat Map OnComplete")
            }

            override fun onSubscribe(d: Disposable) {
                println("In Flat Map onSubscribe")
            }

            override fun onNext(t: User) {
                println("Flat Map User Info :: $t")
            }

            override fun onError(e: Throwable) {
                println("In Flat Map onError  ${e?.localizedMessage}")
            }

        }

        observable.subscribe(observer)
    }

    private fun fetchAddressObsevable(user: User): Observable<User> {
        var address = Address("${user.name}1600 Amphitheatre Parkway, Mountain View, CA 94043")
        user.address = address

        val addressObservable = Observable.just(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        return addressObservable
    }

    private fun concatFlatMapDemo() {
        println("concat Map Operator Demo ::::::::::")
        val observable = fetchMaleObservable()
            .map { updateUser(it) }
            .concatMap { fetchAddressObsevable(it) }
        val observer = object : Observer<User> {
            override fun onComplete() {
                println("In concat Map OnComplete")
            }

            override fun onSubscribe(d: Disposable) {
                println("In concat Map onSubscribe")
            }

            override fun onNext(t: User) {
                println("concat Map User Info :: $t")
            }

            override fun onError(e: Throwable) {
                println("In concat Map onError  ${e?.localizedMessage}")
            }

        }

        observable.subscribe(observer)
    }

    private fun switchMapOperatorDemo() {
        println("Switch Map Operator Demo ::::::::::")
        val observable = fetchMaleObservable()
            .map { updateUser(it) }
            .switchMap { fetchAddressObsevable(it) }
        val observer = object : Observer<User> {
            override fun onComplete() {
                println("In Switch Map OnComplete")
            }

            override fun onSubscribe(d: Disposable) {
                println("In Switch Map onSubscribe")
            }

            override fun onNext(t: User) {
                println("Switch Map User Info :: $t")
            }


            override fun onError(e: Throwable) {
                println("In Switch Map onError  ${e.localizedMessage}")
            }

        }

        observable.subscribe(observer)
    }
}

private fun <T> SingleSource<T>.subscribe(singleObserver: SingleObserver<T>) {

}