package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block implements CollidableRect {
    private final Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/hit.wav"));
    private final ShapeRenderer shapeRenderer;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean isVisible = true;

    public Block(ShapeRenderer shapeRenderer, int x, int y, int width, int height) {
        this.shapeRenderer = shapeRenderer;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        if (isVisible) {
            shapeRenderer.rect(x, y, width, height);
        }
    }

    public void checkCollisionWith(Ball ball) {
        if (isCollidingWith(ball)) {
            sound.play(1.0f);
            isVisible = false;
        }
    }

    @Override
    public boolean isCollidingWith(Ball ball) {
        return isVisible && ball.getY() + ball.getSize() >= y &&
                ball.getY() - ball.getSize() <= y + height &&
                ball.getX() + ball.getSize() >= x &&
                ball.getX() - ball.getSize() <= x + width;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
