package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    private final ShapeRenderer shapeRenderer;
    private int x;
    private int y;
    private final int size;
    private final int initialXSpeed;
    private int xSpeed;
    private int ySpeed;

    public Ball(ShapeRenderer shapeRenderer, int x, int y, int size, int xSpeed, int ySpeed) {
        this.shapeRenderer = shapeRenderer;
        this.x = x;
        this.y = y;
        this.size = size;
        this.initialXSpeed = xSpeed;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void draw() {
        shapeRenderer.circle(x, y, size);
    }

    public void checkCollision(CollidableRect collidableRect) {
        if (collidableRect.isCollidingWith(this)) {
            ySpeed = -ySpeed;
        }
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        if (x + size > Gdx.graphics.getWidth() || x - size < 0) {
            xSpeed = -xSpeed;
        }
        if (y + size > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void resetToInitialXSpeed() {
        xSpeed = initialXSpeed;
    }

    public void setFasterXSpeedLeft() {
        xSpeed = -initialXSpeed - 5;
    }

    public void setFasterXSpeedRight() {
        xSpeed = initialXSpeed + 5;
    }
}
