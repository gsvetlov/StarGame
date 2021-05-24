package ru.svetlov.user.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class UserController {
    private InputProcessor inputProcessor;
    public UserController(){
        Gdx.input.getInputProcessor();
    }
}
