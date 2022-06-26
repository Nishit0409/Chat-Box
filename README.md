# Chat-Box
A complete Java (AWT) Chat Application with great customizable GUI Interface. It has features such as general chat and private chat, sending images and more.
--> Socket based chat application with a chat Client and a chat Server. With swing GUI and message.
--> Created custom UserObject class which will have the client details like username, the socket of user, and the room name, etc.
--> When the Chat Server runs, it opens the Server Socket at port mentioned and listen for the client to connect. If the client connects to the server, it
will open a separate thread to service. So, when the client sends QUIT command, it will close the thread too.
