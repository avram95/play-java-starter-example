package models;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

/**
 * Custom execution context wired to "database.dispatcher" thread pool
 */
public class DatabaseExecutionContext extends CustomExecutionContext {

    @javax.inject.Inject
    public DatabaseExecutionContext(ActorSystem actorSystem) {
        // uses a custom thread pool defined in application.conf
        super(actorSystem, "database.dispatcher");
    }
}