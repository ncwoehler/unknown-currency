This a test case to reproduce [UnknownCurrencyException(CurrencyCode=EUR) with Java 11](https://github.com/JavaMoney/jsr354-ri/issues/331)

# Prerequistes

* Docker
* Make
* Java 8+ JRE

# How to reproduce

## Build & Run

    make run

You will see a stacktrace like:

```
Exception in thread "main" UnknownCurrencyException [currencyCode=EUR]
        at javax.money.spi.MonetaryCurrenciesSingletonSpi.getCurrency(MonetaryCurrenciesSingletonSpi.java:74)
        at javax.money.Monetary.getCurrency(Monetary.java:420)
        at de.nwoehler.unknown.currency.Main.lambda$main$0(Main.java:14)
        at java.base/java.util.stream.IntPipeline$1$1.accept(Unknown Source)
        at java.base/java.util.stream.Streams$RangeIntSpliterator.forEachRemaining(Unknown Source)
        at java.base/java.util.Spliterator$OfInt.forEachRemaining(Unknown Source)
        at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
        at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
        at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(Unknown Source)
        at java.base/java.util.stream.ReduceOps$ReduceTask.doLeaf(Unknown Source)
        at java.base/java.util.stream.AbstractTask.compute(Unknown Source)
        at java.base/java.util.concurrent.CountedCompleter.exec(Unknown Source)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(Unknown Source)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(Unknown Source)
        at java.base/java.util.concurrent.ForkJoinPool.scan(Unknown Source)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(Unknown Source)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(Unknown Source)
```

## Change Moneta version

The default version for this test is `1.4`. To change the library version add `MONETA_VERSION=1.1`. For example:

    make MONETA_VERSION=1.1