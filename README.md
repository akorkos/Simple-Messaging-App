# Simple CLI Messaging Application

A project developed for the course [Computer Networks [NCO-05-02]](https://elearning.auth.gr/course/view.php?id=5942) (2022/23) of the Department of Computer Science at Aristotle University of Thessaloniki (AUTh).

## Description

Within the scope of this project, a distributed messaging system was developed using a simple request-reply protocol. Clients send a request to the server, and the server responds with a response, terminating the connection. Specifically, the Sockets technology (Sockets-I/O Streams-Threads) was used.

In this system, different users can create accounts and send messages to each other, with the server handling them. The following components provide this functionality:
* a server program capable of handling multiple requests from clients simultaneously,
* client programs, each capable of sending requests to the server.

## Implementation

The structure of the project is shown in the following UML diagram:

![UML Diagram](/uml.png)

The `Client` package implements the necessary structures for the user. Specifically, the `Client` class is responsible for executing requests to the server. It accepts the following arguments: `ip, port_number, fid, args` for request execution. Additionally, the package includes the `RequestHandler` class, which is called by the `Client` class and is responsible for opening the connection to the server, sending the request to the server, and finally closing the connection to the server.

The `Common` package contains only one class `Request` that represents requests. It is used in both the `Client` and the `Server` packages for sending and processing requests, respectively.

The `Server` package implements the necessary structures required by the server. Initially, the Server class accepts only one argument, the `port_number`, which specifies the port on which it will listen for requests. The `ServerHandler` class handles the server's functions, such as user authentication, etc. The execution of user requests is achieved through the `ClientHandler` class, which is called by the `ServerHandler` and is equipped with methods for displaying a list of all accounts in the system, sending a message, etc. Additionally, the package includes the `Account`, `MessageBox`, and `Message` classes, which represent users, each user's message box, and individual messages, respectively.

## Details / Assumptions

* The Server runs indefinitely until the window in which it is running is closed,
* a user's ID consists of 4 random numeric digits,
* a message ID consists of 4 random alphanumeric digits (e.g. gf9U),
* if the required arguments are not provided in a Client execution, nothing is displayed on the screen,
* data syntax is case-sensitive (e.g. ```Alex != alex```),
* it is assumed that numeric values will be provided when needed, and no alphanumeric values will be given,
* user messages cannot contain spaces.
