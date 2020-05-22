package com.example.culturavisual;

public class UsuarioCuestionario {

    private String _user;
    private String _quizID;
    private int _correctAnswers;
    private int _wrongAnswers;
    private int _score;

    public UsuarioCuestionario(){

    }

    public UsuarioCuestionario(String user, String quizID, int correct, int wrong, int score){
        this._user = user;
        this._quizID = quizID;
        this._correctAnswers = correct;
        this._wrongAnswers = wrong;
        this._score = score;
    }

    public void setUser(String user){
        this._user = user;
    }

    public String getUser(){
        return _user;
    }

    public void setQuizID(String quizID){
        this._quizID = quizID;
    }

    public String getQuizID(){
        return _quizID;
    }

    public void setCorrectAnswers(int correct){
        this._correctAnswers = correct;
    }

    public int getCorrectAnswers(){
        return _correctAnswers;
    }

    public void setWrongAnswers(int wrong){
        this._wrongAnswers = wrong;
    }

    public int getWrongAnswers(){
        return _wrongAnswers;
    }

    public void setScore(int score){
        this._score = score;
    }

    public int getScore(){
        return _score;
    }
}
