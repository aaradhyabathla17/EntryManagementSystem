# EntryManagementSystem
It is an application which can used as an entry management system for any event/office/meetup.

This application uses Firebase to provide backend Service for remote storage of data of users.
Realtime Database : to store the details of the host of the event/offivce visit and the visitors 
This applications has two types of user 
1.Host   2.Visitor
As a host User has to enter his/her details with a code which will act as primary key for data traversal regarding the visitors he/she/ will have.
As a vistor Application provides visitor with two options to checkIn and checkOut of the event
as soon a the visitor checks in by providing his details and the host id of the person(Host) he has to meet .
this information is tored on firebase database and an sms and email is sent to Host with visitors details and checkIn time.
To exit: Visitor is provided with a button of checkOut which will confirm his departure and inform him with his visit details via an email and sms.
Various Types of layouts are used to achieve the desired UI of the app activities.
Email is being sent through intent.
Sms is being sent through smsmanager
Queries are being conducted to send and retrieve data from firebase which is the main backbone of this application approach.
Further many modifications can be introuced in app like sending email directly from app without using Intent which will further increase its usage and give it more fine structure.
