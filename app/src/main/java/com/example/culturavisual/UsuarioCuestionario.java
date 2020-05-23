package com.example.culturavisual;

public class UsuarioCuestionario implements Comparable{

    private String _user;
    private String _quizID;
    private int _correctAnswers;
    private int _wrongAnswers;
    private int _score;
    private String _quizName;
    private String _quizImage;

    public UsuarioCuestionario(){

    }

    public UsuarioCuestionario(String user, String quizID, int correct, int wrong, int score, String name, String imageURL){
        this._user = user;
        this._quizID = quizID;
        this._correctAnswers = correct;
        this._wrongAnswers = wrong;
        this._score = score;
        this._quizName = name;
        this._quizImage = imageURL;
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

    public void setQuizName(String name){
        this._quizName = name;
    }

    public String getQuizName(){
        return _quizName;
    }

    public void setQuizImage(String imageURL){
        this._quizImage = imageURL;
    }

    public String getQuizImage(){
        return _quizImage;
    }


    @Override
    public int compareTo(Object o) {
        int comparescore=((UsuarioCuestionario)o).getScore();
        return comparescore-this._score;
    }
}
