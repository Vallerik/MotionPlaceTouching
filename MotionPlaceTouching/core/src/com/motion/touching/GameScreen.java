package com.motion.touching;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Vallrik on 28.10.2017.
 */

public class GameScreen implements Screen {

    private SpriteBatch batch; //Добавляем отрисовщик спрайтов
    private OrthographicCamera camera; //Добавляем камеру
    private Texture player1Texture; //Добавляем текстуру
    private Rectangle player1Rectangle; //Добавляем Rectangle
    private Vector3 touchPos; //Добавляем Вектор3 (Для тача)

    private static final float SPEED = 300f; //Мировые единицы в секунду (Скорость передвижения)
    private final Vector2 tmp = new Vector2(); //Добавляем Вектор2 (Для нахождения дистанции)

    @Override
    public void show() {
        batch = new SpriteBatch(); //Создаем отрисовщик спрайтов
        camera = new OrthographicCamera(800, 600); //Создаем камеру
        camera.setToOrtho(false, 800, 600); //Всегда показываем экран 800 на 600
        player1Texture = new Texture("badlogic.jpg"); //Добавляем текстуру
        player1Rectangle = new Rectangle();
        player1Rectangle.set(Gdx.graphics.getWidth()/2+64,Gdx.graphics.getHeight()/2+64, 64, 64);
        touchPos = new Vector3(); //Создаем Вектор3
        touchPos.x=Gdx.graphics.getWidth()/2+64; //Записывем начальное положение касания (Для установки изначального положения текстуры)
        touchPos.y=Gdx.graphics.getHeight()/2+64; //Записывем начальное положение касания (Для установки изначального положения текстуры)
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //Задаем цвет очистки фона
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Очищаем экран

        batch.setProjectionMatrix(camera.combined); //Добавляем систему координат камеры

        batch.begin(); //Начало отрисовки
        if (Gdx.input.isTouched()) { //Опрашиваем касание (Есть ли прикосновение)
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0); //Возвращаем полученные данные касания по X и Y
            camera.unproject(touchPos); //Приобразование координат касания в координаты камеры
            System.out.println("touchPos"); //Выводим текст в консоле
            System.out.println(touchPos.x); //Выводим текст в консоле
            System.out.println(touchPos.y); //Выводим текст в консоле
            //player1Rectangle.setPosition(new Vector2(touchPos.x, touchPos.y));
        }

        //Как далеко игрок может переместить этот кадр(distance = speed * time):
        float maxDistance = SPEED * Gdx.graphics.getDeltaTime();

        //Вектор от игрока до точки касания:
        tmp.set(touchPos.x, touchPos.y).sub(player1Rectangle.x, player1Rectangle.y);

        if (tmp.len() <= maxDistance) { //Достаточно близко, чтобы просто установить игрока в цель
            player1Rectangle.x = touchPos.x;
            player1Rectangle.y = touchPos.y;
        } else { //Необходимо перемещаться вдоль вектора к цели
            tmp.nor().scl(maxDistance); //Уменьшить длину вектора до расстояния, пройденного этой рамкой
            player1Rectangle.x += tmp.x; //Переместить прямоугольник на длину вектора
            player1Rectangle.y += tmp.y;
        }
        batch.draw(player1Texture, player1Rectangle.x-128, player1Rectangle.y-128); //Отрисовываем текстуру
        batch.end(); //Конец отрисовки
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        player1Texture.dispose();
    }
}
