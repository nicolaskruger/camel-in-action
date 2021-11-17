Chapter 2 - FTP-JMS Routing Example
----------------

These examples are not runnable in their current form as they reference
an external FTP server.

They are used to explain the Java DSL and show how auto-complete can be
used in your editor to build up a Java DSL route. 

## FTP

```code
    sudo docker run -d -v ~/Documentos/OneDrive/root/curses/self_taught/sboot/camel/camelinaction2/chapter2/ftp-jms:/orders -p 20:20 -p 21:21 -p 47400-47470:47400-47470 -e FTP_USER=yourName -e FTP_PASS=yourPass -e PASV_ADDRESS=127.0.0.1 --name ftp-new --restart=always bogem/ftp
```

### ActiveMQ

```code
    sudo docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```