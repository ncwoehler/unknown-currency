This a test case to reproduce [UnknownCurrencyException(CurrencyCode=EUR)](https://github.com/JavaMoney/jsr354-ri/issues/158)

# How to reproduce

## Build & Run (Spring Boot)

    make run

## Build & Run (Standalone)

    make standalone

With some luck and patience it will produce a stacktrace like:

```
java.util.concurrent.ExecutionException: UnknownCurrencyException [currencyCode=EUR]
    at java.util.concurrent.FutureTask.report(FutureTask.java:122)
    at java.util.concurrent.FutureTask.get(FutureTask.java:192)
    at com.github.ayastrebov.unknown.currency.Main.main(Main.java:25)
Caused by: UnknownCurrencyException [currencyCode=EUR]
    at javax.money.DefaultMonetaryCurrenciesSingletonSpi.getCurrency(DefaultMonetaryCurrenciesSingletonSpi.java:104)
    at javax.money.Monetary.getCurrency(Monetary.java:150)
    at com.github.ayastrebov.unknown.currency.Main$Task.call(Main.java:39)
    at com.github.ayastrebov.unknown.currency.Main$Task.call(Main.java:35)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
    at java.lang.Thread.run(Thread.java:748)

```

## Change Moneta version

To change moneta library version add `MONETA_VERSION=1.1`. For example:

    make run MONETA_VERSION=1.1