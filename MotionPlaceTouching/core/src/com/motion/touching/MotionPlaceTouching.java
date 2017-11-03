package com.motion.touching;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MotionPlaceTouching extends Game {

	Screen gameScreen; //Добавляем Игровой экран
	
	@Override
	public void create () {
		gameScreen = new GameScreen(); //Создаем экземпляр класса StartGame (Основной Игровой экран)
		setScreen(gameScreen); //Устанавливаем экран запуска
	}

	@Override
	public void dispose () {
		super.dispose();
		gameScreen.dispose();
	}
}
