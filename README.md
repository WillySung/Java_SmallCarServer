Java_SmallCarServer
===================

This is the server part(on PC) of the small car project.

It has a user interface for users to send instructions and messages to smartphone through Wi-Fi.

On the other side, it can receive and show the image from the camera on smartphone.

ImageServer.java
-----------------
This is the main program, create two thread : ImageThread and ChatThread.

ImageThread.java
-----------------
This thread receives the image from the camera on smartphone, and shows it on the screen.

ChatThread.java
----------------
This thread is for control UI, letting users send instructions and messages to smartphone.
