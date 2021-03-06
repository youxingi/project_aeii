package net.toyknight.aeii.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ObjectSet;
import net.toyknight.aeii.GameContext;
import net.toyknight.aeii.ResourceManager;
import net.toyknight.aeii.entity.Unit;

/**
 * @author toyknight 5/20/2015.
 */
public class UnitSparkAnimator extends UnitAnimator {

    private Animation spark_animation;

    public UnitSparkAnimator(GameContext context, Unit unit) {
        super(context);
        createAnimation();
        addUnit(unit, "target");
    }

    public UnitSparkAnimator(GameContext context, ObjectSet<Unit> units) {
        super(context);
        createAnimation();
        int index = 0;
        for (Unit unit : units) {
            addUnit(unit, Integer.toString(index++));
        }
    }

    private void createAnimation() {
        Texture texture_white_spark = getResources().getWhiteSparkTexture();
        spark_animation = new Animation(1f / 15, ResourceManager.createFrames(texture_white_spark, 6, 1));
    }

    @Override
    public void render(Batch batch) {
        for (Unit unit : getUnits()) {
            if (unit != null) {
                getCanvas().getRenderer().drawUnit(batch, unit, unit.getX(), unit.getY());
                int screen_x = getCanvas().getXOnScreen(unit.getX());
                int screen_y = getCanvas().getYOnScreen(unit.getY());
                batch.draw(spark_animation.getKeyFrame(getStateTime()), screen_x, screen_y, ts(), ts());
                batch.flush();
            }
        }
    }

    @Override
    public void update(float delta) {
        addStateTime(delta);
    }

    @Override
    public boolean isAnimationFinished() {
        return getStateTime() >= 1f / 15 * 6;
    }

}
