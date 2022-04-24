package tengine.physics.collisions.detection;

import tengine.Actor;
import tengine.physics.collisions.events.CollisionEvent;

import java.util.*;

public class CollisionDetector {
     public Collection<CollisionEvent> detectCollisions(Set<Actor> actors) {
        Map<Actor, CollisionEvent> collisions = new HashMap<>();

        // TODO: implement the following!
        // Create a set of overlapping actors with broad phase detector
        // For each overlapping actor
            // Create a map of moving actors with a set of collision events

        return Collections.unmodifiableCollection(collisions.values());
     }

}
