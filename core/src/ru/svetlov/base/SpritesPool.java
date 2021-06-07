package ru.svetlov.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {
    protected final List<T> activeObjects = new ArrayList<>();
    protected final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty())
            object = newObject();
        else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        //System.out.println(this.getClass().getName() + " Active/free: " + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (T sprite : activeObjects)
            if (!sprite.isDestroyed())
                sprite.update(delta);
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (T sprite : activeObjects)
            if (!sprite.isDestroyed())
                sprite.draw(batch);
    }

    private void free(T object) {
        if (activeObjects.remove(object)){
            freeObjects.add(object);
            object.setDestroyed(false);
        }
        //System.out.println(this.getClass().getName() + " Active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }

    public void freeAllDestroyed() {
        int size = activeObjects.size();
        while (size-- > 0)
            if (activeObjects.get(size).isDestroyed())
                free(activeObjects.get(size));
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }
}
