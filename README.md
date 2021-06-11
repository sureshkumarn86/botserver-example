# Bot Reply Server
## Overview
Bot integration server replies to customers into their chats.
When a customer, also known as visitors, writes a message in the chat, which is sent to our servers. Our AI analyzes that message in order to predict the customer’s intent, enabling our bot to return an appropriate reply according to the predicted intent. An example of a conversation:
```
> Visitor: "Hello"
*AI understands that this is a Greeting*
> Bot: "Hello :) How can I help you?"
```

To clarify, in this example:
```
“Hello” is the visitor message
“Greeting” is the predicted intent
“How can I help you” is the reply
```

In order to analyze the intent behind a visitor message, we consume an AI API. A message is given to the AI and a list of intents is returned. A simplified version looks something like this: 

```
[
 {
   "name": "Greeting",
   "confidence": 0.81
 },
 {
   "name": "Delivery status",
   "confidence": 0.18
 },
 {
   "name": "Refund possibility",
   "confidence": 0.01
 }
] 
```



As you can see in the above example, the AI does not give one definitive intent, but a list of intents with corresponding confidence values. The confidence means how sure was the AI that this is the correct intent behind what the user meant by a certain message. Usually the reply that is related to this intent is only given, if the confidence is above a certain threshold which is configurable for every reply. In other cases we give the visitor a default answer that can, for example, tell the visitor that the AI could not give the correct answer.

## How to run
### Pre requisites
- A running mongodb
- JDK 8+ in classpath

### Steps to run
- create database in mongodb `(test)` and a collection`(intent_reply)`
- create reply message configurations in `intent_reply`. Sample [here](src/main/resources/intent_reply_example.json)
- run `./mvnw spring-boot:run` in shell.
- open swagger-ui at `http://localhost:8080/swagger-ui.html`. (sample params: botId `5f74865056d7bb000fcd39ff`, message `Hello`) 
- alternatively open `http://localhost:8080/api/?botId=5f74865056d7bb000fcd39ff&visitorMessage=Hello` in a client (or browser).
- run jUnit tests using `./mvnw test`.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.1/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-mongodb)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

