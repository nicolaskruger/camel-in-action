Chapter 2 - Content-Based Router Example
----------------

These examples show you how to use a Content-Based Router from Camel. 

### 2.6.1 - Using a content-based router

The first example is a simple CBR and can be run using:

    mvn test -Dtest=OrderRouterTest
    mvn test -Dtest=SpringOrderRouterTest

The next example has a CBR with an otherwise clause to catch bad orders.
To run this example, execute the following command:

    mvn test -Dtest=OrderRouterOtherwiseTest
    mvn test -Dtest=SpringOrderRouterOtherwiseTest

The last example has a CBR with an otherwise clause to catch bad orders and
stop routing. To run this example, execute the following command:

    mvn test -Dtest=OrderRouterWithStopTest
    mvn test -Dtest=SpringOrderRouterWithStopTest

## FTP

```code
    sudo docker run -d -v ~/Documentos/OneDrive/root/curses/self_taught/sboot/camel/camelinaction2/chapter2/ftp-jms:/orders -p 20:20 -p 21:21 -p 47400-47470:47400-47470 -e FTP_USER=yourName -e FTP_PASS=yourPass -e PASV_ADDRESS=127.0.0.1 --name ftp-new --restart=always bogem/ftp
```

### ActiveMQ

```code
    sudo docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```