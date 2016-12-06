# PG6100 Exam

# Structure
- ```no.woact.controller``` Backing beans.
- ```no.woact.dao``` Beans responsible for database communication. Classes with a @PersistenceContext.
- ```no.woact.external.restful``` Integration towards the countries API.
- ```no.woact.model``` All @Entity classes.
- ```no.woact.service``` Classes here provide JAX-RS endpoints.
- ```no.woact.service.dto``` POJOs only used for serializing JSON and XML.

I've gone with this setup as I think this provides a nice separation of concerns.

# Comments
I've decided to use ```jboss-web.xml``` to set the context root because I think this is a better solution than a html page with a redirect head.

Using the term "Sign in/out" instead of "Login/Logout".

I've used the library ```org.mindrot.jbcrypt``` to hash the password of a registered user.

In one test I have used the ```org.jsoup.jsoup``` library to easier navigate a table. This was mostly just for fun and experimenting.

The DRY concept took a hit in some of the tests. I should probably have made a more generic workflow for common actions as 
creating a user or creating an event.


All the integration tests specified in the assignments is implemented and is executed successfully.