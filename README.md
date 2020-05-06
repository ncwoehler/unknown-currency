This a test case to reproduce [UnknownCurrencyException(CurrencyCode=EUR) with Java 11](https://github.com/JavaMoney/jsr354-ri/issues/331)

# Prerequistes

* Docker
* Java JRE

# How to reproduce

## Build & Run (Standalone)

    make standalone

You will see a stacktrace like:

```
Exception in thread "main" java.lang.reflect.InvocationTargetException
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.base/java.lang.reflect.Method.invoke(Unknown Source)
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:48)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:87)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:51)
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:52)
Caused by: UnknownCurrencyException [currencyCode=EUR]
	at javax.money.spi.MonetaryCurrenciesSingletonSpi.getCurrency(MonetaryCurrenciesSingletonSpi.java:74)
	at javax.money.Monetary.getCurrency(Monetary.java:384)
	at de.nwoehler.unknown.currency.Main.lambda$runStandalone$0(Main.java:23)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfInt.accept(Unknown Source)
	at java.base/java.util.stream.Streams$RangeIntSpliterator.forEachRemaining(Unknown Source)
	at java.base/java.util.Spliterator$OfInt.forEachRemaining(Unknown Source)
	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
	at java.base/java.util.stream.ForEachOps$ForEachTask.compute(Unknown Source)
	at java.base/java.util.concurrent.CountedCompleter.exec(Unknown Source)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(Unknown Source)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(Unknown Source)
	at java.base/java.util.concurrent.ForkJoinPool.scan(Unknown Source)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(Unknown Source)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(Unknown Source)
```

## Build & Run (Spring Boot)

    make spring

## Change Moneta version

The default version for this test is `1.4`. To change the library version add `MONETA_VERSION=1.1`. For example:

    make standalone MONETA_VERSION=1.1