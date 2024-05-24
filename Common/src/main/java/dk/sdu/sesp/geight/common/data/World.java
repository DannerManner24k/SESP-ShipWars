package dk.sdu.sesp.geight.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class World {
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private List<Double> spawnPointsX1;
    private List<Double> spawnPointsX2;


    public void setSpawnPoints(List<Double> spawnPointsX1, List<Double> spawnPointsX2) {
        this.spawnPointsX1 = spawnPointsX1;
        this.spawnPointsX2 = spawnPointsX2;
    }

    public List<Double> getSpawnPointsX1() {
        return spawnPointsX1;
    }

    public List<Double> getSpawnPointsX2() {
        return spawnPointsX2;
    }

    public double getSpawnPointsX1(int index) {
        return spawnPointsX1.get(index);
    }

    public double getSpawnPointsX2(int index) {
        return spawnPointsX2.get(index);
    }

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }


    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

}
