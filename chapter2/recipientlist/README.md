Chapter 2 - Recipient List Example
----------------

This example shows you how to use a Recipient List from Camel. 

### 2.6.4 - Using recipient lists

To run this example, execute the following on the command line:

	mvn clean test -Dtest=OrderRouterWithRecipientListTest
	mvn clean test -Dtest=SpringOrderRouterWithRecipientListTest
	mvn clean test -Dtest=OrderRouterWithRecipientListAnnotationTest
	mvn clean test -Dtest=SpringOrderRouterWithRecipientListAnnotationTest

### Active Mq

```
    sudo docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```
