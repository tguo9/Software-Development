Logger Setup
=================================================

This mini homework assignment requires you to setup Log4j2 and learn how to configure the log output. The **only thing** you should do for this homework assignment is create a Log4j2 configuration file in the correct location with the correct configuration.

Configuration
-------------------------------------------------

You should configure Log4j2 to output to both the console and a `debug.log` file in the current working directory. 

Only log `INFO` level messages and higher to the console. The expected console output should look like:

```
ibis
wren
egret urk
finch ack
``` 

The console output includes the log message (like `egret`), if appropriate the exception message (like `urk`), and finally a newline.

Log `ALL` log levels to a `debug.log` file. See the `test/debug.log` file for the expected file output. It is also included below:

```
[1 TR] LoggerSetup.main main: tucan 
[2 DE] LoggerSetup.main main: dodo 
[3 IN] LoggerSetup.main main: ibis 
[4 WA] LoggerSetup.main main: wren 
[5 ER] LoggerSetup.main main: egret java.lang.Exception: urk
    at LoggerSetup.main(LoggerSetup.java:13)
    at LoggerSetupTest.setup(LoggerSetupTest.java:42)
[6 FA] LoggerSetup.main main: finch java.lang.Exception: ack
    at LoggerSetup.main(LoggerSetup.java:14)
    at LoggerSetupTest.setup(LoggerSetupTest.java:42)
```

The file output includes the log sequence number, two letters from the log level, class name, method name, thread name, log message, and 3 lines from the stack trace if appropriate, all followed by a newline. 

You should **NOT** modify the `Log4j2Setup.java`, `Log4j2SetupTest.java`, or `test/debug.log` files. You should only create a **NEW** file in the correct location to configure log4j2.

## Requirements

The official name of this homework is `LoggerSetup`. This should be the name you use for your Eclipse Java project and the name you use when running the homework test script.

See the [Homework Guides](https://usf-cs212-fall2018.github.io/guides/homework.html) for additional details on homework requirements and submission.

Hints
-------------------------------------------------

1. The `log4j2.xml` file in the [lecture examples](https://github.com/usf-cs212-fall2018/lectures/blob/master/Debugging/src/log4j2.xml) is a good starting point.

1. For configuring the output locations (where to output, file versus console), take a look at the [ConsoleAppender](https://logging.apache.org/log4j/2.x/manual/appenders.html#ConsoleAppender) and [FileAppender](https://logging.apache.org/log4j/2.x/manual/appenders.html#FileAppender) information pages. 

1. For configuring the output format (what to output), I recommend you take a look at the [PatternLayout](https://logging.apache.org/log4j/2.x/manual/layouts.html#PatternLayout) information page. It includes all of the possible patterns, like `class`, `date`, `throwable`, `file`, `location`, `line`, `message`, `method`, `n`, `level`, `sequenceNumber`, `threadId`, and `threadName` (you will only use some of these). 

**Do NOT overwrite the `test/debug.log` file. You should configure Log4j2 to write to the path `debug.log` instead.** 
